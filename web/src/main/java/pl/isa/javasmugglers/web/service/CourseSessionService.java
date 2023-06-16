package pl.isa.javasmugglers.web.service;

import org.springframework.stereotype.Service;
import pl.isa.javasmugglers.web.model.Course;
import pl.isa.javasmugglers.web.model.CourseSession;
import pl.isa.javasmugglers.web.model.Exam;
import pl.isa.javasmugglers.web.repository.CourseSessionRepository;

import java.util.Collection;
import java.util.List;

@Service
public class CourseSessionService {
//    private static CourseService courseService;
//    private static CourseSessionRepository courseSessionRepository;
//    @Autowired
//    public CourseSessionService(CourseService courseService, CourseSessionRepository courseSessionRepository) {
//        this.courseService = courseService;
//        this.courseSessionRepository = courseSessionRepository;
//    }
//
//    public static Collection<CourseSession> listAllCourseSessionByStudentId(Long Id){
//        Collection<Course> studentCoursesList = courseService.coursesListByStudentId(Id);
//        return courseSessionRepository.findAllByCourseIdIn(studentCoursesList);
//    }

    public List<CourseSession> findAllByCourseSessionIdIn (List<Course> courseList){
        return courseSessionRepository.findAllByCourseIdIn(courseList);
    }
}
