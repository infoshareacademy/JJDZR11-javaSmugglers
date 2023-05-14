package pl.isa.javasmugglers.model;

public class ExamResult {
    private Integer id;
    private Integer studentId;
    private Integer examId;
    private Integer studentScore;
    private Integer maxExamScore;

    public ExamResult(Integer id, Integer studentId, Integer examId, Integer studentScore, Integer maxExamScore) {
        this.id = id;
        this.studentId = studentId;
        this.examId = examId;
        this.studentScore = studentScore;
        this.maxExamScore = maxExamScore;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
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
