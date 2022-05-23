package com.alikhver.web.security;

import com.alikhver.model.entity.User;
import com.alikhver.model.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service("customUserDetails")
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userService.findUserByLogin(login).orElseThrow(() -> {
            log.warn("UsernameNotFoundException is thrown");
            throw new UsernameNotFoundException("User doesn't exists");
        });
        return SecurityUser.fromUser(user);
    }
}
