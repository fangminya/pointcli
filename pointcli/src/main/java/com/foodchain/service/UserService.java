package com.foodchain.service;

import com.foodchain.entity.User;
import com.foodchain.repository.BaseUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private BaseUserRepository repository;

    public void save(User user) {
        repository.save(user);
    }

    public User findByUserName(String userName) {
        return repository.findByUserName(userName);
    }
}
