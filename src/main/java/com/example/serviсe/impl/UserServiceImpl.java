package com.example.serviсe.impl;

import com.example.exception.NoSuchObjectException;
import com.example.exception.WrongPasswordOrLoginException;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findById(s).orElseThrow(NoSuchObjectException::new);
    }

    public UserDetails findByUsernameAndPassword(String username, String password){
        UserDetails user = userRepository.findById(username).orElseThrow(WrongPasswordOrLoginException::new);
        if(!user.getPassword().equals(passwordEncoder.encode(password)))
            throw new WrongPasswordOrLoginException();
        return user;
    }
}
