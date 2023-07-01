package pl.isa.javasmugglers.web.service;

import org.springframework.stereotype.Service;
import pl.isa.javasmugglers.web.model.Course;
import pl.isa.javasmugglers.web.model.CourseRegistration;
import pl.isa.javasmugglers.web.model.CourseSession;
import pl.isa.javasmugglers.web.model.user.User;
import pl.isa.javasmugglers.web.repository.CourseRegistrationRepository;
import pl.isa.javasmugglers.web.repository.CourseSessionRepository;
import pl.isa.javasmugglers.web.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentScheduleService {
    private final CourseRegistrationRepository courseRegistrationRepository;
    private final CourseSessionRepository courseSessionRepository;
    private final UserRepository userRepository;

    public StudentScheduleService(CourseRegistrationRepository courseRegistrationRepository,
                                  CourseSessionRepository courseSessionRepository, UserRepository userRepository) {
        this.courseRegistrationRepository = courseRegistrationRepository;
        this.courseSessionRepository = courseSessionRepository;
        this.userRepository = userRepository;
    }

    public List<CourseSession> getStudentSchedule(Long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID"));

        List<CourseRegistration> studentRegistrations = courseRegistrationRepository
                .findAllByStudentId(student);

        List<CourseSession> studentSchedule = new ArrayList<>();

        for (CourseRegistration registration : studentRegistrations) {
            Course course = registration.getCourseId();
            List<CourseSession> courseSessions = courseSessionRepository
                    .findAllByCourseId(course);
            studentSchedule.addAll(courseSessions);
        }

        return studentSchedule;
    }
}

