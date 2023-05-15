package pl.isa.javasmugglers.web.model;

public class ExamResult {
    private Long id;
    private Long studentId;
    private Long examId;
    private Integer studentScore;
    private Integer maxExamScore;

    public ExamResult(Long id, Long studentId, Long examId, Integer studentScore, Integer maxExamScore) {
        this.id = id;
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

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
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
