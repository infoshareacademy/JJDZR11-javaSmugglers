package pl.isa.javasmugglers.web.service;

import org.springframework.stereotype.Service;
import pl.isa.javasmugglers.web.model.ExamResult;
import pl.isa.javasmugglers.web.repository.ExamResultsRepository;

@Service
public class ExamResultService {

    ExamResultsRepository examResultsRepository;

    public ExamResultService(ExamResultsRepository examResultsRepository) {
        this.examResultsRepository = examResultsRepository;
    }

    public ExamResult save(ExamResult examResult) {
        return examResultsRepository.save(examResult);
    }


}
