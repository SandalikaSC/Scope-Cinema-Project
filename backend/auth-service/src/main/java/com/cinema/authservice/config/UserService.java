package com.cinema.authservice.config;


import com.cinema.authservice.entity.Token;
import com.cinema.authservice.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
@RequiredArgsConstructor
public class UserService {
    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            User customUser = (User) userDetails;
            System.out.println(customUser.getId());
            return customUser;
        }
            return null;
    }

}
