package pl.isa.javasmugglers.web.model;

import java.util.Map;

public class UserQuestionAnswers {
    private Long questionId;
    private Map<Long, Boolean> selectedAnswers;

    public UserQuestionAnswers() {
    }

    public UserQuestionAnswers(Long questionId, Map<Long, Boolean> selectedAnswers) {
        this.questionId = questionId;
        this.selectedAnswers = selectedAnswers;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Map<Long, Boolean> getSelectedAnswers() {
        return selectedAnswers;
    }

    public void setSelectedAnswers(Map<Long, Boolean> selectedAnswers) {
        this.selectedAnswers = selectedAnswers;
    }
}
