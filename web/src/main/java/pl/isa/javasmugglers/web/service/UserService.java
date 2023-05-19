package pl.isa.javasmugglers.web.service;

import pl.isa.javasmugglers.web.repository.UserRepository;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
