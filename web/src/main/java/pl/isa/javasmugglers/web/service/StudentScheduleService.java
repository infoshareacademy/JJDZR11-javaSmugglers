package pl.isa.javasmugglers.web.service;

import org.springframework.stereotype.Service;
import pl.isa.javasmugglers.web.model.CourseRegistration;
import pl.isa.javasmugglers.web.model.CourseSession;
import pl.isa.javasmugglers.web.model.user.User;
import pl.isa.javasmugglers.web.repository.CourseRegistrationRepository;
import pl.isa.javasmugglers.web.repository.CourseSessionRepository;
import pl.isa.javasmugglers.web.repository.UserRepository;

import java.sql.Date;
import java.time.LocalDate;
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

    public List<CourseSession> getStudentScheduleByDate(Long studentId, LocalDate date) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + studentId));

        List<CourseRegistration> registrations = courseRegistrationRepository.findAllByStudentId(student);
        List<Long> registeredCourseIds = registrations.stream()
                .map(registration -> registration.getCourseId().getId())
                .toList();


        List<CourseSession> schedule = courseSessionRepository.findAllBySessionDateAndIdIn(date, registeredCourseIds);

        return schedule;
    }

    public List<CourseSession> getStudentScheduleByDateRange(Long studentId, LocalDate startDate, LocalDate endDate) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + studentId));

        List<CourseRegistration> registrations = courseRegistrationRepository.findAllByStudentId(student);
        List<Long> registeredCourseIds = registrations.stream()
                .map(registration -> registration.getCourseId().getId())
                .toList();

        List<CourseSession> schedule = courseSessionRepository.findAllBySessionDateBetweenAndIdIn(startDate, endDate, registeredCourseIds);

        return schedule;
    }

}

