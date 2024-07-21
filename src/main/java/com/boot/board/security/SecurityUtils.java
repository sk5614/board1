package com.boot.board.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.boot.board.service.UserService;


@Component
public class SecurityUtils {

    private final UserService userService;

    @Autowired
    public SecurityUtils(UserService userService) {
        this.userService = userService;
    }

    public void addAuthenticatedUserDetails(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            String username;
            String authorities;

            username = principal.toString();
            authorities = userService.userFind(username).getuAuth(); // UserService로부터 권한을 가져옴

            model.addAttribute("loggedInUser", username);
            model.addAttribute("authorities", authorities);
        }
    }
}
