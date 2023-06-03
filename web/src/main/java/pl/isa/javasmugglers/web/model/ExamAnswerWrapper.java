package pl.isa.javasmugglers.web.model;

import java.util.List;

public class ExamAnswerWrapper {
    private List<ExamAnswer> examAnswers;

    public ExamAnswerWrapper() {
    }

    public ExamAnswerWrapper(List<ExamAnswer> examAnswers) {
        this.examAnswers = examAnswers;
    }

    public List<ExamAnswer> getExamAnswers() {
        return examAnswers;
    }

    public void setExamAnswers(List<ExamAnswer> examAnswers) {
        this.examAnswers = examAnswers;
    }
}
