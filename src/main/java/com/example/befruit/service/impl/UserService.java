package com.example.befruit.service.impl;

import com.example.befruit.converter.UserConverter;
import com.example.befruit.dto.UserDTO;
import com.example.befruit.entity.User;
import com.example.befruit.repo.UserRepo;
import com.example.befruit.service.IUserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserConverter userConverter;

    @Autowired
    private JavaMailSender mailSender;
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO add(User user) {
        return null;
    }

    @Override
    public List<UserDTO> getUsers() {
       return null;
    }

    @Override
    public UserDTO getUserById() {

        return userConverter.convertToDto(userRepo.findById(1).get());
    }

    @Override
    public void register(UserDTO userDTO, String siteURL) {
        System.out.println("vào register");
//        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(userDTO.getPassword());
        User user = userConverter.convertToEntity(userDTO);
        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);
//        user.setId(1212134);
        userRepo.save(user);
        sendVerificationEmail(user, siteURL);

    }

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
            String toAddress = user.getEmail()+"địa chỉ nảyAA";
            System.out.println(toAddress);
            String fromAddress = "Your email address";
            String senderName = "Your company name";
            String subject = "Please verify your registration";
            String content = "Dear [[name]],<br>"
                    + "Please click the link below to verify your registration:<br>"
                    + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                    + "Thank you,<br>"
                    + "Your company name.";

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(fromAddress, senderName);
            helper.setTo(toAddress);
            helper.setSubject(subject);

            content = content.replace("[[name]]", user.getLastName()+user.getFirstName());
            String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();

            content = content.replace("[[URL]]", verifyURL);

            helper.setText(content, true);

            mailSender.send(message);
        } catch (UnsupportedEncodingException | MessagingException e) {
            e.printStackTrace();
        }

    }
}
