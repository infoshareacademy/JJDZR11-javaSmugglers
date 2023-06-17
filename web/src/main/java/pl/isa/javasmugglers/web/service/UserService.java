package pl.isa.javasmugglers.web.service;


import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.isa.javasmugglers.web.model.registration.RegistrationRequest;
import pl.isa.javasmugglers.web.model.user.User;
import pl.isa.javasmugglers.web.repository.UserRepository;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String signUpUser(User user) {
        boolean userExists = userRepository
                .findByEmail(user.getUsername())
                .isPresent();
        if (userExists) {
            throw new IllegalStateException("email already taken");
        }
        String encodedPassword = passwordEncoder
                .encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        // TODO:SEND CONFIRMATION And tokken
        return "Użytkownik  " + user.getFirstName() + " " + user.getLastName() + " został zarejestrowany. Proszę czekać na akceptacje użytkownika.";
    }

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

    public void register(RegistrationRequest user) throws Exception {

        //Let's check if user already registered with us
        if(checkIfUserExist(user.getEmail())){
            throw new Exception("User already exists for this email");
        }
        User userEntity = new User();
        BeanUtils.copyProperties(user, userEntity);
        userRepository.save(userEntity);
    }



    public boolean checkIfUserExist(String email) {
        return userRepository.findByEmail(email) !=null ? true : false;
    }

    private void encodePassword( User userEntity, RegistrationRequest user){
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
    }
    public String save(User user)
    {
        userRepository.save(user);
        return "rejestracja udana";
    }

}
