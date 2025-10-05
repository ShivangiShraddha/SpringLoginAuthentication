package com.sts_LoginAuthentication.controller;

import com.sts_LoginAuthentication.entity.UserEntity;
import com.sts_LoginAuthentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getName())) {
            return "redirect:/login";
        }

        String username = authentication.getName();

        // Fetch user safely
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);
        UserEntity user = optionalUser.orElseGet(() -> {
            // Return a default user if not found
            UserEntity temp = new UserEntity();
            temp.setUsername(username);
            temp.setFirstName("User");
            temp.setLastName("");
            temp.setPhotoUrl("/uploads/default.png");
            temp.setMobileNumber("N/A");
            temp.setDateOfBirth(null);
            temp.setRole("USER");
            return temp;
        });

        model.addAttribute("user", user);

        return "home";
    }
}
