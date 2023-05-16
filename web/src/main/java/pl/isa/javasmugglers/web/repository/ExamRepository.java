package pl.isa.javasmugglers.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.isa.javasmugglers.web.model.Exam;


public interface ExamRepository extends JpaRepository<Exam, Long> {
}
