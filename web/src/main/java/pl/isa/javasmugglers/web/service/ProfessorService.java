package pl.isa.javasmugglers.web.service;

import org.springframework.stereotype.Service;
import pl.isa.javasmugglers.web.model.Course;
import pl.isa.javasmugglers.web.model.CourseSession;
import pl.isa.javasmugglers.web.model.user.User;
import pl.isa.javasmugglers.web.repository.CourseRepository;
import pl.isa.javasmugglers.web.repository.CourseSessionRepository;
import pl.isa.javasmugglers.web.repository.UserRepository;

import java.util.List;

@Service
public class ProfessorService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CourseSessionRepository courseSessionRepository;

    public ProfessorService(UserRepository userRepository, CourseRepository courseRepository, CourseSessionRepository courseSessionRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.courseSessionRepository = courseSessionRepository;
    }

    public List<Course> getCoursesByProfessorId(Long professorId) {
        User professor = userRepository.findById(professorId).orElseThrow(() -> new IllegalArgumentException("Nie znaleziono profesora o podanym identyfikatorze"));
        return courseRepository.findAllByProfessorId(professor);
    }

    public List<CourseSession> getSessionsByCourseId(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("Nie znaleziono kursu o podanym identyfikatorze"));
        return courseSessionRepository.findAllByCourseId(course);
    }
}
