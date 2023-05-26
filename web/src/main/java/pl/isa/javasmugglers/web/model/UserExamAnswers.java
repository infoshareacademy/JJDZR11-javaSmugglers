package pl.isa.javasmugglers.web.model;

import java.util.List;
import java.util.Map;

public class UserExamAnswers {
    private Map<Long, List<Long>> answers;

    public UserExamAnswers() {
    }

    public UserExamAnswers(Map<Long, List<Long>> answers) {
        this.answers = answers;
    }

    public Map<Long, List<Long>> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Long, List<Long>> answers) {
        this.answers = answers;
    }
}
