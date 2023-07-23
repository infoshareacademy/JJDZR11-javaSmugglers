package pl.isa.javasmugglers.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.isa.javasmugglers.web.model.Course;
import pl.isa.javasmugglers.web.model.CourseSession;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public interface CourseSessionRepository extends JpaRepository<CourseSession, Long> {
    List<CourseSession> findAllByCourseId(Course course);

    List<CourseSession> findAllByCourseIdIn(List<Course> courseList);

    List<CourseSession> findAllBySessionDateAndIdIn(LocalDate sessionDate, List<Long> ids);

    List<CourseSession> findByCourseInAndSessionDate(List<Long> courseId, LocalDate sessionDate);

    List<CourseSession> findByCourseInAndSessionDateBetween(List<Long> courseId, LocalDate startDate, LocalDate endDate);
}
