package pl.isa.javasmugglers.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.isa.javasmugglers.web.StudentConfig.ProfessorDTO;
import pl.isa.javasmugglers.web.model.Course;
import pl.isa.javasmugglers.web.model.CourseRegistration;
import pl.isa.javasmugglers.web.model.CourseSession;
import pl.isa.javasmugglers.web.model.user.User;
import pl.isa.javasmugglers.web.repository.UserRepository;
import pl.isa.javasmugglers.web.service.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProfessorController {

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
    @Autowired
    StudentScheduleService studentScheduleService;


    @GetMapping("students/{authToken}/register")
    public String showProfessors(@PathVariable("authToken") String authToken, Model model) {
        User student = userService.findByAuthToken(authToken);
        Long studentID = student.getId();
        List<ProfessorDTO> professors = professorService.getAllProfessors();
        List<Long> registeredCourseIds = courseRegistrationService.findRegisteredCourseIdsByStudentId(studentID);

        List<Course> availableCourses = new ArrayList<>();
        for (ProfessorDTO professor : professors) {
            for (Course course : professor.getCourses()) {
                if (!registeredCourseIds.contains(course.getId())) {
                    availableCourses.add(course);
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

    @GetMapping("students/{authToken}/schedule")
    public String getStudentSchedule(@PathVariable("authToken") String authToken,
                                     @RequestParam(value = "weekOffset", defaultValue = "0") int weekOffset,
                                     @RequestParam(value = "selectedDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate selectedDate,
                                     Model model) {
        User student = userService.findByAuthToken(authToken);
        Long studentID = student.getId();

        LocalDate currentDate = selectedDate != null ? selectedDate : LocalDate.now();
        LocalDate startDate = currentDate.minusWeeks(1 + weekOffset);
        LocalDate endDate = startDate.plusDays(6);

        List<CourseSession> schedule = studentScheduleService.getStudentScheduleByDateRange(studentID, startDate, endDate);
        model.addAttribute("schedule", schedule);
        model.addAttribute("currentDate", currentDate);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "student-schedule";
    }

    @PostMapping("students/{authToken}/schedule")
    public String getStudentScheduleByDate(@PathVariable("authToken") String authToken,
                                           @RequestParam("selectedDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate selectedDate,
                                           Model model) {
        User student = userService.findByAuthToken(authToken);
        Long studentID = student.getId();
        List<CourseSession> schedule = studentScheduleService.getStudentScheduleByDate(studentID, selectedDate);
        model.addAttribute("schedule", schedule);
        return "student-schedule";
    }


    @GetMapping("students/{studentId}/registered-courses")
    public String showRegisteredCourses(@PathVariable("studentId") String authToken, Model model) {
        User student = userService.findByAuthToken(authToken);

        List<Course> registeredCourses = CourseRegistrationService.findAllByStudentId(student)
                .stream()
                .map(CourseRegistration::getCourseId)
                .collect(Collectors.toList());

        model.addAttribute("registeredCourses", registeredCourses)
                .addAttribute("authToken", authToken);

        return "registered-courses";
    }

    @PostMapping("/students/{authToken}/courses/{courseId}/unregister")
    public String unregisterFromCourse(@PathVariable("authToken") String authToken, @PathVariable("courseId") Long courseId) {
        User student = userService.findByAuthToken(authToken);

        Course course = courseService.findByID(courseId);

        CourseRegistration courseRegistration = courseRegistrationService.findAllByStudentIdAndCourseId(student, course);

        courseRegistrationService.delete(courseRegistration);

        return "redirect:/students/" + authToken + "/registered-courses";
    }


}

