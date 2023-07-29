package pl.isa.javasmugglers.web.service;

import org.springframework.stereotype.Service;
import pl.isa.javasmugglers.web.model.ExamResult;
import pl.isa.javasmugglers.web.model.user.User;
import pl.isa.javasmugglers.web.repository.ExamResultsRepository;

import java.util.List;

@Service
public class ExamResultService {

    ExamResultsRepository examResultsRepository;

    public ExamResultService(ExamResultsRepository examResultsRepository) {
        this.examResultsRepository = examResultsRepository;
    }

    public ExamResult save(ExamResult examResult) {
        return examResultsRepository.save(examResult);
    }

    public List<ExamResult> findUserExamResults(User user) {
        return examResultsRepository.findAllByStudentId(user);

    }

    public int calculatePercentageScore(double userScore, double maxScore) {
        return (int) Math.round(userScore / maxScore * 100);
    }

    public void deleteByStudentId(long studentId) {
        examResultsRepository.deleteExamResultByStudentId(studentId);
    }

    public List<ExamResult> findAllByStudentsList(List<User> studentsList) {
        return examResultsRepository.findAllByStudentIdIn(studentsList);
    }


}
