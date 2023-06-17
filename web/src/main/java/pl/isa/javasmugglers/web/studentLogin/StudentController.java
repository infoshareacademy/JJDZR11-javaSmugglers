package pl.isa.javasmugglers.web.studentLogin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.isa.javasmugglers.web.model.*;
import pl.isa.javasmugglers.web.service.UserService;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

//@RestController
//@RequestMapping(path="/student_courses")
//public class StudentController {
//
//    private final StudentService studentService;
//
//    @Autowired
//    public StudentController(StudentService studentService) {
//        this.studentService = studentService;
//    }
//
//    @GetMapping
//    public List<Student> getStudents() {
//        return studentService.getStudents();
//    }
//}
