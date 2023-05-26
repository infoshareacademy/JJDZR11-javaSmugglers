package pl.isa.javasmugglers.web.service;


import org.springframework.stereotype.Service;
import pl.isa.javasmugglers.web.model.*;
import pl.isa.javasmugglers.web.repository.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class ExamService {
    private final ExamRepository examRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private CourseService courseService;
    private ExamQuestionRepository examQuestionRepository;
    private ExamAnswerRepository examAnswerRepository;


    public ExamService(ExamRepository examRepository, CourseRepository courseRepository, UserRepository userRepository, CourseService courseService, ExamQuestionRepository examQuestionRepository, ExamAnswerRepository examAnswerRepository) {
        this.examRepository = examRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.courseService = courseService;
        this.examQuestionRepository = examQuestionRepository;
        this.examAnswerRepository = examAnswerRepository;
    }

    public List<Exam> listAllExamsByProfessorId(Long professorId) {
        Collection<Course> professorCoursesList = courseService.coursesListByProfessorId(professorId);
        return examRepository.findAllByCourseIdIn(professorCoursesList);
    }

    public Exam saveExam(Exam exam) {
        return examRepository.save(exam);
    }

    public Exam findById(Long id) {
        return examRepository.findById(id).orElseThrow();
    }

    public Exam findByExamQuestion(ExamQuestion examQuestion) {
        return examRepository.findAllByExamQuestionList(examQuestion);
    }

    public double calculateExamMaxScore(Exam exam) {
        double maxScore;
        List<ExamQuestion> examQuestionList = examQuestionRepository.findAllByExamId(exam);
        maxScore = examQuestionList.size();
        return maxScore;
    }

    public double calculateUserScore(UserExamAnswers userExamAnswers) {
        double score = 0.0;
        Map<Long, Map<Long, Boolean>> userAnswersMap = userExamAnswers.getAnswers();
        if (userAnswersMap != null) {
            for (Map.Entry<Long, Map<Long, Boolean>> entry : userAnswersMap.entrySet()) {
                Long questionId = entry.getKey();
                ExamQuestion question = examQuestionRepository.findById(questionId).orElseThrow();
                List<ExamAnswer> correctAnswersList = examAnswerRepository.findAllByQuestionId(question);
                Map<Long, Boolean> userAnswersList = entry.getValue();

                int correctCount = 0;
                int userCorrectCount = 0;
                boolean incorrectSelection = false;

                for (ExamAnswer correctAnswer : correctAnswersList) {
                    Boolean userSelected = userAnswersList.get(correctAnswer.getId());

                    if (correctAnswer.isCorrect()) {
                        correctCount++;
                        if (userSelected != null && userSelected) {
                            userCorrectCount++;
                        }
                    } else {
                        if (userSelected != null && userSelected) {
                            incorrectSelection = true;
                            break;
                        }
                    }
                }
                if (incorrectSelection) {
                    continue;
                }
                if (userCorrectCount == correctCount) {
                    score += 1.0;
                } else if (userCorrectCount > 0) {
                    score += ((double) userCorrectCount / correctCount);
                }
            }
            return score;
        } else return 0.00;
    }


}
