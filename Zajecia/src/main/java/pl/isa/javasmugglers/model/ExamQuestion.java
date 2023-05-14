package pl.isa.javasmugglers.model;

public class ExamQuestion {

    private Integer id;
    private String questionText;
    private enum questionType {SINGLE, MULTIPLE}
    private questionType type;
    private Integer examId;

    public ExamQuestion(Integer id, String questionText, questionType type, Integer examId) {
        this.id = id;
        this.questionText = questionText;
        this.type = type;
        this.examId = examId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }
}
