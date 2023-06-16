package pl.isa.javasmugglers.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.isa.javasmugglers.web.model.Course;
import pl.isa.javasmugglers.web.model.CourseSession;

import java.util.Collection;
import java.util.List;

@Repository
public interface CourseSessionRepository extends JpaRepository<CourseSession, Long> {
    Collection<CourseSession> findAllByCourseIdIn(Collection<Course> course);
    List<CourseSession> findAllByCourseIdIn (List<Course> courseList);

}
