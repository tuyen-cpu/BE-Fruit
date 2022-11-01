package com.example.befruit.controller;

import com.example.befruit.dto.LoginDTO;
import com.example.befruit.dto.UserDTO;
import com.example.befruit.dto.UserInformation;
import com.example.befruit.entity.ResponseObject;
import com.example.befruit.entity.Role;
import com.example.befruit.entity.User;
import com.example.befruit.repo.RoleRepo;
import com.example.befruit.sercurity.jwt.JwtUtils;
import com.example.befruit.sercurity.jwt.exception.TokenRefreshException;
import com.example.befruit.sercurity.jwt.payload.request.TokenRefreshRequest;
import com.example.befruit.sercurity.jwt.payload.response.JwtResponse;
import com.example.befruit.sercurity.jwt.payload.response.TokenRefreshResponse;
import com.example.befruit.sercurity.service.UserDetail;
import com.example.befruit.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials="true")

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private IUserService userService;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(@RequestBody LoginDTO loginDTO) {
        try{
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetail userDetail = (UserDetail) authentication.getPrincipal();

            //generate access token
            String jwt = jwtUtils.generateJwtToken(userDetail);

            //generate refresh token
            String refreshToken = userService.getTokenByUserId(userDetail.getUser().getId());

            List<String> roles = userDetail.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return ResponseEntity.ok()
                    .body(new ResponseObject("ok","Login successful!",
                            new JwtResponse(jwt,refreshToken,userDetail.getUser().getId(),userDetail.getUser().getUserName(),userDetail.getUser().getEmail(),roles)));

        }catch (Exception e) {
            return  ResponseEntity.badRequest().body(new ResponseObject("failed","login failed!","Tài khoản hoặc mật khẩu khôn hợp lệ!"));
        }

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())

                .body(new ResponseObject("ok","Logout successful!",""));
    }
    @PostMapping("/refreshtoken")
    public ResponseEntity<ResponseObject> refreshToken( @RequestBody TokenRefreshRequest request) {
        System.out.println("-----Enter refresh token------");
        String refreshToken = request.getRefreshToken();

        if ((refreshToken != null) && (refreshToken.length() > 0)) {
           try{
               User user = userService.getUserByToken(refreshToken);
               String token = jwtUtils.generateTokenFromUsername(user.getEmail());
               System.out.println("tạo lại refresh token xong"+token);
               return ResponseEntity.ok().body(new ResponseObject("ok","Refresh token successful",new TokenRefreshResponse(token,refreshToken)));

           }catch (Exception e){
               return ResponseEntity.badRequest().body(new ResponseObject("failed","Refresh token successful","Ko tim thấy token"));
           }
        }
            return ResponseEntity.badRequest().body(new ResponseObject("failed","Refresh token failed!","Refresh token empty!"));



    }
    @PostMapping("/process_register")
    public ResponseEntity<ResponseObject> processRegister(@RequestBody UserDTO user, HttpServletRequest request)
            throws UnsupportedEncodingException, MessagingException {
     try{
         userService.register(user, getSiteURL(request));
         return  ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","Register successful!",user));
     }catch (Exception e) {
         return  ResponseEntity.badRequest().body(new ResponseObject("failed","Register failed!",e.getMessage()));
     }


    }
    @GetMapping("/verify")
    public ResponseEntity<ResponseObject> verifyUser(@Param("code") String code) {
        if (userService.verify(code)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","Successful verification!","Xác minh email thành công!"));

        } else {
            return ResponseEntity.badRequest().body(new ResponseObject("","Verification failed!","Xác minh email thất bại!"));

        }
    }
    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

}
