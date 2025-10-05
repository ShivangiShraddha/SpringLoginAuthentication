package com.sts_LoginAuthentication.Security;

import com.sts_LoginAuthentication.entity.UserEntity;
import com.sts_LoginAuthentication.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    public CustomOAuth2SuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String photo = oAuth2User.getAttribute("picture");

        // Check if user already exists in DB
        Optional<UserEntity> existingUser = userRepository.findByUsername(email);

        if (existingUser.isEmpty()) {
            // Create new user if not exists
            UserEntity newUser = new UserEntity();
            newUser.setUsername(email);
            newUser.setPassword(""); // No password for OAuth2
            newUser.setFirstName(name != null ? name : "Google");
            newUser.setLastName("User");
            newUser.setRole("USER");
            newUser.setPhotoUrl(photo != null ? photo : "/uploads/default.png");
            newUser.setMobileNumber("N/A");
            newUser.setDateOfBirth(null);

            userRepository.save(newUser);
        }

        // Redirect to home page
        response.sendRedirect("/home");
    }
}
