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

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private CourseRepository courseRepository;
    private UserRepository userRepository;
    private CourseSessionRepository courseSessionRepository;
    private CourseRegistrationRepository courseRegistrationeRepository;

    public CourseService(CourseRepository courseRepository, UserRepository userRepository,
                         CourseSessionRepository courseSessionRepository,
                         CourseRegistrationRepository courseRegistrationeRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.courseSessionRepository = courseSessionRepository;
        this.courseRegistrationeRepository = courseRegistrationeRepository;
    }

    public List<Course> coursesListByProfessorId(Long professorId) {
        User user = userRepository.findById(professorId).orElseThrow();
        List<Course> professorCoursesList = courseRepository.findAllByProfessorId(user);
        return professorCoursesList;
    }


    public List<CourseSession> coursesListByStudentId(Long studentId) {
        User user = userRepository.findById(studentId).orElseThrow();
        List<CourseRegistration> coursesByStudent = courseRegistrationeRepository.findAllByStudentId(user);
        return coursesByStudent.stream()
                .map(courseRegistration -> courseRepository.findById(courseRegistration.getId()).orElseThrow())
                .map(course -> courseSessionRepository.findAllByCourseId(course))
                .flatMap(c -> c.stream())
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "CourseService{" +
                "courseRepository=" + courseRepository +
                ", userRepository=" + userRepository +
                ", courseSessionRepository=" + courseSessionRepository +
                ", courseRegistrationeRepository=" + courseRegistrationeRepository +
                '}';
    }

    public List<CourseSession> coursesListByProfessorId2(Long professorId) {
        User user = userRepository.findById(professorId).orElseThrow();
        List<Course> coursesByProfessor = courseRepository.findAllByProfessorId(user);
        return coursesByProfessor.stream()
                .map(courseRegistration -> courseRepository.findById(courseRegistration.getId()).orElseThrow())
                .map(course -> courseSessionRepository.findAllByCourseId(course))
                .flatMap(c -> c.stream())
                .collect(Collectors.toList());
    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id){
        courseRepository.delete(courseRepository.findById(id).orElseThrow());
    }

}