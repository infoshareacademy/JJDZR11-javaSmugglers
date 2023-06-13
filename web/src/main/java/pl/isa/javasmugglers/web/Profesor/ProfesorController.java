package pl.isa.javasmugglers.web.Profesor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

    @RestController
    @RequestMapping(path="/zajecia")
    public class ProfesorController { {

        private final ProfesorService profesorService;

        @Autowired
        public ProfesorController(ProfesorService profesorService) {
            this.profesorService = profesorService;        }

        @GetMapping
        public List<Profesor> getProfesors() {
            return profesorService.getProfesors();
        }
    }
}
