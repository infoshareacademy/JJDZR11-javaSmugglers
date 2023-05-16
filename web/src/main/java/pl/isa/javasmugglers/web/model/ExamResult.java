package pl.isa.javasmugglers.web.model;

import jakarta.persistence.*;

@Entity(name = "examResults")
public class ExamResult {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
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
    private Integer studentScore;
    private Integer maxExamScore;

    public ExamResult() {
    }

    public ExamResult(User studentId, Exam examId, Integer studentScore, Integer maxExamScore) {
        this.studentId = studentId;
        this.examId = examId;
        this.studentScore = studentScore;
        this.maxExamScore = maxExamScore;
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
        if (user.getType() != User.userType.STUDENT) {
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

    public Integer getStudentScore() {
        return studentScore;
    }

    public void setStudentScore(Integer studentScore) {
        this.studentScore = studentScore;
    }

    public Integer getMaxExamScore() {
        return maxExamScore;
    }

    public void setMaxExamScore(Integer maxExamScore) {
        this.maxExamScore = maxExamScore;
    }
}
