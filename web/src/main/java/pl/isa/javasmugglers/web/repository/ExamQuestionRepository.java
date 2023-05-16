package pl.isa.javasmugglers.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.isa.javasmugglers.web.model.ExamQuestion;

public interface ExamQuestionRepository extends JpaRepository<ExamQuestion, Long    > {
}
