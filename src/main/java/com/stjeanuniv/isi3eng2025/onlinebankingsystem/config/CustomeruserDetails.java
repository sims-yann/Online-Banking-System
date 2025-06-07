package com.stjeanuniv.isi3eng2025.onlinebankingsystem.config;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Admin;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Customer;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.User;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomeruserDetails implements UserDetailsService {

    @Autowired
    private final UserRepo user_repo;

    public CustomeruserDetails(UserRepo user_repo) {
        this.user_repo = user_repo;
    }

     @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = user_repo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("User"));

        if (user instanceof Admin){
           authorities.add(new SimpleGrantedAuthority("Admin"));
        } else if (user instanceof Customer) {
            authorities.add(new SimpleGrantedAuthority("Customer"));
        }

        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            true, true, true, true,
            authorities
        );
    }
}
