package pl.isa.javasmugglers.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.isa.javasmugglers.web.model.Course;
import pl.isa.javasmugglers.web.model.CourseSession;
import pl.isa.javasmugglers.web.repository.CourseSessionRepository;

import java.time.LocalDate;
import java.util.ArrayList;
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

    public List<CourseSession> getCourseSessionByCourseID(List<Course> courseList) {
        return courseSessionRepository.findAllByCourseIdIn(courseList);
    }

    public CourseSession saveCourseSession(CourseSession courseSession) {
        return courseSessionRepository.save(courseSession);
    }


    public void addMultipleSession(Integer frequency, String startTime, String endTime, String location, Course course) {

        LocalDate courseStartDate = course.getStartDate();
        LocalDate courseEndDate = course.getEndDate();
        List<LocalDate> sessionDates = new ArrayList<>();
        LocalDate currentDate = courseStartDate;

        while (!currentDate.isAfter(courseEndDate)) {
            sessionDates.add(currentDate);

            switch (frequency) {
                case 1:
                    currentDate = currentDate.plusWeeks(1);
                    break;
                case 2:
                    currentDate = currentDate.plusWeeks(2);
                    break;
                case 4:
                    currentDate = currentDate.plusMonths(1);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid frequency value: " + frequency);
            }
        }

        java.sql.Time sessionStartTime = java.sql.Time.valueOf(startTime + ":00");
        java.sql.Time sessionEndTime = java.sql.Time.valueOf(endTime + ":00");


        for (LocalDate date : sessionDates
        ) {
            CourseSession session = new CourseSession();
            session.setCourseId(course);
            session.setSessionDate(date);
            session.setStartTime(sessionStartTime);
            session.setEndTime(sessionEndTime);
            session.setLocation(location);

            saveCourseSession(session);

        }


    }

    public void deleteCourseSession(Long id) {
        courseSessionRepository.delete(courseSessionRepository.findById(id).orElseThrow());
    }

    public CourseSession findByID(long id) {
        return (courseSessionRepository.findById(id).orElseThrow());
    }


}
