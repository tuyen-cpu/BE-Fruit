package com.example.befruit.controller;

import com.example.befruit.dto.LoginDTO;
import com.example.befruit.dto.UserDTO;
import com.example.befruit.dto.UserInformation;
import com.example.befruit.entity.ResponseObject;
import com.example.befruit.entity.Role;
import com.example.befruit.entity.User;
import com.example.befruit.repo.RoleRepo;
import com.example.befruit.sercurity.jwt.JwtUtils;
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
            //generate jwtcookie
            ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetail);

            //generate refresh jwtcookie

            String reFreshToken = userService.getTokenByUserId(userDetail.getUser().getId());
            ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(reFreshToken);

            List<String> roles = userDetail.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());


            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                    .body(new ResponseObject("ok","Login successful!",new UserInformation(userDetail.getUser().getId(),
                            userDetail.getUser().getUserName(),
                            userDetail.getUser().getEmail(),
                            roles)));

        }catch (Exception e) {
            return  ResponseEntity.badRequest().body(new ResponseObject("failed","login failed!","Tài khoản hoặc mật khẩu khôn hợp lệ!"));
        }

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new ResponseObject("ok","Logout successful!",""));
    }
    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(HttpServletRequest request) {
        String refreshToken = jwtUtils.getJwtRefreshFromCookies(request);

        if ((refreshToken != null) && (refreshToken.length() > 0)) {
           User user = userService.getUserByToken(refreshToken);
            System.out.println("id:_"+user.getId());
//            return refreshTokenService.findByToken(refreshToken)
//                    .map(refreshTokenService::verifyExpiration)
//                    .map(RefreshToken::getUser)
//                    .map(user -> {
//                        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(user);
//
//                        return ResponseEntity.ok()
//                                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
//                                .header(HttpHeaders.SET_COOKIE, refreshToken)
//                                .body(new MessageResponse("Token is refreshed successfully!"));
//                    })
//                    .orElseThrow(() -> new TokenRefreshException(refreshToken,
//                            "Refresh token is not in database!"));
        }else{
            return ResponseEntity.badRequest().body("KO còn");
        }

        return ResponseEntity.badRequest().body("lll");
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
