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

        if (userExamAnswers != null) {
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

                // Sprawdzenie czy użytkownik nie zaznaczył żadnej odpowiedzi
                if (userAnswersList.isEmpty()) {
                    continue; // Pomiń obliczenia dla tego pytania
                }

                // Sprawdzenie czy odpowiedzi użytkownika zawierają wszystkie prawidłowe odpowiedzi
                boolean allCorrectAnswersSelected = correctAnswersIDs.containsAll(userAnswersList);
                boolean someCorrectAnswersSelected = correctAnswersIDs.contains(userAnswersList);

                // Sprawdzenie czy odpowiedzi użytkownika zawierają błędne odpowiedzi
                boolean hasWrongAnswersSelected = wrongAnswersIDs.stream()
                        .anyMatch(userAnswersList::contains);

                // Obliczanie punktów na podstawie odpowiedzi użytkownika
                if (allCorrectAnswersSelected && !hasWrongAnswersSelected) {
                    score += 1.0; // Użytkownik zaznaczył wszystkie prawidłowe odpowiedzi bez błędnych odpowiedzi
                } else if (!hasWrongAnswersSelected && someCorrectAnswersSelected) {
                    double fraction = (double) userAnswersList.size() / correctAnswersIDs.size();
                    System.out.println(fraction);
                    score += fraction; // Użytkownik zaznaczył część prawidłowych odpowiedzi (obliczenie ułamka)
                } else if (!hasWrongAnswersSelected) {
                    score += 0.0; // Użytkownik nie zaznaczył wszystkich prawidłowych odpowiedzi ani błędnych odpowiedzi
                }
            }
        }

        return score;
    }




}
