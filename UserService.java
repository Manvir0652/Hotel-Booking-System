package com.example.hotelbooking.service;

import com.example.hotelbooking.model.User;
import com.example.hotelbooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public  List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User register(User user) {
        if (user.getRole() == null) {
            user.setRole("USER"); // default role
        }
        return userRepository.save(user);
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public void updateUser(User user) {
    userRepository.save(user); // Spring Data JPA repository
    
}
                 



}