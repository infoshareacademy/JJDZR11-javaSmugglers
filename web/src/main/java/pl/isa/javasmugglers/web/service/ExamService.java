package pl.isa.javasmugglers.web.service;


import org.springframework.stereotype.Service;
import pl.isa.javasmugglers.web.model.Course;
import pl.isa.javasmugglers.web.model.Exam;
import pl.isa.javasmugglers.web.model.User;
import pl.isa.javasmugglers.web.repository.CourseRepository;
import pl.isa.javasmugglers.web.repository.ExamRepository;
import pl.isa.javasmugglers.web.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ExamService {
    private final ExamRepository examRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private CourseService courseService;


    public ExamService(ExamRepository examRepository, CourseRepository courseRepository, UserRepository userRepository, CourseService courseService) {
        this.examRepository = examRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.courseService = courseService;
    }

    public List<Exam> listAllExamsByProfessorId(Long professorId) {
        Collection<Course> professorCoursesList = courseService.coursesListByProfessorId(professorId);
        return examRepository.findAllByCourseIdIn(professorCoursesList);
    }

    public Exam saveExam(Exam exam){
        return examRepository.save(exam);
    }

    public Exam findById(Long id){
        return examRepository.findById(id).orElseThrow();
    }


}
