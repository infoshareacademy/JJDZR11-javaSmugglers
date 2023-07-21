package pl.isa.javasmugglers.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.isa.javasmugglers.web.model.*;
import pl.isa.javasmugglers.web.model.user.User;
import pl.isa.javasmugglers.web.model.user.UserStatus;
import pl.isa.javasmugglers.web.model.user.UserType;
import pl.isa.javasmugglers.web.repository.UserRepository;
import pl.isa.javasmugglers.web.repository.CourseRegistrationRepository;
import pl.isa.javasmugglers.web.repository.CourseRepository;
import pl.isa.javasmugglers.web.repository.UserRepository;
import pl.isa.javasmugglers.web.service.*;

import java.time.LocalDate;
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

    @GetMapping("examlist/{id}")
    String examlist(@PathVariable("id") Long id, Model model) {
        model.addAttribute("examlist", examService.listAllExamsByProfessorId(id))
                .addAttribute("content", "examlist")
                .addAttribute("profID", id)
                .addAttribute("content", "examlist");

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
    String professorTimetable(@PathVariable("authToken") String authToken, Model model) {
        User user = userService.findByAuthToken(authToken);
        List<Course> courseList = courseService.coursesListByProfessorId(user.getId());
        List<CourseSession> courseSessionList = courseSessionService.getCourseSessionByCourseID(courseList);
        model.addAttribute("professorCourseList", courseList)
                .addAttribute("content", "professorCourseList")
                .addAttribute("professorId", id)
                .addAttribute("user", user);
        return "main";
    }


    @PostMapping("addexam")
    public String addExam(@ModelAttribute Exam exam) {
        examService.saveExam(exam);
        Long activeUserId = exam.getCourseId().getProfessorId().getId();
        return "redirect:/examlist/" + activeUserId;
    }

    @GetMapping("addexam/{id}")
    public String showAddExamForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("exam", new Exam())
                .addAttribute("courseList", courseService.coursesListByProfessorId(id))
                .addAttribute("content", "addexam");
        return "main";
    }

    @GetMapping("edit-exam/{id}")
    public String editExam(@PathVariable("id") Long id, Model model) {
        Exam exam = examService.findById(id);
        model.addAttribute("exam", exam)
                .addAttribute("courseList",
                        courseService.coursesListByProfessorId(exam.getCourseId().getProfessorId().getId()))
                .addAttribute("content", "editexam");
        return "main";
    }

    @PostMapping("edit-exam/update-exam/{id}")
    public String updateExam(@PathVariable("id") Long id, @ModelAttribute Exam exam) {
        Exam existingExam = examService.findById(id);
        existingExam.setName(exam.getName());
        existingExam.setDescription(exam.getDescription());
        existingExam.setStatus(exam.getStatus());
        examService.saveExam(exam);
        Long profId = exam.getCourseId().getProfessorId().getId();
        return "redirect:/examlist/" + profId;
    }

    @GetMapping("questionlist/{id}")
    public String questionList(@PathVariable("id") Long id, Model model) {
        List<ExamQuestion> questionList = examQuestionService.findAllQuestionByExamID(id);
        Long profID = examService.findById(id).getCourseId().getProfessorId().getId();
        Long examID = examService.findById(id).getId();
        model.addAttribute("questionList", questionList)
                .addAttribute("profId", profID)
                .addAttribute("examID", examID)
                .addAttribute("content", "questionlist");
        return "main";
    }

    @GetMapping("edit-question/{id}")
    public String editQuestion(@PathVariable("id") Long id, Model model) {
        ExamQuestion examQuestion = examQuestionService.findByID(id);
        model.addAttribute("examQuestion", examQuestion)
                .addAttribute("content", "editquestion");
        return "main";
    }


    @PostMapping("edit-question/update-question/{id}")
    public String updateQuestion(@PathVariable("id") Long id, @ModelAttribute ExamQuestion examQuestion) {
        ExamQuestion existingQuestion = examQuestionService.findByID(id);
        existingQuestion.setQuestionText(examQuestion.getQuestionText());
        existingQuestion.setType(examQuestion.getType());
        examQuestionService.saveQuestion(existingQuestion);
        Long currentExamId = existingQuestion.getExamId().getId();
        return "redirect:/questionlist/" + currentExamId;
    }

    @GetMapping("edit-answers/{id}")
    public String editAnswers(@PathVariable("id") Long id, Model model) {
        ExamQuestion examQuestion = examQuestionService.findByID(id);
        List<ExamAnswer> examAnswerList = examAnswerService.findAllAnswersByQuestionID(id);
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


    @PostMapping("update-answers/{id}")
    public String updateAnswers(@PathVariable("id") Long id, @ModelAttribute("examAnswers") ExamAnswerWrapper examAnswerWrapper) {
        for (ExamAnswer examAnswer : examAnswerWrapper.getExamAnswers()) {
            examAnswerService.saveAnswer(examAnswer);
        }

        ExamQuestion questionID = examAnswerWrapper.getExamAnswers().get(0).getQuestionId();
        Long currentExamID = examService.findByExamQuestion(questionID).getId();

        return "redirect:/questionlist/" + currentExamID;
    }

    @GetMapping("addquestion/{examId}")
    public String showAddQuestionForm(@PathVariable("examId") Long examId, Model model) {
        Exam exam = examService.findById(examId);
        ExamQuestion question = new ExamQuestion();
        question.setExamId(exam);
        model.addAttribute("question", question);
        model.addAttribute("exam", exam)
                .addAttribute("content", "addquestion");
        return "main";
    }

    @PostMapping("addquestion/{examId}")
    public String saveQuestion(@PathVariable("examId") Long examId, ExamQuestion question, @RequestParam("answers[]") String[] answers, @RequestParam("isCorrect") List<Integer> correctAnswers, Model model) {
        Exam exam = examService.findById(examId);
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
        return "redirect:/questionlist/" + examId;
    }


    @GetMapping("startexam/{userId}/{examId}")
    public String startExam(@PathVariable Long examId, @PathVariable Long userId, Model model) {
        Exam exam = examService.findById(examId);
        User user = userService.findByID(userId);
        UserExamAnswers userExamAnswers = new UserExamAnswers();


        model.addAttribute("exam", exam)
                .addAttribute("examQuestionList", exam.getExamQuestionList())
                .addAttribute("user", user)
                .addAttribute("remainingTime", exam.getDuration())
                .addAttribute("answers", userExamAnswers)
                .addAttribute("content", "exam");
        return "main";

    }

    @PostMapping("startexam/{userId}/{examId}")
    public String submitAnswers(@PathVariable Long examId, @PathVariable Long userId,
                                @ModelAttribute UserExamAnswers userExamAnswers) {
        Exam exam = examService.findById(examId);
        User user = userService.findByID(userId);
        Double maxScore = examService.calculateExamMaxScore(exam);
        Double userScore = examService.calculateUserScore(userExamAnswers);

        ExamResult examResult = new ExamResult();
        examResult.setExamId(exam);
        examResult.setMaxExamScore(maxScore);
        examResult.setStudentScore(userScore);
        examResult.setStudentId(user);

        examResultService.save(examResult);
        return "redirect:/userexamresults/" + userId;
    }


    @GetMapping("userexamresults/{userID}")
    public String showExamResults(Model model, @PathVariable("userID") Long userID) {
        User user = userService.findByID(userID);
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
                .addAttribute("content", "userexamresults");
        return "main";
    }


    @GetMapping("/showactiveexams/{userID}")
    public String showActiveExams(Model model, @PathVariable("userID") Long userID) {
        User user = userService.findByID(userID);
        List<CourseRegistration> registrations = courseRegistrationService.findAllRegisteredCourses(user);
        List<Course> registeredCourses = registrations.stream().map(CourseRegistration::getCourseId).toList();
        List<Exam> allRegisteredExams = examService.findAllByCourseList(registeredCourses);
        List<Exam> takenExams = examResultService.findUserExamResults(user).stream().map(ExamResult::getExamId).toList();

        List<Exam> examsToTake = allRegisteredExams.stream()
                .filter(exam -> exam.getStatus() == Exam.status.ACTIVE &&
                        takenExams.stream().noneMatch(takenExam -> takenExam.getId().equals(exam.getId())))
                .toList();


        model.addAttribute("exams", examsToTake)
                .addAttribute("user", user)
                .addAttribute("content", "userexamlist");


        return "main";
    }

    @PostMapping("delete/exam/{id}")
    public String deleteExam(@PathVariable("id") Long examID, @RequestParam("userID") Long userID) {
        examService.deleteExam(examID);
        return "redirect:/examlist/" + userID;
    }

    @PostMapping("delete/question/{id}")
    public String deleteQuestion(@PathVariable("id") Long questionID, @RequestParam("examID") Long examID) {
        examAnswerService.deleteAswersByQuestionID(questionID);
        examQuestionService.deleteQuestion(questionID);
        return "redirect:/questionlist/" + examID;
    }


    @GetMapping("/")
    public String home() {
        return "homepage";
    }

    @GetMapping("/registrationsuccesfull")
    public String registrationSuccesfullPage() {
        return "registrationsuccesfull";
    }

    @GetMapping("DashboardProfessor/{userID}")
    public String professorDashboard(Model model, @PathVariable("userID") Long userID) {
        User user = userService.findByID(userID);
        model.addAttribute("user", user)
                .addAttribute("content", "DashboardProfessor");
        return "main";
    }

    @GetMapping("user-dashboard/{userID}")
    public String userDashboard(Model model, @PathVariable("userID") Long userID) {
        User user = userService.findByID(userID);
        model.addAttribute("user", user)
                .addAttribute("content", "user-dashboard");
        return "main";
    }


    @GetMapping("/rf")
    public String registrationFailedPage() {
        return "rf";
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

        @Controller
        public class ProfessorController {

            private final ProfessorService professorService;
            private final CourseRegistrationService courseRegistrationService;
            private final StudentScheduleService studentScheduleService;
            private final UserRepository userRepository;
            private final CourseRepository courseRepository;
            private final CourseRegistrationRepository courseRegistrationRepository;

            @Autowired
            public ProfessorController(ProfessorService professorService, CourseRegistrationService courseRegistrationService, StudentScheduleService studentScheduleService, UserRepository userRepository, CourseRepository courseRepository, CourseRegistrationRepository courseRegistrationRepository) {
                this.professorService = professorService;
                this.courseRegistrationService = courseRegistrationService;
                this.studentScheduleService = studentScheduleService;
                this.userRepository = userRepository;
                this.courseRepository = courseRepository;
                this.courseRegistrationRepository = courseRegistrationRepository;
            }

            @GetMapping("/students/{id}/register")
            public String showProfessors(@PathVariable("id") Long id, Model model) {
                User student = userService.findByID(id);
                List<ProfessorDTO> professors = professorService.getAllProfessors();
                List<Long> registeredCourseIds = courseRegistrationRepository.findRegisteredCourseIdsByStudentId(id);

                List<Course> availableCourses = new ArrayList<>();
                for (ProfessorDTO professor : professors) {
                    for (Course course : professor.getCourses()) {
                        if (!registeredCourseIds.contains(course.getId())) {
                            availableCourses.add(course);
                        }
                    }
                }

                model.addAttribute("professors", professors);
                model.addAttribute("studentId", id);
                model.addAttribute("availableCourses", availableCourses);

                return "professors";
            }


            @PostMapping("/students/{studentId}/courses/{courseId}/register")
            public String registerForCourse(@PathVariable("studentId") Long studentId, @PathVariable("courseId") Long courseId, RedirectAttributes redirectAttributes) {
                boolean registrationStatus = courseRegistrationService.registerCourse(studentId, courseId);

                if (!registrationStatus) {
                    redirectAttributes.addFlashAttribute("message", "Student is already registered for this course");
                }
                return "redirect:/students/" + studentId + "/schedule";
            }

            @GetMapping("/students/{studentId}/schedule")
            public String getStudentSchedule(@PathVariable Long studentId, Model model) {
                LocalDate currentDate = LocalDate.now();
                List<CourseSession> schedule = studentScheduleService.getStudentScheduleByDate(studentId, currentDate);
                System.out.println("Student ID: " + studentId);
                System.out.println("Current Date: " + currentDate);
                System.out.println("Schedule: " + schedule);
                model.addAttribute("schedule", schedule);
                model.addAttribute("currentDate", currentDate);
                return "student-schedule";
            }

            @PostMapping("/students/{studentId}/schedule")
            public String getStudentScheduleByDate(@PathVariable("studentId") Long studentId, @RequestParam("selectedDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate selectedDate, Model model) {
                List<CourseSession> schedule = studentScheduleService.getStudentScheduleByDate(studentId, selectedDate);
                System.out.println("Student ID: " + studentId);
                System.out.println("Selected Date: " + selectedDate);
                System.out.println("Schedule: " + schedule);
                model.addAttribute("schedule", schedule);
                return "student-schedule";
            }


            @GetMapping("/students/{studentId}/registered-courses")
            public String showRegisteredCourses(@PathVariable("studentId") Long studentId, Model model) {
                User student = userRepository.findById(studentId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + studentId));

                List<Course> registeredCourses = courseRegistrationRepository.findAllByStudentId(student)
                        .stream()
                        .map(CourseRegistration::getCourseId)
                        .collect(Collectors.toList());

                model.addAttribute("registeredCourses", registeredCourses);
                return "registered-courses";
            }

            @PostMapping("/students/{studentId}/courses/{courseId}/unregister")
            public String unregisterFromCourse(@PathVariable("studentId") Long studentId, @PathVariable("courseId") Long courseId) {
                User student = userRepository.findById(studentId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + studentId));

                Course course = courseRepository.findById(courseId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid course ID: " + courseId));

                CourseRegistration courseRegistration = courseRegistrationRepository.findAllByStudentIdAndCourseId(student, course)
                        .orElseThrow(() -> new IllegalArgumentException("Student is not registered for this course"));

                courseRegistrationRepository.delete(courseRegistration);

                return "redirect:/students/" + studentId + "/registered-courses";
            }


        }

    @GetMapping("/QKP85NW83DGZ2EWYXHVRJH1IDJ7SDCULSCJP460E8Z4DKQQQCROIVTGG0X1Y")
    public String adminDashboard(Model model) {
        ;

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


}
