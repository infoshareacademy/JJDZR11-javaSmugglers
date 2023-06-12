package pl.isa.javasmugglers.web.studentLogin;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Component
public class StudentService {
        public List<Student> getStudents() {
            return List.of(
                    new Student(
                            1L,
                            "Dominik",
                            "dominik@gmail.com",
                            LocalDate.of(2000, Month.FEBRUARY, 23),
                            21
                    )

            );
        }
}
