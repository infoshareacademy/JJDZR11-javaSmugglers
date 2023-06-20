package pl.isa.javasmugglers.web.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.isa.javasmugglers.web.model.user.User;
import pl.isa.javasmugglers.web.repository.UserRepository;

import java.util.Random;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();



    private static final String USER_NOT_FOUND = "USER WITH EMAIL %S NOT FOUND.";

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByID (Long id){
       return userRepository.findById(id).orElseThrow();
    }
    public User findByAuthToken (String authToken){
        return userRepository.findByAuthToken(authToken);
    }






    public String save(User user){

        String encodedPassword = passwordEncoder
                .encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);
        return "rejestracja udana";
    }
    public String generateAuthToken (){
        Random r = new Random();
        int low = 10;
        int high = 100;
        Integer result = r.nextInt(high-low) + low;
        String authTokenInstance = result.toString();
        String s = passwordEncoder.encode(authTokenInstance);
        return s.replace("/", "q");

    }

}
