package pl.isa.javasmugglers.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.isa.javasmugglers.web.model.*;
import pl.isa.javasmugglers.web.model.user.User;
import pl.isa.javasmugglers.web.service.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Controller
public class MainController {

    @Autowired
    ExamService examService;
    @Autowired
    CourseService courseService;
    @Autowired
    ExamQuestionService examQuestionService;
    @Autowired
    ExamAnswerService examAnswerService;
    @Autowired
    UserService userService;
    @Autowired
    ExamResultService examResultService;
    @Autowired
    CourseRegistrationService courseRegistrationService;

    @GetMapping("examlist/{authToken}")
    String examlist(@PathVariable("authToken") String authToken, Model model) {
        User user = userService.findByAuthToken(authToken);
        model.addAttribute("examlist", examService.listAllExamsByProfessorId(user.getId()))
                .addAttribute("profID", user.getId())
                .addAttribute("content", "examlist")
                .addAttribute("authToken", user.getAuthToken());

        return "main";
    }

    @GetMapping("studentTimetable/{authToken}")
    String studentTimetable(@PathVariable("authToken") String authToken, Model model) {
        User user = userService.findByAuthToken(authToken);
        List<CourseSession> courseSessions = courseService.coursesListByStudentId(user.getId());
        System.out.println(courseSessions);
        model.addAttribute("studentTimetable", courseSessions)
                .addAttribute("content", "studentTimetable")
                .addAttribute("studentId", user.getId())
                .addAttribute("user", user)
                .addAttribute("authToken", authToken);
        return "main";
    }


    @GetMapping("professorTimetable/{authToken}")
    String professorTimetable(@PathVariable("authToken")String authToken, Model model) {
        User user = userService.findByAuthToken(authToken);
        List<Course> courseList = courseService.coursesListByProfessorId(user.getId());
        System.out.println(courseList);
        model.addAttribute("professorCourseList", courseList)
                .addAttribute("content", "professorCourseList")
                .addAttribute("professorId", user.getId())
                .addAttribute("user", user)
                .addAttribute("authToken", authToken);
        return "main";
    }


    @PostMapping("addexam")
    public String addExam(@ModelAttribute Exam exam) {
        examService.saveExam(exam);
        String authToken = exam.getCourseId().getProfessorId().getAuthToken();
        return "redirect:/examlist/" + authToken;
    }

    @GetMapping("addexam/{authToken}")
    public String showAddExamForm(Model model, @PathVariable("authToken") String authToken) {
        User user = userService.findByAuthToken(authToken);
        model.addAttribute("exam", new Exam())
                .addAttribute("courseList", courseService.coursesListByProfessorId(user.getId()))
                .addAttribute("authToken", authToken)
                .addAttribute("content", "addexam");
        return "main";
    }

    @GetMapping("edit-exam/{encodedID}")
    public String editExam(@PathVariable("encodedID") String encodedID, Model model) {
        Long decodedId = PathEncoderDecoder.decodePath(encodedID);
        Exam exam = examService.findById(decodedId);
        model.addAttribute("exam", exam)
                .addAttribute("courseList",
                        courseService.coursesListByProfessorId(exam.getCourseId().getProfessorId().getId()))
                .addAttribute("content", "editexam");
        return "main";
    }

    @PostMapping("edit-exam/update-exam/{encodedID}")
    public String updateExam(@PathVariable("encodedID") String encodedID, @ModelAttribute Exam exam) {
        Long decodedId = PathEncoderDecoder.decodePath(encodedID);
        Exam existingExam = examService.findById(decodedId);
        existingExam.setName(exam.getName());
        existingExam.setDescription(exam.getDescription());
/*
        existingExam.setStatus(exam.getStatus());
*/
        examService.saveExam(existingExam);
        String authToken = exam.getCourseId().getProfessorId().getAuthToken();
        return "redirect:/examlist/" + authToken;
    }

    @GetMapping("questionlist/{encodedID}")
    public String questionList(@PathVariable("encodedID") String encodedID, Model model) {
        Long decodedId = PathEncoderDecoder.decodePath(encodedID);
        List<ExamQuestion> questionList = examQuestionService.findAllQuestionByExamID(decodedId);
        String profID = examService.findById(decodedId).getCourseId().getProfessorId().getAuthToken();
        String examID = PathEncoderDecoder.encodePath(examService.findById(decodedId).getId());
        model.addAttribute("questionList", questionList)
                .addAttribute("profId", profID)
                .addAttribute("examID", examID)
                .addAttribute("content", "questionlist");
        return "main";
    }

    @GetMapping("edit-question/{encodedID}")
    public String editQuestion(@PathVariable("encodedID") String encodedID, Model model) {
        Long decodedId = PathEncoderDecoder.decodePath(encodedID);
        ExamQuestion examQuestion = examQuestionService.findByID(decodedId);
        model.addAttribute("examQuestion", examQuestion)
                .addAttribute("content", "editquestion");
        return "main";
    }


    @PostMapping("edit-question/update-question/{encodedID}")
    public String updateQuestion(@PathVariable("encodedID") String encodedID, @ModelAttribute ExamQuestion examQuestion) {
        Long decodedId = PathEncoderDecoder.decodePath(encodedID);
        ExamQuestion existingQuestion = examQuestionService.findByID(decodedId);
        existingQuestion.setQuestionText(examQuestion.getQuestionText());
        existingQuestion.setType(examQuestion.getType());
        examQuestionService.saveQuestion(existingQuestion);
        Long currentExamId = existingQuestion.getExamId().getId();

        return "redirect:/questionlist/" + PathEncoderDecoder.encodePath(currentExamId);
    }

    @GetMapping("edit-answers/{encodedID}")
    public String editAnswers(@PathVariable("encodedID") String encodedID, Model model) {
        Long decodedId = PathEncoderDecoder.decodePath(encodedID);
        ExamQuestion examQuestion = examQuestionService.findByID(decodedId);
        List<ExamAnswer> examAnswerList = examAnswerService.findAllAnswersByQuestionID(decodedId);
        ExamAnswerWrapper examAnswerWrapper = new ExamAnswerWrapper();
        examAnswerWrapper.setExamAnswers(examAnswerList);
        List<Character> alphabet = IntStream.rangeClosed('a', 'z')
                .mapToObj(c -> (char) c)
                .toList();
        model.addAttribute("examQuestion", examQuestion)
                .addAttribute("examAnswers", examAnswerWrapper)
                .addAttribute("alphabet", alphabet)
                .addAttribute("content", "editanswers");


        return "main";
    }


    @PostMapping("update-answers")
    public String updateAnswers(@ModelAttribute("examAnswers") ExamAnswerWrapper examAnswerWrapper) {
        for (ExamAnswer examAnswer : examAnswerWrapper.getExamAnswers()) {
            examAnswerService.saveAnswer(examAnswer);
        }

        ExamQuestion questionID = examAnswerWrapper.getExamAnswers().get(0).getQuestionId();
        Long currentExamID = examService.findByExamQuestion(questionID).getId();
        String encodedCurrentExamID = PathEncoderDecoder.encodePath(currentExamID);
        return "redirect:/questionlist/" + encodedCurrentExamID;
    }

    @GetMapping("addquestion/{encodedID}")
    public String showAddQuestionForm(@PathVariable("encodedID") String encodedID, Model model) {
        Long decodedId = PathEncoderDecoder.decodePath(encodedID);
        Exam exam = examService.findById(decodedId);
        ExamQuestion question = new ExamQuestion();
        question.setExamId(exam);
        model.addAttribute("question", question);
        model.addAttribute("exam", exam)
                .addAttribute("content", "addquestion");
        return "main";
    }

    @PostMapping("addquestion/{encodedID}")
    public String saveQuestion(@PathVariable("encodedID") String encodedID, ExamQuestion question, @RequestParam("answers[]") String[] answers, @RequestParam("isCorrect") List<Integer> correctAnswers, Model model) {
        Long decodedId = PathEncoderDecoder.decodePath(encodedID);
        Exam exam = examService.findById(decodedId);
        question.setExamId(exam);
        examQuestionService.saveQuestion(question);

        for (int i = 0; i < answers.length; i++) {
            ExamAnswer answer = new ExamAnswer();
            answer.setQuestionId(question);
            answer.setAnswerText(answers[i]);
            answer.setCorrect(correctAnswers.contains(i));
            examAnswerService.saveAnswer(answer);
        }

        model.addAttribute("exam", exam);
        return "redirect:/questionlist/" + encodedID;
    }


    @GetMapping("startexam/{authToken}/{examId}")
    public String startExam(@PathVariable Long examId, @PathVariable String authToken, Model model) {
        Exam exam = examService.findById(examId);
        User user = userService.findByAuthToken(authToken);
        UserExamAnswers userExamAnswers = new UserExamAnswers();


        model.addAttribute("exam", exam)
                .addAttribute("examQuestionList", exam.getExamQuestionList())
                .addAttribute("user", user)
                .addAttribute("remainingTime", exam.getDuration())
                .addAttribute("answers", userExamAnswers)
                .addAttribute("content", "exam")
                .addAttribute("authToken", user.getAuthToken());
        return "main";

    }

    @PostMapping("startexam/{authToken}/{examId}")
    public String submitAnswers(@PathVariable Long examId, @PathVariable String authToken,
                                @ModelAttribute UserExamAnswers userExamAnswers) {
        Exam exam = examService.findById(examId);
        User user = userService.findByAuthToken(authToken);
        Double maxScore = examService.calculateExamMaxScore(exam);
        Double userScore = examService.calculateUserScore(userExamAnswers);

        ExamResult examResult = new ExamResult();
        examResult.setExamId(exam);
        examResult.setMaxExamScore(maxScore);
        examResult.setStudentScore(userScore);
        examResult.setStudentId(user);

        examResultService.save(examResult);
        return "redirect:/userexamresults/" + authToken;
    }


    @GetMapping("userexamresults/{authToken}")
    public String showExamResults(Model model, @PathVariable("authToken") String authToken) {
        User user = userService.findByAuthToken(authToken);
        List<ExamResult> examResults = examResultService.findUserExamResults(user);
        List<Integer> percentageScores = new ArrayList<>();
        for (ExamResult result : examResults) {
            int percentageScore = examResultService.calculatePercentageScore(
                    result.getStudentScore(),
                    result.getMaxExamScore());
            percentageScores.add(percentageScore);
        }
        model.addAttribute("examResults", examResults)
                .addAttribute("percentageScores", percentageScores)
                .addAttribute("user", user)
                .addAttribute("content", "userexamresults")
                .addAttribute("authToken", user.getAuthToken());
        return "main";
    }


    @GetMapping("/showactiveexams/{authToken}")
    public String showActiveExams(Model model, @PathVariable("authToken") String authToken) {
        User user = userService.findByAuthToken(authToken);
        List<CourseRegistration> registrations = courseRegistrationService.findAllRegisteredCourses(user);
        List<Course> registeredCourses = registrations.stream().map(CourseRegistration::getCourseId).toList();
        List<Exam> allRegisteredExams = examService.findAllByCourseList(registeredCourses);
        List<Exam> takenExams = examResultService.findUserExamResults(user).stream().map(ExamResult::getExamId).toList();

        List<Exam> examsToTake = allRegisteredExams.stream()
                .filter(exam -> LocalDateTime.now().isAfter(LocalDateTime.of(exam.getStartDate().toLocalDate(), exam.getStartTime().toLocalTime())) &&
                        LocalDateTime.now().isBefore(LocalDateTime.of(exam.getEndDate().toLocalDate(), exam.getEndTime().toLocalTime())) &&
                takenExams.stream().noneMatch(takenExam -> takenExam.getId().equals(exam.getId())))
                .toList();


        model.addAttribute("exams", examsToTake)
                .addAttribute("user", user)
                .addAttribute("content", "userexamlist")
                .addAttribute("authToken", user.getAuthToken());


        return "main";
    }

    @PostMapping("delete/exam/{encodedID}")
    public String deleteExam(@PathVariable("encodedID") String encodedID, @RequestParam("authToken") String authToken) {
        Long decodedID = PathEncoderDecoder.decodePath(encodedID);
        examService.deleteExam(decodedID);
        return "redirect:/examlist/" + authToken;
    }

    @PostMapping("delete/question/{encodedID}")
    public String deleteQuestion(@PathVariable("encodedID") String encodedID, @RequestParam("examID") String ecodedExamID) {
        Long decodedID = PathEncoderDecoder.decodePath(encodedID);
        examAnswerService.deleteAswersByQuestionID(decodedID);
        examQuestionService.deleteQuestion(decodedID);
        return "redirect:/questionlist/" + ecodedExamID;
    }


    @GetMapping("/")
    public String home() {
        return "homepage";
    }

    @GetMapping("/registrationsuccesfull")
    public String registrationSuccesfullPage() {
        return "registrationsuccesfull";
    }

    @GetMapping("DashboardProfessor/{authToken}")
    public String professorDashboard(Model model, @PathVariable("authToken") String authToken) {
        User user = userService.findByAuthToken(authToken);
        model.addAttribute("user", user)
                .addAttribute("content", "DashboardProfessor")
                .addAttribute("authToken", user.getAuthToken());
        return "main";
    }

    @GetMapping("user-dashboard/{authToken}")
    public String userDashboard(Model model, @PathVariable("authToken")  String authToken) {
        User user = userService.findByAuthToken(authToken);
        model.addAttribute("user", user)
                .addAttribute("content", "user-dashboard")
                .addAttribute("authToken", user.getAuthToken());
        return "main";
    }


    @GetMapping("/registrationFailed")
    public String registrationFailedPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "/rf";
    }

    // jakiś nieużywany kod, chyba Dominika
/*
    @GetMapping("user-dashboard/courses/{id}")
    String courselist(@PathVariable("id") Long id, Model model) {
        model.addAttribute("CourseList", examService.listAllExamsByProfessorId(id))
                .addAttribute("profID", id)
                .addAttribute("content", "courseList");

        return "main";
    }*/



    @GetMapping("/menu")
    public String showMenu() {
        return "menu";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "/login";
    }

}

