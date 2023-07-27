package pl.isa.javasmugglers.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.isa.javasmugglers.web.model.Course;
import pl.isa.javasmugglers.web.model.CourseSession;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CourseSessionRepository extends JpaRepository<CourseSession, Long> {
    List<CourseSession> findAllByCourseId(Course course);

    List<CourseSession> findAllByCourseIdIn(List<Course> courseList);

    @Query("SELECT cs FROM courseSessions cs " +
            "JOIN cs.courseId course " +
            "JOIN course.courseRegistrationList reg " +
            "WHERE cs.sessionDate = :date AND reg.studentId.id = :studentId")
    List<CourseSession> findStudentScheduleByDate(@Param("date") LocalDate date, @Param("studentId") Long studentId);

    @Query("SELECT cs FROM courseSessions cs " +
            "JOIN cs.courseId course " +
            "JOIN course.courseRegistrationList reg " +
            "WHERE cs.sessionDate BETWEEN :startDate AND :endDate AND reg.studentId.id IN :courseIds")
    List<CourseSession> findAllBySessionDateBetweenAndIdIn(@Param("startDate") LocalDate startDate,
                                                           @Param("endDate") LocalDate endDate,
                                                           @Param("courseIds") List<Long> courseIds);

}
