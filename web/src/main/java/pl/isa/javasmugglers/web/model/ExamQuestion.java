package pl.isa.javasmugglers.web.model;

public class ExamQuestion {

    private Long id;
    private String questionText;
    private enum questionType {SINGLE, MULTIPLE}
    private questionType type;
    private Long examId;

    public ExamQuestion(Long id, String questionText, questionType type, Long examId) {
        this.id = id;
        this.questionText = questionText;
        this.type = type;
        this.examId = examId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public questionType getType() {
        return type;
    }

    public void setType(questionType type) {
        this.type = type;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }
}
