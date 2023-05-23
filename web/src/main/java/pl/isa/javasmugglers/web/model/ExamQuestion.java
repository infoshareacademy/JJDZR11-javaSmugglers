package pl.isa.javasmugglers.web.model;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "examQuestions")
public class ExamQuestion {

    @Id
    @SequenceGenerator(
            name = "exam_questions_sequence",
            sequenceName = "exam_questions_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "exam_questions_sequence"
    )
    @Column(
            updatable = false
    )
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String questionText;

    public enum questionType {SINGLE, MULTIPLE}

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('SINGLE', 'MULTIPLE')")
    private questionType type;

    @ManyToOne
    @JoinColumn(columnDefinition = "examId", referencedColumnName = "id")
    private Exam examId;

    @OneToMany(mappedBy = "questionId")
    private List<ExamAnswer> examAnswerList;

    public ExamQuestion() {
    }

    public ExamQuestion(String questionText, questionType type, Exam examId) {
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

    public Exam getExamId() {
        return examId;
    }

    public void setExamId(Exam examId) {
        this.examId = examId;
    }

    public List<ExamAnswer> getExamAnswerList() {
        return examAnswerList;
    }

    public void setExamAnswerList(List<ExamAnswer> examAnswerList) {
        this.examAnswerList = examAnswerList;
    }
}
