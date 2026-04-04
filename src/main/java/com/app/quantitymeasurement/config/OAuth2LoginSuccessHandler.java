package com.app.quantitymeasurement.config;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.app.quantitymeasurement.entity.AuthProvider;
import com.app.quantitymeasurement.entity.Role;
import com.app.quantitymeasurement.entity.User;
import com.app.quantitymeasurement.repository.UserRepository;
import com.app.quantitymeasurement.security.CustomUserDetails;
import com.app.quantitymeasurement.security.JwtService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.frontend-url:http://localhost:4200}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");
        String providerId = oauth2User.getAttribute("sub"); // Google unique id

        if (email == null || email.isBlank()) {
            response.sendRedirect(frontendUrl + "/auth?error="
                    + URLEncoder.encode("Email not received from Google", StandardCharsets.UTF_8));
            return;
        }

        Optional<User> existingUser = userRepository.findByEmail(email);

        User user;

        if (existingUser.isPresent()) {
            user = existingUser.get();

            user.setProvider(AuthProvider.GOOGLE);
            user.setProviderId(providerId);
            user.setEnabled(true);

            if (user.getRole() == null) {
                user.setRole(Role.USER);
            }

            if (user.getUsername() == null || user.getUsername().isBlank()) {
                user.setUsername(generateUniqueUsername(email, name));
            }

            if (user.getPassword() == null || user.getPassword().isBlank()) {
                user.setPassword(passwordEncoder.encode("GOOGLE_USER"));
            }

            user = userRepository.save(user);

        } else {
            user = User.builder()
                    .username(generateUniqueUsername(email, name))
                    .email(email)
                    .password(passwordEncoder.encode("GOOGLE_USER"))
                    .role(Role.USER)
                    .provider(AuthProvider.GOOGLE)
                    .providerId(providerId)
                    .enabled(true)
                    .build();

            user = userRepository.save(user);
        }

        String token = jwtService.generateToken(new CustomUserDetails(user));

        response.sendRedirect(frontendUrl + "/auth?token="
                + URLEncoder.encode(token, StandardCharsets.UTF_8)
                + "&oauth2=success");
    }

    private String generateUniqueUsername(String email, String name) {
        String baseUsername;

        if (name != null && !name.isBlank()) {
            baseUsername = name.replaceAll("\\s+", "");
        } else {
            baseUsername = email.substring(0, email.indexOf("@"));
        }

        baseUsername = baseUsername.replaceAll("[^a-zA-Z0-9]", "");

        if (baseUsername.isBlank()) {
            baseUsername = "user";
        }

        String username = baseUsername;
        int count = 1;

        while (userRepository.existsByUsername(username)) {
            username = baseUsername + count;
            count++;
        }

        return username;
    }
}