package pl.isa.javasmugglers.web.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;

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

    @ManyToOne
    @JoinColumn(name = "courseId", referencedColumnName = "id")
    private Course courseId;

    private Date sessionDate;
    private Time startTime;
    private Time endTime;

    public enum sessionType {LECTURE, SEMINAR, LAB, OTHER}
    @Enumerated(EnumType.STRING)
    @Column(
            columnDefinition = "enum('LECTURE', 'SEMINAR', 'LAB', 'OTHER')"
    )
    private sessionType type;

    private String location;

    //relacje do innych tabel




    public CourseSession() {
    }

    public CourseSession(Course courseId, Date sessionDate, Time startTime, Time endTime, sessionType type, String location) {
        this.courseId = courseId;
        this.sessionDate = sessionDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
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

    public sessionType getType() {
        return type;
    }

    public void setType(sessionType type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
