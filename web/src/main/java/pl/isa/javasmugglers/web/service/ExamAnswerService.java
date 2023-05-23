package pl.isa.javasmugglers.web.service;

import org.springframework.stereotype.Service;
import pl.isa.javasmugglers.web.model.ExamAnswer;
import pl.isa.javasmugglers.web.model.ExamQuestion;
import pl.isa.javasmugglers.web.repository.ExamAnswerRepository;
import pl.isa.javasmugglers.web.repository.ExamQuestionRepository;
import java.util.List;

@Service
public class ExamAnswerService {
    ExamAnswerRepository examAnswerRepository;
    ExamQuestionRepository examQuestionRepository;

    public ExamAnswerService(ExamAnswerRepository examAnswerRepository, ExamQuestionRepository examQuestionRepository) {
        this.examAnswerRepository = examAnswerRepository;
        this.examQuestionRepository = examQuestionRepository;
    }


    public List<ExamAnswer> findAllAnswersByQuestionID(Long id){
        return examAnswerRepository.findAllByQuestionId(examQuestionRepository.findById(id).orElseThrow()) ;
    }

}
