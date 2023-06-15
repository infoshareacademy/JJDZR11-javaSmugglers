package pl.isa.javasmugglers.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.isa.javasmugglers.web.registration.RegistrationRequest2;
import pl.isa.javasmugglers.web.service.RegistrationServiceStudent;

@RestController
@RequestMapping(path = "registration/student")
@AllArgsConstructor
public class RegistrationStudentController {

    private RegistrationServiceStudent registrationServiceStudent;
@PostMapping
    public String register(@RequestBody RegistrationRequest2 request){
        return registrationServiceStudent.register(request);
    }
}
