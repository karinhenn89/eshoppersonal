package com.example.eshoppersonal.service;


import com.example.eshoppersonal.entity.Role;
import com.example.eshoppersonal.entity.User;
import com.example.eshoppersonal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(String email, String password, Role role) {
      User user = new User();
      user.setEmail(email);
      user.setPassword(passwordEncoder.encode(password));
      user.setRole(role);
      return userRepository.save(user);
    };

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
