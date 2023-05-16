package pl.isa.javasmugglers.web.model;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "exams")
public class Exam {

    @Id
    @SequenceGenerator(
            name = "exam_sequence",
            sequenceName = "exam_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "exam_sequence"
    )
    @Column(
            updatable = false
    )
    private Long id;
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "courseId", referencedColumnName = "id")
    private Course courseId;

    public enum status {ACTIVE, INACTIVE}
    @Enumerated(EnumType.STRING)
    @Column(
            columnDefinition = "enum('ACTIVE', 'INACTIVE')"
    )
    private status status;

    //referencje do innych tabel
    @OneToMany(mappedBy = "examId")
    private List<ExamQuestion> examQuestionList;

    @OneToMany(mappedBy = "examId")
    private List<ExamResult> examResultList;


    public Exam() {
    }

    public Exam(String name, String description, Course courseId, Exam.status status) {
        this.name = name;
        this.description = description;
        this.courseId = courseId;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Course getCourseId() {
        return courseId;
    }

    public void setCourseId(Course courseId) {
        this.courseId = courseId;
    }

    public Exam.status getStatus() {
        return status;
    }

    public void setStatus(Exam.status status) {
        this.status = status;
    }
}
