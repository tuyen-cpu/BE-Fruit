package com.example.befruit.controller;

import com.example.befruit.dto.LoginDTO;
import com.example.befruit.dto.UserDTO;
import com.example.befruit.entity.ResponseObject;
import com.example.befruit.entity.Role;
import com.example.befruit.repo.RoleRepo;
import com.example.befruit.sercurity.service.UserDetail;
import com.example.befruit.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private IUserService userService;
    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(@RequestBody LoginDTO loginDTO) {
        try{
            System.out.println("đây0");
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetail userDetail = (UserDetail) authentication.getPrincipal();
            return new ResponseEntity<>(
                    new ResponseObject("ok","Login successful!", new UserDTO(userDetail.getUser().getId(), userDetail.getUsername(),
                         userDetail.getUsername(), "","firstName", "lastName",null)), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(new ResponseObject("failed","login failed!","Tài khoản hoặc mật khẩu khôn hợp lệ!"), HttpStatus.BAD_REQUEST);
        }

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
