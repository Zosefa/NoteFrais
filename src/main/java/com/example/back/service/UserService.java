package com.example.back.service;

import com.example.back.model.Users;
import com.example.back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Users users = userRepository.findByEmail(email);
        if(users != null){
            Set<GrantedAuthority> authorities = users.getRoles()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_"+role.getRole().name()))
                    .collect(Collectors.toSet());
            var springUser = User.withUsername(users.getEmail())
                    .password(users.getPassword())
                    .authorities(authorities)
                    .build();
            return springUser;
        }
        return null;
    }
}
