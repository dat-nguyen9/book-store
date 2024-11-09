package com.book_store.service;

import com.book_store.entity.User;
import com.book_store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getById(int id) {
        return userRepository.getById(id);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
