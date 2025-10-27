package youcandev.security.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import youcandev.security.jpa.User;
import youcandev.security.jpa.UserRepository;
import youcandev.security.jwt.JwtService;
import youcandev.security.model.AuthRequest;
import youcandev.security.model.AuthResponse;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final String COOKIE_NAME_REFRESH_TOKEN = "refresh_token";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public AuthResponse register(AuthRequest authRequest, HttpServletResponse response) {
        if (userRepository.findByEmail(authRequest.email()).isPresent()) {
            throw new RuntimeException("User with email: " + authRequest.email() + " already exists");
        }

        User user = User.builder()
                .email(authRequest.email())
                .password(passwordEncoder.encode(authRequest.password()))
                .role(User.Role.USER)
                .build();
        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);

        HttpCookie refreshTokenCookie = this.createRefreshTokenCookie(user);
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        return new AuthResponse(jwtToken, user.getEmail());
    }

    public AuthResponse login(AuthRequest authRequest, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtService.generateToken(userDetails);

        HttpCookie refreshTokenCookie = this.createRefreshTokenCookie(userDetails);
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        return new AuthResponse(jwtToken, userDetails.getUsername());
    }

    public AuthResponse refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(COOKIE_NAME_REFRESH_TOKEN)) {
                    refreshToken = cookie.getValue();
                }
            }
        }
        if (refreshToken == null)
            throw new RuntimeException("Cannot find refresh token in cookies");

        String username = jwtService.extractUsername(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!jwtService.isTokenValid(refreshToken, userDetails))
            throw new RuntimeException("Invalid refresh token");

        String newAccessToken = jwtService.generateToken(userDetails);

        HttpCookie refreshTokenCookie = this.createRefreshTokenCookie(userDetails);
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        return new AuthResponse(newAccessToken, userDetails.getUsername());
    }

    private ResponseCookie createRefreshTokenCookie(UserDetails userDetails) {
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        return ResponseCookie.from(COOKIE_NAME_REFRESH_TOKEN, refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/api/auth/refresh")
                .maxAge(Duration.ofDays(7))
                .build();
    }
}
