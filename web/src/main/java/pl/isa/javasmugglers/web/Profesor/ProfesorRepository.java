package pl.isa.javasmugglers.web.Profesor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.isa.javasmugglers.web.model.Course;
import pl.isa.javasmugglers.web.model.Exam;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProfesorRepository extends JpaRepository<Course, Long> {
    Collection<Course> findAllByCourseIdIn (Collection<Course> course);
    Course findAllByCourseTypeAndIdIn(Course examQuestion);
     List<Course> findAllByCourseIdIn (List<Course> courseList);
}





//package pl.isa.javasmugglers.web.repository;
//
//        import org.springframework.data.jpa.repository.JpaRepository;
//        import org.springframework.stereotype.Repository;
//        import pl.isa.javasmugglers.web.model.Course;
//        import pl.isa.javasmugglers.web.model.CourseRegistration;
//        import pl.isa.javasmugglers.web.model.Exam;
//        import pl.isa.javasmugglers.web.model.ExamQuestion;
//
//        import java.util.Collection;
//        import java.util.List;
//
//
//@Repository
//public interface ExamRepository extends JpaRepository<Exam, Long> {
//    Collection<Exam> findAllByCourseIdIn (Collection<Course> course);
//    Exam findAllByExamQuestionList (ExamQuestion examQuestion);
//    List<Exam> findAllByCourseIdIn (List<Course> courseList);