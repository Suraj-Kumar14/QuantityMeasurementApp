package com.app.microservices.auth_service.config;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.app.microservices.auth_service.entity.AuthProvider;
import com.app.microservices.auth_service.entity.Role;
import com.app.microservices.auth_service.entity.User;
import com.app.microservices.auth_service.repository.UserRepository;
import com.app.microservices.auth_service.security.CustomUserDetails;
import com.app.microservices.auth_service.security.JwtService;

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

    @Value("${app.frontend-url:http://13.206.157.162}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

        String registrationId = "google";
        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
            registrationId = oauthToken.getAuthorizedClientRegistrationId();
        }

        AuthProvider authProvider = registrationId.equalsIgnoreCase("github")
                ? AuthProvider.GITHUB
                : AuthProvider.GOOGLE;

        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");

        String providerId = oauth2User.getAttribute("sub"); // Google
        if (providerId == null) {
            Object id = oauth2User.getAttribute("id"); // GitHub
            providerId = id != null ? String.valueOf(id) : null;
        }

        System.out.println("OAuth provider: " + registrationId);
        System.out.println("OAuth attributes: " + oauth2User.getAttributes());

        if (email == null || email.isBlank()) {
            response.sendRedirect(frontendUrl + "/auth?oauth2=failed&error="
                    + URLEncoder.encode("Email not received from OAuth provider", StandardCharsets.UTF_8));
            return;
        }

        Optional<User> existingUser = userRepository.findByEmail(email);

        User user;

        if (existingUser.isPresent()) {
            user = existingUser.get();

            user.setProvider(authProvider);
            user.setProviderId(providerId);
            user.setEnabled(true);

            if (user.getRole() == null) {
                user.setRole(Role.USER);
            }

            if (user.getUsername() == null || user.getUsername().isBlank()) {
                user.setUsername(generateUniqueUsername(email, name));
            }

            if (user.getPassword() == null || user.getPassword().isBlank()) {
                user.setPassword(passwordEncoder.encode("OAUTH_USER"));
            }

            user = userRepository.save(user);

        } else {
            user = User.builder()
                    .username(generateUniqueUsername(email, name))
                    .email(email)
                    .password(passwordEncoder.encode("OAUTH_USER"))
                    .role(Role.USER)
                    .provider(authProvider)
                    .providerId(providerId)
                    .enabled(true)
                    .build();

            user = userRepository.save(user);
        }

        String token = jwtService.generateToken(new CustomUserDetails(user));

        response.sendRedirect(frontendUrl + "/auth?token="
                + URLEncoder.encode(token, StandardCharsets.UTF_8)
                + "&oauth2=" + registrationId);
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