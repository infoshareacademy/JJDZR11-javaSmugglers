package pl.isa.javasmugglers.web.model;

public class ExamAnswer {

    private Long id;
    private String answerText;
    private boolean isCorrect;
    private Long questionId;

    public ExamAnswer(Long id, String answerText, boolean isCorrect, Long questionId) {
        this.id = id;
        this.answerText = answerText;
        this.isCorrect = isCorrect;
        this.questionId = questionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        this.isCorrect = correct;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}
