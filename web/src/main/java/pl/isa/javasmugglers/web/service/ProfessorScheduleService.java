package pl.isa.javasmugglers.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.isa.javasmugglers.web.model.Course;
import pl.isa.javasmugglers.web.model.CourseSession;
import pl.isa.javasmugglers.web.repository.CourseSessionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessorScheduleService {
    private final CourseService courseService;
    private final CourseSessionRepository courseSessionRepository;

    @Autowired
    public ProfessorScheduleService(CourseService courseService, CourseSessionRepository courseSessionRepository) {
        this.courseService = courseService;
        this.courseSessionRepository = courseSessionRepository;
    }

    public List<CourseSession> getProfessorScheduleByDate(Long professorId, LocalDate selectedDate) {
        List<Long> professorCourseIds = courseService.findCoursesByProfessorId(professorId).stream()
                .map(Course::getId)
                .collect(Collectors.toList());
        return courseSessionRepository.findByCourseInAndSessionDate(professorCourseIds, selectedDate);
    }

    public List<CourseSession> getProfessorScheduleByDateRange(Long professorId, LocalDate startDate, LocalDate endDate) {
        List<Long> professorCourseIds = courseService.findCoursesByProfessorId(professorId).stream()
                .map(Course::getId)
                .collect(Collectors.toList());
        return courseSessionRepository.findByCourseInAndSessionDateBetween(professorCourseIds, startDate, endDate);
    }
}
