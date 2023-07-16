package pl.isa.javasmugglers.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Entity(name = "courseSessions")
public class CourseSession {

    @Id
    @SequenceGenerator(
            name = "course_sessions_sequence",
            sequenceName = "course_sessions_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_sessions_sequence"
    )
    @Column(
            updatable = false
    )
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "courseId", referencedColumnName = "id")
    private Course courseId;

    private Date sessionDate;
    private Time startTime;
    private Time endTime;
    private String location;

    public CourseSession() {
    }

    public CourseSession(Course courseId, Date sessionDate, Time startTime, Time endTime, String location) {
        this.courseId = courseId;
        this.sessionDate = sessionDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourseId() {
        return courseId;
    }

    public void setCourseId(Course courseId) {
        this.courseId = courseId;
    }

    public Date getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(Date sessionDate) {
        this.sessionDate = sessionDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @JsonIgnore
    @ManyToMany(mappedBy = "registeredSessions")
    private List<CourseRegistration> courseRegistrations;

    public List<CourseRegistration> getCourseRegistrations() {
        return courseRegistrations;
    }

    public void setCourseRegistrations(List<CourseRegistration> courseRegistrations) {
        this.courseRegistrations = courseRegistrations;
    }
}
