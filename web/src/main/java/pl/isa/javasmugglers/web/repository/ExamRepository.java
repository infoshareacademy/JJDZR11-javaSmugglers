package pl.isa.javasmugglers.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.isa.javasmugglers.web.model.Course;
import pl.isa.javasmugglers.web.model.Exam;

import java.util.Collection;
import java.util.List;


@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findAllByCourseIdIn (Collection<Course> courseId);
}
