package pl.isa.javasmugglers.web.model;

import jakarta.persistence.*;
import pl.isa.javasmugglers.web.model.user.User;
import pl.isa.javasmugglers.web.model.user.UserType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "examResults")
public class ExamResult {

    @Id
    @SequenceGenerator(
            name = "exam_results_sequence",
            sequenceName = "exam_results_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "exam_results_sequence"
    )
    @Column(
            updatable = false
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "studentId", referencedColumnName = "id")
    private User studentId;

    @ManyToOne
    @JoinColumn(name = "examId", referencedColumnName = "id")
    private Exam examId;
    private Double studentScore;
    private Double maxExamScore;
    private Integer percentage;
    private LocalDateTime examDateTime;

    public ExamResult() {
    }

    public ExamResult(User studentId, Exam examId, Double studentScore, Double maxExamScore, Integer percentage, LocalDateTime examDateTime) {
        this.studentId = studentId;
        this.examId = examId;
        this.studentScore = studentScore;
        this.maxExamScore = maxExamScore;
        this.percentage = percentage;
        this.examDateTime = examDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getStudentId() {
        return studentId;
    }

    public void setStudentId(User user) {
        if (user.getType() != UserType.STUDENT) {
            throw new IllegalArgumentException("User must be a student to register");
        }
        this.studentId = user;
    }
    public Exam getExamId() {
        return examId;
    }

    public void setExamId(Exam examId) {
        this.examId = examId;
    }

    public Double getStudentScore() {
        return studentScore;
    }

    public void setStudentScore(Double studentScore) {
        this.studentScore = studentScore;
    }

    public Double getMaxExamScore() {
        return maxExamScore;
    }

    public void setMaxExamScore(Double maxExamScore) {
        this.maxExamScore = maxExamScore;
    }

    public LocalDateTime getExamDateTime() {
        return examDateTime;
    }

    public void setExamDateTime(LocalDateTime examDateTime) {
        this.examDateTime = examDateTime;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }
}
