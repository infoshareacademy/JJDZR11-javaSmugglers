package pl.isa.javasmugglers.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.isa.javasmugglers.web.model.CourseSession;

public interface CourseSessionRepository extends JpaRepository<CourseSession, Long> {
}
