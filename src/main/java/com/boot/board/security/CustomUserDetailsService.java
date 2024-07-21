package com.boot.board.security;

import com.boot.board.domain.User;
import com.boot.board.mapper.UserMapper;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper usermapper;

    @Autowired
    public CustomUserDetailsService(UserMapper usermapper) {
        this.usermapper = usermapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usermapper.userExist(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // `uAuth` 값 확인
        String uAuth = user.getuAuth();
        System.out.println("User Authority: " + uAuth);
        
        boolean isCredentialsNonExpired = true; 
        user.setCredentialsNonExpired(isCredentialsNonExpired);

        // `uAuth`가 올바른 권한 형식인지 확인
        if (uAuth == null || uAuth.isEmpty()) {
            throw new IllegalArgumentException("User authority cannot be null or empty");
        }

        Collection<? extends GrantedAuthority> authorities = getAuthorities(uAuth);
        System.out.println("Authorities: " + authorities);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isAccountNonLocked(),
                authorities);
    }
    
    private Collection<? extends GrantedAuthority> getAuthorities(String uAuth) {
        if (uAuth == null || uAuth.isEmpty()) {
            throw new IllegalArgumentException("Authority cannot be null or empty");
        }
        return Collections.singletonList(new SimpleGrantedAuthority(uAuth));
    }
}
