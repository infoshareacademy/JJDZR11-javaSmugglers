package pl.isa.javasmugglers.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.isa.javasmugglers.web.model.Course;
import pl.isa.javasmugglers.web.model.user.User;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByProfessorId (User professorId);

    @Query("SELECT c FROM courses c WHERE c.professorId.id = :professorId")
    List<Course> findCoursesByProfessorId(@Param("professorId") Long professorId);

}
