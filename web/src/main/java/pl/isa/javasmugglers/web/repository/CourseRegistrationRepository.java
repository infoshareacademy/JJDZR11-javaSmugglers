package pl.isa.javasmugglers.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.isa.javasmugglers.web.model.Course;
import pl.isa.javasmugglers.web.model.CourseRegistration;
import pl.isa.javasmugglers.web.model.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Long> {
    List<CourseRegistration> findAllByStudentId(User user);
    List<CourseRegistration> findAllByCourseId(Course courseId);
    boolean existsByStudentIdAndCourseId(User student, Course course);
    Optional<CourseRegistration> findAllByStudentIdAndCourseId(User student, Course course);
}
