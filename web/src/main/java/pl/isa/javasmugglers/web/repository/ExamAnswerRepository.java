package pl.isa.javasmugglers.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.isa.javasmugglers.web.model.ExamAnswer;

public interface ExamAnswerRepository extends JpaRepository<ExamAnswer, Long> {
}
