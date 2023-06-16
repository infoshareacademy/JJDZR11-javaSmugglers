package pl.isa.javasmugglers.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.isa.javasmugglers.web.model.Course;
import pl.isa.javasmugglers.web.model.Courses;
import pl.isa.javasmugglers.web.model.User;

import java.util.List;

public interface CoursesRepository extends JpaRepository <Courses, Long> {
    List<Course> findAllByProfessorId (User professorId);
}
