package com.example.backend.controller;

import com.example.backend.dto.UserProfileDTO;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{username}")
    public ResponseEntity<UserProfileDTO> getUserProfile(@PathVariable String username) {
        User user = userRepository.findByUsername(username)
                      .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfileDTO profileDTO = new UserProfileDTO();
        profileDTO.setUsername(user.getUsername());
        profileDTO.setEmail(user.getEmail());
        profileDTO.setPhone(user.getPhone());
        profileDTO.setAddress(user.getAddress());

        return ResponseEntity.ok(profileDTO);
    }

    @PutMapping("/{username}")
    public ResponseEntity<String> updateUserProfile(@PathVariable String username, @RequestBody UserProfileDTO profileDTO) {
        User user = userRepository.findByUsername(username)
                      .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPhone(profileDTO.getPhone());
        user.setAddress(profileDTO.getAddress());

        userRepository.save(user);
        return ResponseEntity.ok("Profile updated successfully!");
    }
}

