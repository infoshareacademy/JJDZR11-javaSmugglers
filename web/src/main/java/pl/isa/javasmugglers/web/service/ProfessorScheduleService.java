package pl.isa.javasmugglers.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.isa.javasmugglers.web.model.Course;
import pl.isa.javasmugglers.web.model.CourseSession;
import pl.isa.javasmugglers.web.repository.CourseSessionRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class ProfessorScheduleService {
    private final CourseService courseService;
    private final CourseSessionRepository courseSessionRepository;

    @Autowired
    public ProfessorScheduleService(CourseService courseService, CourseSessionRepository courseSessionRepository) {
        this.courseService = courseService;
        this.courseSessionRepository = courseSessionRepository;
    }

    public List<CourseSession> getProfessorSchedule(Long professorId) {
        List<Long> professorCourseIds = courseService.findCoursesByProfessorId(professorId).stream()
                .map(Course::getId)
                .toList();
        return courseSessionRepository.findAll().stream()
                .filter(s -> professorCourseIds.contains(s.getCourseId().getId()))
                .sorted(Comparator.comparing(CourseSession::getSessionDate))
                .toList();
    }

    public List<CourseSession> getProfessorScheduleByDate(Long professorId, LocalDate selectedDate) {
        List<Long> professorCourseIds = courseService.findCoursesByProfessorId(professorId).stream()
                .map(Course::getId)
                .toList();
        return courseSessionRepository.findAll().stream()
                .filter(s -> professorCourseIds.contains(s.getCourseId().getId()))
                .filter(s -> selectedDate == null || s.getSessionDate().equals(selectedDate))
                .sorted(Comparator.comparing(CourseSession::getSessionDate))
                .toList();
    }
}
