package com.sts_LoginAuthentication.controller;

import com.sts_LoginAuthentication.entity.UserEntity;
import com.sts_LoginAuthentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserEntity());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") UserEntity user,
                           @RequestParam("photo") MultipartFile photoFile,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        try {
            Optional<UserEntity> existingUser = userRepo.findByUsername(user.getUsername());
            if (existingUser.isPresent()) {
                model.addAttribute("errorMessage", "Username/email already exists!");
                return "register";
            }

            if (!photoFile.isEmpty()) {
                Files.createDirectories(Paths.get(UPLOAD_DIR));
                String filename = System.currentTimeMillis() + "_" + photoFile.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR + filename);
                photoFile.transferTo(filePath.toFile());
                user.setPhotoUrl("/uploads/" + filename);
            } else {
                user.setPhotoUrl("/uploads/default.png");
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole("USER");
            userRepo.save(user);

            redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please log in.");
            return "redirect:/login";

        } catch (IOException e) {
            model.addAttribute("errorMessage", "Failed to upload photo");
            e.printStackTrace();
            return "register";
        }
    }
}
