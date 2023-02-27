package com.likelion.Demojwt.Security;

import com.likelion.Demojwt.Entity.User;
import com.likelion.Demojwt.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.memory.UserAttribute;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username)  {
        //kiem tra xem user co ton tai trong database ko
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(user);
    }
    public UserDetails loadUserById(Long userId){
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent() == false){
            throw new UsernameNotFoundException("Long" + userId);
        }
        return new CustomUserDetails(user.get());
    }
}
