package pl.isa.javasmugglers.web.service;

import org.springframework.stereotype.Service;
import pl.isa.javasmugglers.web.model.CourseRegistration;
import pl.isa.javasmugglers.web.model.user.User;
import pl.isa.javasmugglers.web.repository.CourseRegistrationRepository;

import java.util.List;

@Service
public class CourseRegistrationService {
    private CourseRegistrationRepository courseRegistrationRepository;

    public CourseRegistrationService(CourseRegistrationRepository courseRegistrationRepository) {
        this.courseRegistrationRepository = courseRegistrationRepository;
    }

    public List<CourseRegistration> findAllRegisteredCourses(User user){
       return courseRegistrationRepository.findAllByStudentId(user);
    }
    public void deleteByStudentId(Long studentId){
        courseRegistrationRepository.deleteCourseRegistrationByStudentId(studentId);
    }
}
