//package pl.isa.javasmugglers.web.Profesor;
//
//import org.springframework.stereotype.Service;
//import pl.isa.javasmugglers.web.model.*;
//import pl.isa.javasmugglers.web.repository.*;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//import pl.isa.javasmugglers.web.model.Course;
//import pl.isa.javasmugglers.web.model.Courses;
//import pl.isa.javasmugglers.web.model.Exam;
//import pl.isa.javasmugglers.web.repository.*;
//import pl.isa.javasmugglers.web.service.CourseService;
//
//import java.time.LocalDate;
//import java.time.Month;
//import java.util.Collection;
//import java.util.List;
//
//import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
//
//@Service
//public class ProfesorService {
//        public List<ProfesorMain> getProfesorMain() {
//
//
//
//                    )
//            public Collection<Exam> listAllExamsByProfessorId(Long professorId) {
//                Collection<Course> professorCoursesList = courseService.coursesListByProfessorId(professorId);
//            );
//        }
//    }
//
////        "id=" + id +
////        ", Course_Type='" + Course_Type + '\'' +
////        ", Descryption='" + Descryption + '\'' +
////        ", ECTS_Points=" + ECTS_Points +
////        ", End_Date=" + End_Date +
////        ", name='" + name + '\'' +
////        ", Start_Date=" + Start_Date
//
//
//
//    ProfesorService {
//        private final ProfesorRepository profesorRepository;
//        private final CourseRepository courseRepository;
//        private final UserRepository userRepository;
//        private CourseService courseService;
//
//
//        public ExamService(ExamRepository examRepository, CourseRepository courseRepository, UserRepository userRepository, CourseService courseService, ExamQuestionRepository examQuestionRepository, ExamAnswerRepository examAnswerRepository) {
//            this.examRepository = examRepository;
//            this.courseRepository = courseRepository;
//            this.userRepository = userRepository;
//            this.courseService = courseService;
//            this.examQuestionRepository = examQuestionRepository;
//            this.examAnswerRepository = examAnswerRepository;