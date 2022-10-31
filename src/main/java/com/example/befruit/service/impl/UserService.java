package com.example.befruit.service.impl;

import com.example.befruit.converter.UserConverter;
import com.example.befruit.dto.UserDTO;
import com.example.befruit.entity.ERole;
import com.example.befruit.entity.Role;
import com.example.befruit.entity.User;
import com.example.befruit.repo.RoleRepo;
import com.example.befruit.repo.UserRepo;
import com.example.befruit.service.IUserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    private JavaMailSender mailSender;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO add(User user) {
        return null;
    }

    @Override
    public List<UserDTO> getUsers() {
       return null;
    }

    @Override
    public User getUserByToken(String token) {
        return userRepo.findByToken(token);
    }

    @Override
    public UserDTO getUserById(long id) {
        User u = userRepo.findById(id).get();
UserDTO user = new UserDTO();
user.setId(u.getId());
user.setEmail(u.getEmail());
        return user;
    }



    @Override
    public void register(UserDTO userDTO, String siteURL) {

        if (userRepo.existsByEmail(userDTO.getEmail())){
            throw new RuntimeException("Email đã được đăng ký!");
        }
       /* if (userRepo.existsByUsername(userDTO.getEmail())){
            throw new RuntimeException("Username đã được đăng ký!");
        }*/

        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encodedPassword);

        User user = userConverter.convertToEntity(userDTO);
        String randomCode = RandomString.make(64);
        System.out.println(ERole.CLIENT.name());
        Role  role = roleRepo.findByName(ERole.CLIENT.name());
        user.addRole(role);
        user.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        user.setToken(UUID.randomUUID().toString());
        user.setVerificationCode(randomCode);
        user.setEnabled(false);

        userRepo.save(user);
        sendVerificationEmail(user, siteURL);

    }
    @Override
      public  String getTokenByUserId(Long id){
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
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepo.save(user);
            return true;
        }
    }

    private void sendVerificationEmail(User user, String siteURL)   {
        try{
            String toAddress = user.getEmail();
            System.out.println(toAddress);
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

            content = content.replace("[[name]]", user.getLastName()+" "+user.getFirstName());
            String verifyURL = "http://localhost:4200" + "/account/verify?code=" + user.getVerificationCode();

            content = content.replace("[[URL]]", verifyURL);

            helper.setText(content, true);

            mailSender.send(message);
        } catch (UnsupportedEncodingException | MessagingException e) {
            e.printStackTrace();
        }

    }
}
