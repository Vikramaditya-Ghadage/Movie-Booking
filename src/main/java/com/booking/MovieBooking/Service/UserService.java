package com.booking.MovieBooking.Service;

import com.booking.MovieBooking.Exception.ResourceNotFoundException;
import com.booking.MovieBooking.Model.User;
import com.booking.MovieBooking.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create a new user
    public User addUser(User user) {
        return userRepository.save(user);
    }

    // Get user by ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
