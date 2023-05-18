package pl.isa.javasmugglers.web.model;

import jakarta.persistence.*;
import pl.isa.javasmugglers.web.model.User.User;

import java.sql.Date;
import java.util.List;

@Entity(name = "courses")
public class Course {

    @Id
    @SequenceGenerator(
            name = "course_sequence",
            sequenceName = "course_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_sequence"
    )
    @Column(
            updatable = false
    )
    private Long id;
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
    private Date startDate;
    private Date endDate;

    private Integer ectsPoints;

    //relacje do innych tabel
    @ManyToOne
    @JoinColumn(name="professor_id", referencedColumnName = "id")
    private User professorId;

    @OneToMany(mappedBy = "courseId")
    private List<CourseRegistration> courseRegistrationList;

    @OneToMany(mappedBy = "courseId")
    private List<CourseSession> courseSessionList;

    public Course() {
    }

    public Course(String name, String description, Date startDate, Date endDate, User professorId, Integer ectsPoints) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.professorId = professorId;
        this.ectsPoints = ectsPoints;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public User getProfessorId() {
        return professorId;
    }

    public void setProfessor(User user) {
        if (user.getType() != User.userType.PROFESSOR) {
            throw new IllegalArgumentException("User must be a professor to be set as course professor");
        }
        this.professorId = user;
    }
    public Integer getEctsPoints() {
        return ectsPoints;
    }

    public void setEctsPoints(Integer ectsPoints) {
        this.ectsPoints = ectsPoints;
    }
}
