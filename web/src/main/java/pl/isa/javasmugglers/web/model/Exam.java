package pl.isa.javasmugglers.web.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Entity(name = "exams")
public class Exam {

    @Id
    @SequenceGenerator(
            name = "exam_sequence",
            sequenceName = "exam_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "exam_sequence"
    )
    @Column(
            updatable = false
    )
    private Long id;
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "courseId", referencedColumnName = "id")
    private Course courseId;

    private Date startDate;

    private Time startTime;

    private Date endDate;

    private Time endTime;

    private Integer passingThreshold;





    private Integer duration;

    //referencje do innych tabel
    @OneToMany(mappedBy = "examId")
    private List<ExamQuestion> examQuestionList;

    @OneToMany(mappedBy = "examId")
    private List<ExamResult> examResultList;

    public Exam() {
    }



    public Exam(String name, String description, Course courseId, Date startDate, Time startTime, Date endDate, Time endTime, Integer passingThreshold, Integer duration) {
        this.name = name;
        this.description = description;
        this.courseId = courseId;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.passingThreshold = passingThreshold;
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Course getCourseId() {
        return courseId;
    }

    public void setCourseId(Course courseId) {
        this.courseId = courseId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<ExamQuestion> getExamQuestionList() {
        return examQuestionList;
    }

    public void setExamQuestionList(List<ExamQuestion> examQuestionList) {
        this.examQuestionList = examQuestionList;
    }

    public List<ExamResult> getExamResultList() {
        return examResultList;
    }

    public void setExamResultList(List<ExamResult> examResultList) {
        this.examResultList = examResultList;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Integer getPassingThreshold() {
        return passingThreshold;
    }

    public void setPassingThreshold(Integer passingThreshold) {
        this.passingThreshold = passingThreshold;
    }
}
