package pl.isa.javasmugglers.web.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.isa.javasmugglers.web.model.*;
import pl.isa.javasmugglers.web.model.user.User;
import pl.isa.javasmugglers.web.model.user.UserStatus;
import pl.isa.javasmugglers.web.model.user.UserType;
import pl.isa.javasmugglers.web.repository.UserRepository;
import pl.isa.javasmugglers.web.service.*;

import java.security.Principal;
import java.time.LocalDateTime;
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
    @Autowired
    UserRepository userRepository;
    @Autowired
    CourseSessionService courseSessionService;


    @GetMapping("examlist")
    String examlist(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("examlist", examService.listAllExamsByProfessorId(user.getId()))
                .addAttribute("profID", user.getId())
                .addAttribute("content", "examlist")
                .addAttribute("authToken", user.getAuthToken());
        return "main";
    }

    @GetMapping("studentTimetable")
    String studentTimetable(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        List<CourseSession> courseSessions = courseService.coursesListByStudentId(user.getId());
        model.addAttribute("studentTimetable", courseSessions)
                .addAttribute("content", "studentTimetable")
                .addAttribute("studentId", user.getId())
                .addAttribute("user", user)
                .addAttribute("authToken", user.getAuthToken());
        return "main";
    }


    @GetMapping("professor-courses")
    String professorCourses(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        List<Course> courseList = courseService.coursesListByProfessorId(user.getId());
        List<CourseSession> courseSessionList = courseSessionService.getCourseSessionByCourseID(courseList);
        model.addAttribute("professorCourseList", courseList)
                .addAttribute("content", "professorCourseList")
                .addAttribute("professorId", user.getId())
                .addAttribute("user", user)
                .addAttribute("authToken", user.getAuthToken())
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
        return "redirect:/examlist";
    }

    @GetMapping("addexam")
    public String showAddExamForm(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("exam", new Exam())
                .addAttribute("courseList", courseService.coursesListByProfessorId(user.getId()))
                .addAttribute("authToken", user.getAuthToken())
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
        return "redirect:/examlist";
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


    @GetMapping("startexam/{examId}")
    public String startExam(@PathVariable Long examId, Model model, Principal principal) {
        Exam exam = examService.findById(examId);
        User user = userService.findByEmail(principal.getName());
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

    @PostMapping("startexam/{examId}")
    public String submitAnswers(@PathVariable Long examId,
                                @ModelAttribute UserExamAnswers userExamAnswers,
                                Principal principal) {
        Exam exam = examService.findById(examId);
        User user = userService.findByEmail(principal.getName());
        Double maxScore = examService.calculateExamMaxScore(exam);
        Double userScore = examService.calculateUserScore(userExamAnswers);
        Integer percentage = examResultService.calculatePercentageScore(userScore, maxScore);

        ExamResult examResult = new ExamResult();
        examResult.setExamId(exam);
        examResult.setMaxExamScore(maxScore);
        examResult.setStudentScore(userScore);
        examResult.setStudentId(user);
        examResult.setExamDateTime(LocalDateTime.now());
        examResult.setPercentage(percentage);

        examResultService.save(examResult);
        return "redirect:/userexamresults";
    }


    @GetMapping("userexamresults")
    public String showExamResults(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        List<ExamResult> examResults = examResultService.findUserExamResults(user);
        model.addAttribute("examResults", examResults)
                .addAttribute("user", user)
                .addAttribute("content", "userexamresults")
                .addAttribute("authToken", user.getAuthToken());
        return "main";
    }


    @GetMapping("/showactiveexams")
    public String showActiveExams(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
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
    public String deleteExam(@PathVariable("encodedID") String encodedID) {
        Long decodedID = PathEncoderDecoder.decodePath(encodedID);
        examService.deleteExam(decodedID);
        return "redirect:/examlist";
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

    @GetMapping("DashboardProfessor")
    public String professorDashboard(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("user", user)
                .addAttribute("content", "DashboardProfessor")
                .addAttribute("authToken", user.getAuthToken());
        return "main";
    }

    @GetMapping("user-dashboard")
    public String userDashboard(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
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


    @GetMapping("user-dashboard/courses")
    String courselist(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        Long profID = user.getId();
        model.addAttribute("CourseList", examService.listAllExamsByProfessorId(profID))
                .addAttribute("profID", profID)
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


    @GetMapping("addcourse")
    public String showAddCourseForm(Model model, Principal principal, HttpSession session) {
        User user = userService.findByEmail(principal.getName());

        session.setAttribute("user", user);

        model.addAttribute("course", new Course())
                .addAttribute("authToken", user.getAuthToken())
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
        return "redirect:/professor-courses";
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
        return "redirect:/professor-courses";
    }

    @PostMapping("delete/course/{encodedID}")
    public String deleteCourse(@PathVariable("encodedID") String encodedID) {
        Long decodedID = PathEncoderDecoder.decodePath(encodedID);
        courseService.deleteCourse(decodedID);
        return "redirect:/professor-courses";
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

        existingCourseSession.setStartTime(startTime);
        existingCourseSession.setEndTime(endTime);
        existingCourseSession.setLocation(courseSession.getLocation());
        existingCourseSession.setSessionDate(courseSession.getSessionDate());
        courseSessionService.saveCourseSession(existingCourseSession);
        return "redirect:/professor-courses";
    }

    @PostMapping("delete/courseSession/{encodedID}")
    public String deleteCourseSession(@PathVariable("encodedID") String encodedID) {
        Long decodedID = PathEncoderDecoder.decodePath(encodedID);
        courseSessionService.deleteCourseSession(decodedID);
        return "redirect:/professor-courses";
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

        return "redirect:/professor-courses";
    }


    @GetMapping("my-courses")
    public String showMyCourses(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        List<Course> coursesList = courseService.coursesListByProfessorId(user.getId());

        System.out.println(coursesList.get(0).getName());
        model.addAttribute("coursesList", coursesList)
                .addAttribute("content", "myCoursesList");
        return "main";
    }


    @GetMapping("student-results-list")
    public String showStudentResultsList(@RequestParam("id") Long courseId, Model model, Principal principal) {
        User Professor = userService.findByEmail(principal.getName());
        List<User> courseRegisteredStudentList = courseRegistrationService.courseRegisteredStudentsList(courseId);
        List<ExamResult> courseExamResults = examResultService.findAllByStudentsList(courseRegisteredStudentList);
        Course course = courseService.findByID(courseId);

        System.out.println(courseRegisteredStudentList.get(0).getFirstName());
        System.out.println(courseExamResults.get(0).getExamId().getCourseId().getName());
        model.addAttribute("examResults", courseExamResults)
                .addAttribute("courseName", course.getName())
                .addAttribute("content", "courseExamResults");

        return "main";


    }

    @GetMapping("student-list")
    public String showStudentList(@RequestParam("id") Long courseId, Model model, Principal principal) {
        User Professor = userService.findByEmail(principal.getName());
        Course course = courseService.findByID(courseId);

        List<User> courseRegisteredStudentList = courseRegistrationService.courseRegisteredStudentsList(courseId);
        model.addAttribute("students", courseRegisteredStudentList)
                .addAttribute("courseName", course.getName())
                .addAttribute("content", "courseRegisteredStudents");

        return "main";


    }

}