package pl.isa.javasmugglers.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.isa.javasmugglers.web.model.Course;
import pl.isa.javasmugglers.web.model.CourseSession;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface CourseSessionRepository extends JpaRepository<CourseSession, Long> {
    List<CourseSession> findAllByCourseId(Course course);
    @Query("SELECT cs FROM courseSessions cs WHERE cs.sessionDate = :date AND cs.id IN :sessionIds")
    List<CourseSession> findAllBySessionDateAndIdIn(@Param("date") LocalDate date, @Param("sessionIds") List<Long> sessionIds);
    List<CourseSession> findAllBySessionDateAndIdIn(Date sessionDate, List<Long> sessionIds);

}


