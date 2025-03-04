package com.vinicius.web_posting.Service;

import com.vinicius.web_posting.Model.User;
import com.vinicius.web_posting.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {

        return userRepository.findAll();

    }

    public Optional<User> getUserById(Long id) {

        return userRepository.findById(id);

    }

    public User createUser(User user) {

        return userRepository.save(user);

    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
