package pl.isa.javasmugglers.web.studentLogin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.isa.javasmugglers.web.model.Course;
import pl.isa.javasmugglers.web.model.CourseSession;
import pl.isa.javasmugglers.web.model.Exam;
import pl.isa.javasmugglers.web.repository.CourseRepository;
import pl.isa.javasmugglers.web.repository.ExamRepository;
import pl.isa.javasmugglers.web.service.CourseService;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {

//    private final StudentRepository studentRepository;
//    private CourseService courseService;
//    private CourseRepository courseRepository;
//
//    @Autowired
//    public StudentService(StudentRepository studentRepository) {
//        this.studentRepository = studentRepository;
//    }
//
//    public List<Student> getStudents() {
//            return studentRepository.findAll();
//        }
//
//    public Collection<CourseSession> listAllCoursesByProfessorId(Long professorId) {
//        Collection<Course> professorCoursesList = courseService.coursesListByProfessorId(professorId);
//        return courseRepository.findAllByCourseIdIn(professorCoursesList);
//    }
}
