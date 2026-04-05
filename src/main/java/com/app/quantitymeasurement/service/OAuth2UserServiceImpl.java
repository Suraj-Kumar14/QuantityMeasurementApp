package com.app.quantitymeasurement.service;

import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.app.quantitymeasurement.entity.AuthProvider;
import com.app.quantitymeasurement.entity.Role;
import com.app.quantitymeasurement.entity.User;
import com.app.quantitymeasurement.repository.UserRepository;

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

        String email = null;
        String name = null;
        String providerId = null;
        AuthProvider provider = null;

        if ("google".equals(registrationId)) {
            email = (String) attributes.get("email");
            name = (String) attributes.get("name");
            providerId = (String) attributes.get("sub");
            provider = AuthProvider.GOOGLE;
        } else if ("github".equals(registrationId)) {
            email = (String) attributes.get("email");
            name = (String) attributes.getOrDefault("login", "github_user");
            providerId = String.valueOf(attributes.get("id"));
            provider = AuthProvider.GITHUB;
        } else {
            throw new OAuth2AuthenticationException(
                    new OAuth2Error("invalid_provider"),
                    "Unsupported provider");
        }

        if ("google".equals(registrationId) && (email == null || email.isBlank())) {
            throw new OAuth2AuthenticationException(
                    new OAuth2Error("email_not_found"),
                    "Email not found from Google");
        }

        if ("github".equals(registrationId) && (email == null || email.isBlank())) {
            email = providerId + "@github.local";
        }

        final String finalEmail = email;
        final String finalName = name;
        final String finalProviderId = providerId;
        final AuthProvider finalProvider = provider;

        userRepository.findByEmail(finalEmail).orElseGet(() -> {
            User newUser = User.builder()
                    .username(finalName != null ? finalName.replaceAll("\\s+", "_") : finalEmail)
                    .email(finalEmail)
                    .password(null)
                    .role(Role.USER)
                    .provider(finalProvider)
                    .providerId(finalProviderId)
                    .enabled(true)
                    .build();

            return userRepository.save(newUser);
        });

        return oauth2User;
    }
}