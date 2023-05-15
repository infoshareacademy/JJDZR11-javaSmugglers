package pl.isa.javasmugglers.web.model;

import java.sql.Date;
import java.sql.Time;

public class CourseSession {
    private Long id;
    private Long courseId;
    private Date sessionDate;
    private Time startTime;
    private Time endTime;
    private enum sessionType {LECTURE, SEMINAR, OTHER}
    private sessionType type;
    private Integer location;

    public CourseSession(Long id, Long courseId, Date sessionDate, Time startTime, Time endTime, sessionType type, Integer location) {
        this.id = id;
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

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
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

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }
}
