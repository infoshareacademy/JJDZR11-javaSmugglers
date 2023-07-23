package pl.isa.javasmugglers.web.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.isa.javasmugglers.web.StudentConfig.ProfessorDTO;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.isa.javasmugglers.web.StudentConfig.ProfessorDTO;
import pl.isa.javasmugglers.web.model.*;
import pl.isa.javasmugglers.web.model.user.User;
import pl.isa.javasmugglers.web.repository.CourseRegistrationRepository;
import pl.isa.javasmugglers.web.repository.CourseRepository;
import pl.isa.javasmugglers.web.repository.UserRepository;
import pl.isa.javasmugglers.web.model.user.UserStatus;
import pl.isa.javasmugglers.web.model.user.UserType;
import pl.isa.javasmugglers.web.repository.UserRepository;
import pl.isa.javasmugglers.web.service.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
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
    @Autowired
    UserRepository userRepository;
    @Autowired
    CourseSessionService courseSessionService;
    @Autowired
    ProfessorService professorService;


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
        model.addAttribute("studentTimetable", courseSessions)
                .addAttribute("content", "studentTimetable")
                .addAttribute("studentId", user.getId())
                .addAttribute("user", user)
                .addAttribute("authToken", authToken);
        return "main";
    }


    @GetMapping("professorTimetable/{authToken}")
    String professorTimetable(@PathVariable("authToken") String authToken, Model model) {
        User user = userService.findByAuthToken(authToken);
        List<Course> courseList = courseService.coursesListByProfessorId(user.getId());
        List<CourseSession> courseSessionList = courseSessionService.getCourseSessionByCourseID(courseList);
        model.addAttribute("professorCourseList", courseList)
                .addAttribute("content", "professorCourseList")
                .addAttribute("professorId", user.getId())
                .addAttribute("user", user)
                .addAttribute("authToken", authToken)
                .addAttribute("courseSessionList", courseSessionList);
        return "main";
    }


    @PostMapping("addexam")
    public String addExam(@ModelAttribute Exam exam,
                          @RequestParam("startTimeString") String startTimeString,
                          @RequestParam("endTimeString") String endTimeString) {

        java.sql.Time startTime = java.sql.Time.valueOf(startTimeString + ":00");
        java.sql.Time endTime = java.sql.Time.valueOf(endTimeString + ":00");

        exam.setStartTime(startTime);
        exam.setEndTime(endTime);

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
    public String updateExam(@PathVariable("encodedID") String encodedID,
                             @ModelAttribute Exam exam,
                             @RequestParam("startTimeString") String startTimeString,
                             @RequestParam("endTimeString") String endTimeString) {
        Long decodedId = PathEncoderDecoder.decodePath(encodedID);

        long colonCountInStartTime = startTimeString.chars().filter(ch -> ch == ':').count();
        long colonCountInEndTime = endTimeString.chars().filter(ch -> ch == ':').count();
        if (colonCountInStartTime == 1) {
            startTimeString += ":00";
        }

        if (colonCountInEndTime == 1) {
            endTimeString += ":00";
        }
        java.sql.Time startTime = java.sql.Time.valueOf(startTimeString);
        java.sql.Time endTime = java.sql.Time.valueOf(endTimeString);


        Exam existingExam = examService.findById(decodedId);
        existingExam.setName(exam.getName());
        existingExam.setDescription(exam.getDescription());
        existingExam.setDuration(exam.getDuration());
        existingExam.setStartDate(exam.getStartDate());
        existingExam.setStartTime(startTime);
        existingExam.setEndDate(exam.getEndDate());
        existingExam.setEndTime(endTime);
        existingExam.setPassingThreshold(exam.getPassingThreshold());

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
    public String userDashboard(Model model, @PathVariable("authToken") String authToken) {
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


    @GetMapping("user-dashboard/courses/{id}")
    String courselist(@PathVariable("id") Long id, Model model) {
        model.addAttribute("CourseList", examService.listAllExamsByProfessorId(id))
                .addAttribute("profID", id)
                .addAttribute("content", "courseList");

        return "main";
    }

    @GetMapping("/menu")
    public String showMenu() {
        return "menu";
    }


    @GetMapping("/QKP85NW83DGZ2EWYXHVRJH1IDJ7SDCULSCJP460E8Z4DKQQQCROIVTGG0X1Y")
    public String adminDashboard(Model model) {
        model.addAttribute("content", "AdminDashboard");
        return "/main";
    }

    @GetMapping("/n3pNjrMZhvD53qMF35ukZn9UeJZdkJJy57SUdweuyy7hf6uiQEBFwtgZucr7")
    public String UserList(Model model) {
        List<User> userList = userService.getAllUsers().stream().filter(user -> user.getType() != UserType.ADMIN).toList();
        model.addAttribute("alluserlist", userList)
                .addAttribute("content", "UserList");
        return "/main";
    }

    @GetMapping("/eLL8RkECTB2BDSX43bZhRYH5329BrUbVtxcRavNetipcENgeXRfCcSKGcvuz")
    public String inactiveUserList(Model model) {
        List<User> userList = userService.getAllUsers().stream().filter(user -> user.getStatus() == UserStatus.WAITING_FOR_CONFIRMATION).toList();


        model.addAttribute("unactiveuserlist", userList)
                .addAttribute("content", "UnactiveUserList");

        return "/main";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "/login";
    }


    @GetMapping("/EQE79ZSU7CMWO218YANYX25PXY7973QYK9NPM2I0DSANLRW4A8QMFLM4ZING/{userId}")
    public String deleteThroughId(@PathVariable(value = "userId") long userId) {
        userService.deleteViaId(userId);
        return "redirect:/n3pNjrMZhvD53qMF35ukZn9UeJZdkJJy57SUdweuyy7hf6uiQEBFwtgZucr7";
    }

    @GetMapping("/pmgNnEdgHMLZKfxCbxWTy7uDGMmiwqqjDvQj8z3feArW9gJD2bpB9ppd6zMQ/{userId}")
    public String makeUserActive(@PathVariable(value = "userId") long userId) {
        UserStatus status = UserStatus.ACTIVE;
        userRepository.updateStatus(userId, status);
        return "redirect:/eLL8RkECTB2BDSX43bZhRYH5329BrUbVtxcRavNetipcENgeXRfCcSKGcvuz";
    }

    @GetMapping("/userinactive")
    public String ui() {
        return "/userinactive";
    }


    @GetMapping("addcourse/{authToken}")
    public String showAddCourseForm(Model model, @PathVariable("authToken") String authToken, HttpSession session) {
        User user = userService.findByAuthToken(authToken);

        session.setAttribute("user", user);

        model.addAttribute("course", new Course())
                .addAttribute("authToken", authToken)
                .addAttribute("content", "addCourse");
        return "main";
    }

    @PostMapping("addcourse")
    public String submitCourseForm(
            @ModelAttribute("course") Course course,
            @RequestParam("sessionFrequency") Integer frequency,
            @RequestParam("sessionStartTime") String startTime,
            @RequestParam("sessionEndTime") String endTime,
            @RequestParam("sessionLocation") String location,
            HttpSession session) {
        User user = (User) session.getAttribute("user");
        course.setProfessorId(user);
        courseService.saveCourse(course);
        courseSessionService.addMultipleSession(frequency, startTime, endTime, location, course);
        return "redirect:/professorTimetable/" + user.getAuthToken();
    }

    @GetMapping("edit-course/{encodedID}")
    public String editCourse(@PathVariable("encodedID") String encodedID, Model model) {
        Long decodedId = PathEncoderDecoder.decodePath(encodedID);
        Course course = courseService.findByID(decodedId);
        model.addAttribute("course", course)
                .addAttribute("content", "editcourse");
        return "main";
    }


    @PostMapping("edit-course/update-course/{encodedID}")
    public String updateCourse(@PathVariable("encodedID") String encodedID, @ModelAttribute Course course) {
        Long decodedId = PathEncoderDecoder.decodePath(encodedID);
        Course existingCourse = courseService.findByID(decodedId);
        existingCourse.setName(course.getName());
        existingCourse.setDescription(course.getDescription());
        existingCourse.setStartDate(course.getStartDate());
        existingCourse.setEndDate(course.getEndDate());
        existingCourse.setEctsPoints(course.getEctsPoints());
        existingCourse.setCourseType(course.getCourseType());
        courseService.saveCourse(existingCourse);
        String authToken = existingCourse.getProfessorId().getAuthToken();
        return "redirect:/professorTimetable/" + authToken;
    }

    @PostMapping("delete/course/{encodedID}")
    public String deleteCourse(@PathVariable("encodedID") String encodedID, @RequestParam("authToken") String authToken) {
        Long decodedID = PathEncoderDecoder.decodePath(encodedID);
        courseService.deleteCourse(decodedID);
        return "redirect:/professorTimetable/" + authToken;
    }

    @GetMapping("edit-courseSession/{encodedID}")
    public String editCourseSession(@PathVariable("encodedID") String encodedID, Model model) {
        Long decodedId = PathEncoderDecoder.decodePath(encodedID);
        CourseSession courseSession = courseSessionService.findByID(decodedId);
        model.addAttribute("courseSession", courseSession)
                .addAttribute("content", "editcoursesession");
        return "main";
    }


    @PostMapping("edit-courseSession/update-courseSession/{encodedID}")
    public String updateCourseSession(
            @PathVariable("encodedID") String encodedID,
            @ModelAttribute CourseSession courseSession,
            @RequestParam("startTimeString") String startTimeString,
            @RequestParam("endTimeString") String endTimeString) {


        Long decodedId = PathEncoderDecoder.decodePath(encodedID);
        CourseSession existingCourseSession = courseSessionService.findByID(decodedId);

        java.sql.Time startTime = java.sql.Time.valueOf(startTimeString + ":00");
        java.sql.Time endTime = java.sql.Time.valueOf(endTimeString + ":00");

        existingCourseSession.setStartTime(startTime);
        existingCourseSession.setEndTime(endTime);
        existingCourseSession.setLocation(courseSession.getLocation());
        existingCourseSession.setSessionDate(courseSession.getSessionDate());
        courseSessionService.saveCourseSession(existingCourseSession);
        String authToken = existingCourseSession.getCourseId().getProfessorId().getAuthToken();
        return "redirect:/professorTimetable/" + authToken;
    }

    @PostMapping("delete/courseSession/{encodedID}")
    public String deleteCourseSession(@PathVariable("encodedID") String encodedID, @RequestParam("authToken") String authToken) {
        Long decodedID = PathEncoderDecoder.decodePath(encodedID);
        courseSessionService.deleteCourseSession(decodedID);
        return "redirect:/professorTimetable/" + authToken;
    }

    @GetMapping("addcoursesession/{encodedCourseID}")
    public String showAddCourseSessionForm(
            Model model,
            @PathVariable("encodedCourseID") String encodedCourseID,
            HttpSession session) {
        Long decodedID = PathEncoderDecoder.decodePath(encodedCourseID);
        Course course = courseService.findByID(decodedID);
        session.setAttribute("course", course);

        model.addAttribute("courseSession", new CourseSession())
                .addAttribute("course", course)
                .addAttribute("content", "addcoursesession");
        return "main";
    }

    @PostMapping("addcoursesession")
    public String submitCourseSessionForm(
            @ModelAttribute CourseSession courseSession,
            @RequestParam("startTimeString") String startTimeString,
            @RequestParam("endTimeString") String endTimeString,
            HttpSession session) {

        Course course = (Course) session.getAttribute("course");


        java.sql.Time startTime = java.sql.Time.valueOf(startTimeString + ":00");
        java.sql.Time endTime = java.sql.Time.valueOf(endTimeString + ":00");
        courseSession.setStartTime(startTime);
        courseSession.setEndTime(endTime);
        courseSession.setCourseId(course);


        courseSessionService.saveCourseSession(courseSession);

        String authToken = courseSession.getCourseId().getProfessorId().getAuthToken();

        return "redirect:/professorTimetable/" + authToken;
    }

    @GetMapping("students/{authToken}/register")
    public String showProfessors(@PathVariable("authToken") String authToken, Model model) {
        User student = userService.findByAuthToken(authToken);
        Long studentID = student.getId();
        List<ProfessorDTO> professors = professorService.getAllProfessors();
        List<Long> registeredCourseIds = courseRegistrationService.findRegisteredCourseIdsByStudentId(studentID);

        List<Course> availableCourses = new ArrayList<>();
        for (ProfessorDTO professor : professors) {
            for (Course course : professor.getCourses()) {
                // Sprawdzamy, czy kurs nie jest już zarejestrowany przez studenta
                if (!registeredCourseIds.contains(course.getId())) {
                    // Sprawdzamy, czy kurs jest przypisany do odpowiedniego profesora
                    if (course.getProfessorId().getId().equals(professor.getId())) {
                        availableCourses.add(course);
                    }
                }
            }
        }

        model.addAttribute("professors", professors)
                .addAttribute("studentId", studentID)
                .addAttribute("availableCourses", availableCourses)
                .addAttribute("authToken", authToken);

        return "professors";
    }

    @PostMapping("students/{authToken}/courses/{courseId}/register")
    public String registerForCourse(@PathVariable("authToken") String authToken, @PathVariable("courseId") Long courseId, RedirectAttributes redirectAttributes) {
        User student = userService.findByAuthToken(authToken);
        Long studentID = student.getId();
        boolean registrationStatus = courseRegistrationService.registerCourse(studentID, courseId);

        if (!registrationStatus) {
            redirectAttributes.addFlashAttribute("message", "Student is already registered for this course");
        }
        return "redirect:/students/" + authToken + "/registered-courses";
    }


}