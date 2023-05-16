package pl.isa.javasmugglers.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.isa.javasmugglers.web.model.CourseRegistration;

public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Long> {
}
