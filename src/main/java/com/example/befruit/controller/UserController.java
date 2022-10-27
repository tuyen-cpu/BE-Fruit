package com.example.befruit.controller;

import com.example.befruit.dto.UserDTO;
import com.example.befruit.entity.ResponseObject;
import com.example.befruit.repo.UserRepo;
import com.example.befruit.service.IUserService;
import com.example.befruit.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/user")
    public ResponseEntity<ResponseObject> getUserById() {
        UserDTO user = userService.getUserById();
//        sendEmail("18130277@st.hcmuaf.edu.vn","BANMOI");
        return new ResponseEntity<>(new ResponseObject("ok","get thanh cong",user), HttpStatus.OK);

    }
    private boolean sendEmail(String email, String voucherCode) {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);

        try {
            helper.setFrom("hello@gmail.com", "Fruit Shop");
            helper.setTo(email);
            String subject = "E-voucher";
            String content = "<p>Xin chào, </p>" + "<p>Fruit Shop xin tặng bạn voucher: <b> <h2>" + voucherCode + "</h2></b></p>"
                    + "<p>- Voucher có giá trị 50.000đ.</p>"
                    + "<p>- Voucher có thời hạn trong vòng 30 ngày kể từ khi được gửi.</p>"
                    + "<p>- Mọi thắc mắc về voucher có thể gưỉ mail về hop@gmail.com để nhận được phản hồi sớm nhất!!!</p>";
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(msg);
            return true;

        } catch (UnsupportedEncodingException | MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }
    @PostMapping("/process_register")
    public ResponseEntity<ResponseObject> processRegister(@RequestBody UserDTO user, HttpServletRequest request)
            throws UnsupportedEncodingException, MessagingException {
     try{
         userService.register(user, getSiteURL(request));
         return new ResponseEntity<>(new ResponseObject("ok","Register successful!",user), HttpStatus.OK);
     }catch (Exception e) {
         return new ResponseEntity<>(new ResponseObject("failed","Register failed!",e.getMessage()), HttpStatus.BAD_REQUEST);
     }


    }
    @GetMapping("/verify")
    public ResponseEntity<ResponseObject> verifyUser(@Param("code") String code) {
        if (userService.verify(code)) {
            return new ResponseEntity<>(new ResponseObject("ok","Successful verification!","Xác minh email thành công!"), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(new ResponseObject("","Verification failed!","Xác minh email thất bại!"), HttpStatus.BAD_REQUEST);

        }
    }
    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
