package com.example.serviсe.impl;

import com.example.exception.NoSuchEntityException;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.serviсe.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        log.info("Fetching all products");
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) {
        log.info("Fetching user with id {}", id);
        return userRepository.findById(id).orElseThrow(NoSuchEntityException::new);
    }
}
