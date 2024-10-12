package com.mindspark.family_finances.services;

import com.mindspark.family_finances.model.UserCredentials;
import com.mindspark.family_finances.repository.UserCredentialsRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserCredentialsService {

    private UserCredentialsRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public UserCredentials registerUser(UserCredentials user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

    public UserCredentials authenticate(String email, String password) {
        UserCredentials userCredentials = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(password, userCredentials.getPassword())){
            throw new RuntimeException("Invalid password");
        }
        return userCredentials;
    }
}
