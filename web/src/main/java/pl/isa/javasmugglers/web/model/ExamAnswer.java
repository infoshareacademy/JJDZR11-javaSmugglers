package pl.isa.javasmugglers.web.model;

import jakarta.persistence.*;

@Entity(name = "examAnswers")
public class ExamAnswer {

    @Id
    @SequenceGenerator(
            name = "Exam_answer_sequence",
            sequenceName = "Exam_answer_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Exam_answer_sequence"
    )
    @Column(
            updatable = false
    )
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String answerText;
    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name="questionId", referencedColumnName = "id")
    private ExamQuestion questionId;

    public ExamAnswer() {
    }

    public ExamAnswer(String answerText, boolean isCorrect, ExamQuestion questionId) {
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

    public ExamQuestion getQuestionId() {
        return questionId;
    }

    public void setQuestionId(ExamQuestion questionId) {
        this.questionId = questionId;
    }
}
