package pl.isa.javasmugglers.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.isa.javasmugglers.web.model.user.User;
import pl.isa.javasmugglers.web.service.RegistrationService;

@Controller
@AllArgsConstructor
public class RegistrationController {

    private RegistrationService registrationService;


    @GetMapping("/addnew")
    public String addNewTool(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/save")
    public String saveTool(@ModelAttribute("user") User user) {
        registrationService.register(user);
        return "redirect:/registrationsuccesfull";
    }
}
