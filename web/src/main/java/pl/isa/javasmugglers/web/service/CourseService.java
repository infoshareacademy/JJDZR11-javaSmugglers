package pl.isa.javasmugglers.web.service;

import org.springframework.stereotype.Service;
import pl.isa.javasmugglers.web.model.Course;
import pl.isa.javasmugglers.web.model.CourseRegistration;
import pl.isa.javasmugglers.web.model.CourseSession;
import pl.isa.javasmugglers.web.model.user.User;
import pl.isa.javasmugglers.web.repository.CourseRegistrationRepository;
import pl.isa.javasmugglers.web.repository.CourseRepository;
import pl.isa.javasmugglers.web.repository.CourseSessionRepository;
import pl.isa.javasmugglers.web.repository.UserRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseSessionRepository courseSessionRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;

    public CourseService(CourseRepository courseRepository, UserRepository userRepository,
                         CourseSessionRepository courseSessionRepository,
                         CourseRegistrationRepository courseRegistrationRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.courseSessionRepository = courseSessionRepository;
        this.courseRegistrationRepository = courseRegistrationRepository;
    }

    public List<Course> coursesListByProfessorId(Long professorId){
        User user = userRepository.findById(professorId).orElseThrow();
        return courseRepository.findAllByProfessorId(user);
    }

    @Override
    public String toString() {
        return "CourseService{" +
                "courseRepository=" + courseRepository +
                ", userRepository=" + userRepository +
                ", courseSessionRepository=" + courseSessionRepository +
                ", courseRegistrationRepository=" + courseRegistrationRepository +
                '}';
    }

}