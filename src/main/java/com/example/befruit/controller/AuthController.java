package com.example.befruit.controller;

import com.example.befruit.dto.TokenDTO;
import com.example.befruit.dto.UserDTO;
import com.example.befruit.entity.ResponseObject;
import com.example.befruit.entity.User;
import com.example.befruit.sercurity.jwt.JwtUtils;
import com.example.befruit.sercurity.jwt.payload.request.LoginRequest;
import com.example.befruit.sercurity.jwt.payload.request.ResetPasswordRequest;
import com.example.befruit.sercurity.jwt.payload.request.TokenRefreshRequest;
import com.example.befruit.sercurity.jwt.payload.response.JwtResponse;
import com.example.befruit.sercurity.jwt.payload.response.TokenRefreshResponse;
import com.example.befruit.sercurity.service.UserDetail;
import com.example.befruit.service.IUserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	private IUserService userService;
	@Value("${server.frontend}")
	private String urlFrontend;
	@Value("${google.clientId}")
	private String googleClientId;
	@Value(value = "${google.secret}")
	private String password;

	@PostMapping("/login")
	public ResponseEntity<ResponseObject> login(@RequestBody LoginRequest loginRequest) {
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			UserDetail userDetail = (UserDetail) authentication.getPrincipal();

			//generate access token
			String jwt = jwtUtils.generateJwtToken(userDetail);

			//generate refresh token
			String refreshToken = userService.getTokenByUserId(userDetail.getId());

			List<String> roles = userDetail.getAuthorities().stream()
					.map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList());

			return ResponseEntity.ok()
					.body(new ResponseObject("ok", "Login successful!",
							new JwtResponse(jwt, refreshToken, userDetail.getId(), userDetail.getUsername(), userDetail.getEmail(),userDetail.getFirstName(),userDetail.getLastName(), roles)));

		}  catch (BadCredentialsException e) {
			return ResponseEntity.badRequest().body(new ResponseObject("failed", "Email or password invalid!", ""));
		} catch (LockedException e) {
			return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
		} catch (DisabledException e) {
			return ResponseEntity.badRequest().body(new ResponseObject("failed",e.getMessage(), ""));

		}

	}

	@PostMapping("/google")
	public ResponseEntity<ResponseObject> google(@RequestBody TokenDTO tokenDTO) {

		try {
			final NetHttpTransport transport = new NetHttpTransport();
			final GsonFactory jacksonFactory = GsonFactory.getDefaultInstance();
			GoogleIdTokenVerifier.Builder verifier = new GoogleIdTokenVerifier.Builder(transport, jacksonFactory);
			final GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), tokenDTO.getValue());
			final GoogleIdToken.Payload payload = googleIdToken.getPayload();
			User uu = userService.getUserByEmail(payload.getEmail());
			UserDetail userDetail;
			String jwt;
			String refreshToken;
			List<String> roles;
			if (uu == null) {
				UserDTO user = new UserDTO();
				user.setEmail(payload.getEmail());
				user.setPassword(password);
				user.setFirstName((String) payload.get("family_name"));
				user.setLastName((String) payload.get("given_name"));
				userService.register(user, urlFrontend, false);
				userDetail = UserDetail.build(userService.getUserByEmail(user.getEmail()));

			} else {
				userDetail = UserDetail.build(uu);
			}
			jwt = jwtUtils.generateJwtToken(userDetail);
			refreshToken = userService.getTokenByUserId(userDetail.getId());
			roles = userDetail.getAuthorities().stream()
					.map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList());
			return ResponseEntity.ok().body(new ResponseObject("ok", "Login with google success!", new JwtResponse(jwt, refreshToken, userDetail.getId(), userDetail.getUsername(), userDetail.getEmail(),userDetail.getFirstName(),userDetail.getLastName(), roles)));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));

		}

	}

//	@PostMapping("/logout")
//	public ResponseEntity<?> logoutUser() {
//		ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
//		return ResponseEntity.ok()
//				.header(HttpHeaders.SET_COOKIE, cookie.toString())
//
//				.body(new ResponseObject("ok", "Logout successful!", ""));
//	}

	@PostMapping("/refreshtoken")
	public ResponseEntity<ResponseObject> refreshToken(@RequestBody TokenRefreshRequest request) {
		System.out.println("-----Enter refresh token------");
		String refreshToken = request.getRefreshToken();

		if ((refreshToken != null) && (refreshToken.length() > 0)) {
			try {
				User user = userService.getUserByToken(refreshToken);
				String token = jwtUtils.generateTokenFromUsername(user.getEmail());
				return ResponseEntity.ok().body(new ResponseObject("ok", "Refresh token successful", new TokenRefreshResponse(token, refreshToken)));

			} catch (Exception e) {
				return ResponseEntity.badRequest().body(new ResponseObject("failed", "Refresh token successful", "Ko tim thấy token"));
			}
		}
		return ResponseEntity.badRequest().body(new ResponseObject("failed", "Refresh token failed!", "Refresh token empty!"));


	}

	@PostMapping("/process_register")
	public ResponseEntity<ResponseObject> processRegister(@RequestBody UserDTO user) {
		try {
			userService.register(user, urlFrontend, true);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Register successful. Please check your mail to verify!", user));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
		}


	}

	@GetMapping("/verify")
	public ResponseEntity<ResponseObject> verifyUser(@Param("code") String code) {
		if (userService.verify(code)) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Successful verification!", "Xác minh email thành công!"));

		} else {
			return ResponseEntity.badRequest().body(new ResponseObject("", "Verification failed!", "Xác minh email thất bại!"));

		}
	}

	//    private String getSiteURL(HttpServletRequest request) {
//        String siteURL = request.getRequestURL().toString();
//        return siteURL.replace(request.getServletPath(), "");
//    }
	@GetMapping("/forgot-password")
	public ResponseEntity<ResponseObject> processForgotPassword(@RequestParam String email) {
		try {
			userService.forgotPassword(email, urlFrontend);
			return ResponseEntity.ok().body(new ResponseObject("ok", "Sent mail to reset password", "Please check mail to reset password!"));

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));

		}

	}

	@PostMapping("/reset-password")
	public ResponseEntity<ResponseObject> changePassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
		try {

			userService.resetPassword(resetPasswordRequest.getVerifyCode(), resetPasswordRequest.getPassword());
			return ResponseEntity.ok().body(new ResponseObject("ok", "Reset password successful!", "Reset password successful!"));

		} catch (Exception e) {

			return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));

		}
	}
}
