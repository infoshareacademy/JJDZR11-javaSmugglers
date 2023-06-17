package pl.isa.javasmugglers.web.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.isa.javasmugglers.web.model.ExamQuestion;
import pl.isa.javasmugglers.web.repository.ExamQuestionRepository;
import pl.isa.javasmugglers.web.repository.ExamRepository;

import java.util.List;

@Service
public class ExamQuestionService {
    ExamQuestionRepository examQuestionRepository;
    ExamRepository examRepository;

    public ExamQuestionService(ExamQuestionRepository examQuestionRepository, ExamRepository examRepository) {
        this.examQuestionRepository = examQuestionRepository;
        this.examRepository = examRepository;
    }


    public List<ExamQuestion> findAllQuestionByExamID(Long examID) {
        return examQuestionRepository.findAllByExamId(examRepository.findById(examID).orElseThrow());
    }

    public ExamQuestion findByID(Long id) {
        return examQuestionRepository.findById(id).orElseThrow();
    }

    public ExamQuestion saveQuestion(ExamQuestion examQuestion) {
        return examQuestionRepository.save(examQuestion);
    }

    public void deleteQuestion(Long questionID){
        examQuestionRepository.delete(findByID(questionID));
    }
}
