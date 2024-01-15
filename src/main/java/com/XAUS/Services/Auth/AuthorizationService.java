package com.XAUS.Services.Auth;

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
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

//        User storedUser = userRepository.findByEmail(email);
//        if (storedUser != null) {
//            if (passwordEncoder.matches(user.password(), storedUser.getPassword())) {
//                return storedUser;
//            }
//        }

        return userRepository.findByEmail(email);
    }
}
