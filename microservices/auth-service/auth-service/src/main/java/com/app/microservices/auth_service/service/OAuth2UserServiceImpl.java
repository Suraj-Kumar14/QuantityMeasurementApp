package com.app.microservices.auth_service.service;

import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.*;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.app.microservices.auth_service.entity.AuthProvider;
import com.app.microservices.auth_service.entity.Role;
import com.app.microservices.auth_service.entity.User;
import com.app.microservices.auth_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oauth2User.getAttributes();

        String email;
        String name;
        String providerId;

        if ("google".equals(registrationId)) {
            email = (String) attributes.get("email");
            name = (String) attributes.get("name");
            providerId = (String) attributes.get("sub");
        } else if ("github".equals(registrationId)) {
            email = (String) attributes.get("email");
            name = (String) attributes.getOrDefault("login", "github_user");
            providerId = String.valueOf(attributes.get("id"));
        } else {
            throw new OAuth2AuthenticationException(new OAuth2Error("invalid_provider"), "Unsupported provider");
        }

        if (email == null || email.isBlank()) {
            throw new OAuth2AuthenticationException(new OAuth2Error("email_not_found"), "Email not found from provider");
        }

        userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = User.builder()
                    .username(name != null ? name.replaceAll("\\s+", "_") : email)
                    .email(email)
                    .password(null)
                    .role(Role.USER)
                    .provider("google".equals(registrationId) ? AuthProvider.GOOGLE : AuthProvider.GITHUB)
                    .providerId(providerId)
                    .enabled(true)
                    .build();
            return userRepository.save(newUser);
        });
        
        return oauth2User;
    }
}