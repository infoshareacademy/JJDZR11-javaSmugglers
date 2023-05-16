package pl.isa.javasmugglers.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.isa.javasmugglers.web.model.ExamResult;

public interface ExamResultsRepository extends JpaRepository<ExamResult, Long> {
}
