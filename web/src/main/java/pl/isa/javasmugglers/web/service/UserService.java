package pl.isa.javasmugglers.web.service;


import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.isa.javasmugglers.web.model.user.User;
import pl.isa.javasmugglers.web.repository.UserRepository;




    @Service
    public class UserService implements UserDetailsService {

        @Autowired
        private UserRepository userRepo;

        @Autowired
        private BCryptPasswordEncoder passwordEncode;


        public User createUser(User user) {

            user.setPassword(passwordEncode.encode(user.getPassword()));

            return userRepo.save(user);
        }


        public boolean checkEmail(String email) {

            return userRepo.existsByEmail(email);
        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return null;
        }
    }
