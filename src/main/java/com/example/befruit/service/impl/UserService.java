package com.example.befruit.service.impl;

import com.example.befruit.converter.UserConverter;
import com.example.befruit.dto.UserDTO;
import com.example.befruit.dto.response.UserResponse;
import com.example.befruit.entity.ERole;
import com.example.befruit.entity.Role;
import com.example.befruit.entity.User;
import com.example.befruit.repo.RoleRepo;
import com.example.befruit.repo.UserRepo;
import com.example.befruit.sercurity.jwt.exception.TokenRefreshException;
import com.example.befruit.service.IUserService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import java.beans.FeatureDescriptor;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService implements IUserService {
	@Value("${tuyen.app.jwtRefreshExpirationMs}")
	private Long refreshTokenDurationMs;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	private UserConverter userConverter;

	@Autowired
	private RoleRepo repo;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserResponse add(UserDTO userDTO) {
		List<Role> roles = new ArrayList<>();
		Arrays.stream(userDTO.getRoles()).map(role->roles.add(roleRepo.findByName(role))).collect(Collectors.toList());
		User user;
		if(userDTO.getId()!=null){
			user=userRepo.findById(userDTO.getId())
					.orElseThrow(() -> new EntityNotFoundException("Product "+userDTO.getId()+" does not exist!"));
			BeanUtils.copyProperties(userDTO,user,"email");
//			user.setFirstName(userDTO.getFirstName());
//			user.setLastName(userDTO.getLastName());
//			user.setStatus(userDTO.getStatus());
//			user.setEmail(userDTO.getEmail());

		}else{
		 user = userConverter.convertToEntity(userDTO);
	}

		user.setRoles(roles);
		user.setPassword(encodedPassword(123456+""));
		user.setEnabled(true);
		user.setUserName(UUID.randomUUID().toString());
		System.out.println(user.getFirstName()+" "+user.getLastName());
		User userAdded =userRepo.save(user);
		System.out.println(userAdded.getFirstName()+" "+userAdded.getLastName());
		return userConverter.convertToResponse(userAdded);
	}

	@Override
	public List<UserDTO> getUsers() {
		return null;
	}

	@Override
	public User getUserByToken(String token) {
		User user = userRepo.findByToken(token);
		if (user.getExpiryDate().compareTo(Instant.now()) < 0) {
			user.setToken(null);
			user.setExpiryDate(null);
			userRepo.save(user);
			throw new TokenRefreshException(token, "Refresh token was expired. Please make a new signing request");

		}
		return user;
	}

	@Transactional
	@Override
	public UserDTO getUserById(long id) {
		User u = userRepo.findById(id).get();
		UserDTO user = new UserDTO();
		user.setId(u.getId());
		user.setEmail(u.getEmail());
		return user;
	}


	@Override
	public void register(UserDTO userDTO, String siteURL, boolean isSendMail) {
		User userCheck = userRepo.findByEmail(userDTO.getEmail());
		if (userCheck != null && !userCheck.getEnabled()) {
			sendVerificationEmail(userConverter.convertToEntity(userDTO), siteURL);
			return;
		}
		if (userCheck != null && userCheck.getEnabled()) {
			throw new RuntimeException("Email already exists!");
		}
       /* if (userRepo.existsByUsername(userDTO.getEmail())){
            throw new RuntimeException("Username đã được đăng ký!");
        }*/

		userDTO.setPassword(encodedPassword(userDTO.getPassword()));
		User user = userConverter.convertToEntity(userDTO);
		Role role = roleRepo.findByName(ERole.CLIENT.name());
		user.addRole(role);
		user.setStatus(1);
		user.setUserName(UUID.randomUUID().toString());
		user.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
		user.setToken(UUID.randomUUID().toString());
		String randomCode = RandomString.make(64);
		user.setVerificationCode(randomCode);

		if (isSendMail) {
			user.setEnabled(false);
			sendVerificationEmail(user, siteURL);
		} else {
			user.setEnabled(true);
		}
		userRepo.save(user);
	}

	private String encodedPassword(String password) {
		return passwordEncoder.encode(password);
	}

	@Override
	public void forgotPassword(String email, String siteURL) {
		User user = userRepo.findByEmailAndStatus(email, 1);
		if (user == null) {
			throw new RuntimeException("Email does not exists!");
		}
		String randomCode = RandomString.make(64);
		user.setVerificationCode(randomCode);
		userRepo.save(user);
		sendMailForgotPassword(user, siteURL);
	}

	@Override
	@Transactional
	public String getTokenByUserId(Long id) {
		User user = userRepo.findById(id).get();
		user.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
		user.setToken(UUID.randomUUID().toString());
		userRepo.save(user);
		return user.getToken();
	}
//    public boolean verifyExpiration(String token,Instant expiryDate) {
//        if (expiryDate.compareTo(Instant.now()) < 0) {
//            refreshTokenRepository.delete(token);
//            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
//        }
//
//        return token;
//    }

	@Override
	public Boolean verify(String verificationCode) {
		User user = userRepo.findByVerificationCode(verificationCode);

		if (user == null || user.getEnabled()) {
			return false;
		}
		user.setEnabled(true);
		user.setVerificationCode(null);
		userRepo.save(user);
		return true;

	}

	@Transactional
	@Override
	public void resetPassword(String verificationCode, String password) {
		User user = userRepo.findByVerificationCode(verificationCode);
		if (user == null) throw new RuntimeException("Account does not exist!");
		user.setPassword(encodedPassword(password));
		user.setVerificationCode(null);
		userRepo.save(user);
	}
	@Transactional
	@Override
	public void changePassword(String email,String password) {
		User user = userRepo.findByEmail(email);
		if (user == null) throw new RuntimeException("Account does not exist!");
		user.setPassword(encodedPassword(password));
		userRepo.save(user);
	}

	@Override
	public boolean checkExistByEmail(String email) {
		return userRepo.existsByEmail(email);
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	public UserDTO update(UserDTO userDTO) {
		User user = userRepo.findById(userDTO.getId()).orElseThrow(() -> new EntityNotFoundException("User "+userDTO.getId()+" does not exist!"));
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		User userSaved = userRepo.save(user);
		return userConverter.convertToDto(userSaved);
	}

	@Override
	public Page<UserResponse> getAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
		return userConverter.convertToResponse(userRepo.findAll(pageable));
	}

	private void sendMailForgotPassword(User user, String siteURL) {
		try {
			String toAddress = user.getEmail();
			System.out.println("Gửi tới: " + toAddress);
			String fromAddress = "tuyencpu@gmail.com";
			String senderName = "FRUIT SHOP";
			String subject = "Reset your password.";
			String content = "Dear [[name]],<br>"
					+ "Please click the link below to reset your password:<br>"
					+ "<h3><a href=\"[[URL]]\" target=\"_self\">RESET NOW</a></h3>"
					+ "Thank you,<br>"
					+ "FRUIT SHOP.";

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);

			helper.setFrom(fromAddress, senderName);
			helper.setTo(toAddress);
			helper.setSubject(subject);

			content = content.replace("[[name]]", user.getLastName() + " " + user.getFirstName());
			String verifyURL = siteURL + "/account/reset?code=" + user.getVerificationCode();

			content = content.replace("[[URL]]", verifyURL);

			helper.setText(content, true);
			mailSender.send(message);
		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
		}

	}

	private void sendVerificationEmail(User user, String siteURL) {
		try {
			String toAddress = user.getEmail();
			System.out.println("Gửi tới: " + toAddress);
			String fromAddress = "tuyencpu@gmail.com";
			String senderName = "FRUIT SHOP";
			String subject = "Please verify your registration";
			String content = "Dear [[name]],<br>"
					+ "Please click the link below to verify your registration:<br>"
					+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
					+ "Thank you,<br>"
					+ "FRUIT SHOP.";

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);

			helper.setFrom(fromAddress, senderName);
			helper.setTo(toAddress);
			helper.setSubject(subject);

			content = content.replace("[[name]]", user.getLastName() + " " + user.getFirstName());
			String verifyURL = siteURL + "/account/verify?code=" + user.getVerificationCode();

			content = content.replace("[[URL]]", verifyURL);

			helper.setText(content, true);
			System.out.println("Chuân bị gửi mail");
			mailSender.send(message);
			System.out.println("Gửi mail xong");
		} catch (UnsupportedEncodingException | MessagingException e) {
			System.out.println("gui mail lỗi");
			e.printStackTrace();
		}

	}

	public static void myCopyProperties(Object src, Object target) {
		BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
	}
	private static String[] getNullPropertyNames(Object source) {
		final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
		return Stream.of(wrappedSource.getPropertyDescriptors())
				.map(FeatureDescriptor::getName)
				.filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
				.toArray(String[]::new);
	}
}
