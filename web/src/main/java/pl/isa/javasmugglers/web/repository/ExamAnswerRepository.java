package pl.isa.javasmugglers.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.isa.javasmugglers.web.model.ExamAnswer;

@Repository
public interface ExamAnswerRepository extends JpaRepository<ExamAnswer, Long> {
}
