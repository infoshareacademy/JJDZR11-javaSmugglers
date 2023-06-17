package pl.isa.javasmugglers.web.Profesor;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.isa.javasmugglers.web.service.CourseService;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

//@Controller
//public class ProfesorController {
//
//    @Autowired
//    ProfesorService profesorService;
//
//    @GetMapping("zajecia/{id}")
//    public String ProfesorCorsesList(@PathVariable("id") Long id, Model model) {
//        model.addAttibute("Courslist", CourseService.listAllByProfessorId(id))
//                .addAttribute("content", "Courslist")
//                .addAttribute("profID", id);
//        return "ProfesorMenu";
//    }
//
//}

