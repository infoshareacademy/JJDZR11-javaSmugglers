package pl.isa.javasmugglers.web.service;

import org.springframework.stereotype.Service;
import pl.isa.javasmugglers.web.model.Course;
import pl.isa.javasmugglers.web.model.User;
import pl.isa.javasmugglers.web.repository.CourseRepository;
import pl.isa.javasmugglers.web.repository.UserRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class CourseService {
    private CourseRepository courseRepository;
    private UserRepository userRepository;

    public CourseService(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public Collection<Course> coursesListByProfessorId(Long professorId){
        Optional<User> user = userRepository.findById(professorId);
        Collection<Course> professorCoursesList = courseRepository.findAllByProfessorId(user);
        return  professorCoursesList;
    }


}
