package pl.isa.javasmugglers.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.isa.javasmugglers.web.model.Exam;
import pl.isa.javasmugglers.web.service.CourseService;
import pl.isa.javasmugglers.web.service.ExamService;

@Controller
public class MainController {

    @Autowired
    ExamService examService;
    @Autowired
    CourseService courseService;


    @GetMapping("/examlist")
    String examlist (Long id, Model model){
        model.addAttribute("examlist", examService.listAllExamsByProfessorId(id))
                .addAttribute("content", "examlist");
        return "examlist";
    }


    @PostMapping("/addexam")
    public String addExam(@ModelAttribute Exam exam) {
        examService.saveExam(exam);
        Long activeUserId = exam.getCourseId().getProfessorId().getId();
        return "redirect:/examlist?id=" + activeUserId;
    }

    @GetMapping("/addexam")
    public String showAddExamForm(Model model, Long id) {
        model.addAttribute("exam", new Exam())
                .addAttribute("courseList", courseService.coursesListByProfessorId(id))
                .addAttribute("content", "addexam");
        System.out.println(courseService.coursesListByProfessorId(id));

        return "addexam";
    }


}
