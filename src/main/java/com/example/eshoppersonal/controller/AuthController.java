package com.example.eshoppersonal.controller;

import com.example.eshoppersonal.config.JwtUtil;
import com.example.eshoppersonal.entity.Role;
import com.example.eshoppersonal.entity.User;
import com.example.eshoppersonal.entity.UserRegistrationRequestDTO;
import com.example.eshoppersonal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email on juba kasutusel!");
        }

//        // ✅ Määra roll (kui pole määratud, siis vaikimisi USER)
//        Role role = request.getRole() != null ? request.getRole() : Role.USER;
//
//        User user = User.builder()
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .role(role)
//                .build();

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        return ResponseEntity.ok("Kasutaja loodud!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        System.out.println("Login attempt: " + user.getEmail());

        User dbUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Vale email!"));


        // 🛠 Kasutame passwordEncoder.matches() võrdlemiseks
        if (!passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            throw new BadCredentialsException("Vale parool!");
        }

        // ✅ Kui parool klapib, siis genereeri JWT token
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtUtil.generateToken(userDetails.getUsername());


        return ResponseEntity.ok(token);
    }
}
