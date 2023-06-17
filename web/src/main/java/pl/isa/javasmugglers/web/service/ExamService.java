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

    public Collection<Exam> listAllExamsByProfessorId(Long professorId) {
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

        if (userExamAnswers.getAnswers() != null) {
            for (Map.Entry<Long, List<Long>> entry : userExamAnswers.getAnswers().entrySet()) {
                Long questionId = entry.getKey();
                ExamQuestion question = examQuestionRepository.findById(questionId).orElseThrow();
                List<ExamAnswer> answersList = examAnswerRepository.findAllByQuestionId(question);
                List<Long> correctAnswersIDs = answersList.stream()
                        .filter(ExamAnswer::isCorrect)
                        .map(ExamAnswer::getId)
                        .toList();
                List<Long> wrongAnswersIDs = answersList.stream()
                        .filter(answer -> !answer.isCorrect())
                        .map(ExamAnswer::getId)
                        .toList();
                List<Long> userAnswersList = entry.getValue();

                if (userAnswersList.isEmpty()) {
                    continue;
                }

                boolean hasWrongAnswersSelected = wrongAnswersIDs.stream()
                        .anyMatch(userAnswersList::contains);
                boolean someCorrectAnswersSelected = correctAnswersIDs.stream()
                        .anyMatch(userAnswersList :: contains);
                boolean allCorrectAnswersSelected = userAnswersList.containsAll(correctAnswersIDs);

                if(hasWrongAnswersSelected){
                    score += 0.0;
                } else if (someCorrectAnswersSelected){
                    double fraction = (double) userAnswersList.size() / correctAnswersIDs.size();
                    score += fraction;

                } else if (allCorrectAnswersSelected) {
                    score += 1.0;
                }
            } return Math.round(score * 10.0) / 10.0;
        } else return 0.0;
    }

    public List<Exam> findAllByCourseList (List<Course> courseList){
        return examRepository.findAllByCourseIdIn(courseList);
    }

}
