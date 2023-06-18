package pl.isa.javasmugglers.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.isa.javasmugglers.web.model.CourseSession;
import pl.isa.javasmugglers.web.repository.CourseSessionRepository;

import java.util.List;

@Service
public class CourseSessionService {

    private final CourseSessionRepository courseSessionRepository;

    @Autowired
    public CourseSessionService(CourseSessionRepository courseSessionRepository) {
        this.courseSessionRepository = courseSessionRepository;
    }


    public List<CourseSession> getCourseSession() {
        return courseSessionRepository.findAll();
    }
}
