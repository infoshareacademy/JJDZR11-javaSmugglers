package pl.isa.javasmugglers.web.model;

import java.util.Map;

public class UserExamAnswers {
    private Map<Long, Map<Long, Boolean>> answers;

    public UserExamAnswers() {
    }

    public UserExamAnswers(Map<Long, Map<Long, Boolean>> answers) {
        this.answers = answers;
    }

    public Map<Long, Map<Long, Boolean>> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Long, Map<Long, Boolean>> answers) {
        this.answers = answers;
    }
}
