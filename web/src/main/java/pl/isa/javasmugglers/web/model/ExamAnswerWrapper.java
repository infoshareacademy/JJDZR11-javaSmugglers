package pl.isa.javasmugglers.web.model;

import java.util.List;

public class ExamAnswerWrapper {
    private List<ExamAnswer> examAnswers;
    private String type;

    public ExamAnswerWrapper() {
    }

    public ExamAnswerWrapper(List<ExamAnswer> examAnswers) {
        this.examAnswers = examAnswers;
    }

    public ExamAnswerWrapper(List<ExamAnswer> examAnswers, String type) {
        this.examAnswers = examAnswers;
        this.type = type;
    }

    public List<ExamAnswer> getExamAnswers() {
        return examAnswers;
    }

    public void setExamAnswers(List<ExamAnswer> examAnswers) {
        this.examAnswers = examAnswers;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
