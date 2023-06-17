package pl.isa.javasmugglers.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.isa.javasmugglers.web.model.registration.RegistrationRequest;
import pl.isa.javasmugglers.web.service.RegistrationService;

@Controller
@AllArgsConstructor
public class RegistrationController {

    private RegistrationService registrationService;

    @RequestMapping("/register")
@PostMapping
    public String register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }
}
