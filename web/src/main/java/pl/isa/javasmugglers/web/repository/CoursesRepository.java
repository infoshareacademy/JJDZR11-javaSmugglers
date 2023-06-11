package pl.isa.javasmugglers.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.isa.javasmugglers.web.model.Courses;

public interface CoursesRepository extends JpaRepository <Courses, Long> {

}
