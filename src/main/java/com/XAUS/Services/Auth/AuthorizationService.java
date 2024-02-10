package com.XAUS.Services.Auth;

import com.XAUS.Configs.SecurityConfig.TokenService;

import com.XAUS.Models.User.User;
import com.XAUS.Repositories.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class AuthorizationService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return userRepository.findByEmail(email);
    }

    public User validateToken(String token){

        String validatedToken = tokenService.validateToken(token);
        return userRepository.findByEmail(validatedToken);

    }
    public String generateToken(User user){
        return tokenService.generateToken(user);
    }



}
