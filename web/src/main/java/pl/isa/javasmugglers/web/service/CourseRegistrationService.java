package pl.isa.javasmugglers.web.service;

import org.springframework.stereotype.Service;
import pl.isa.javasmugglers.web.model.Course;
import pl.isa.javasmugglers.web.model.CourseRegistration;
import pl.isa.javasmugglers.web.model.user.User;
import pl.isa.javasmugglers.web.repository.CourseRegistrationRepository;
import pl.isa.javasmugglers.web.repository.CourseRepository;
import pl.isa.javasmugglers.web.repository.UserRepository;

import java.util.List;

@Service
public class CourseRegistrationService {
    private final CourseRegistrationRepository courseRegistrationRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseRegistrationService(CourseRegistrationRepository courseRegistrationRepository,
                                     CourseRepository courseRepository,
                                     UserRepository userRepository) {
        this.courseRegistrationRepository = courseRegistrationRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public List<CourseRegistration> findAllRegisteredCourses(User user){
        return courseRegistrationRepository.findAllByStudentId(user);
    }

    public boolean registerCourse(Long studentId, Long courseId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + studentId));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course ID: " + courseId));

        boolean isAlreadyRegistered = courseRegistrationRepository.existsByStudentIdAndCourseId(student, course);

        if (isAlreadyRegistered) {
            return false; // Zwracamy false, jeśli student jest już zapisany na kurs
        } else {
            CourseRegistration courseRegistration = new CourseRegistration(student, course);
            courseRegistrationRepository.save(courseRegistration);
            return true; // Zwracamy true, jeśli zapis przebiegł pomyślnie
        }
    }
    public void deleteByStudentId(Long studentId){
        courseRegistrationRepository.deleteCourseRegistrationByStudentId(studentId);
    }
}

