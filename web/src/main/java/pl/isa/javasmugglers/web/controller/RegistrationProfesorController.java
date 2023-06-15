package pl.isa.javasmugglers.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.isa.javasmugglers.web.registration.RegistrationRequest1;
import pl.isa.javasmugglers.web.registration.RegistrationRequest2;
import pl.isa.javasmugglers.web.service.RegistrationServiceProfessor;

@RestController
@RequestMapping(path = "registration/professor")
@AllArgsConstructor
public class RegistrationProfesorController {

    private RegistrationServiceProfessor registrationServiceProfessor;
@PostMapping
    public String register(@RequestBody RegistrationRequest1 request){
        return registrationServiceProfessor.register(request);
    }
}
