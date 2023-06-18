package pl.isa.javasmugglers.web.model;

import jakarta.persistence.*;
import pl.isa.javasmugglers.web.model.user.User;
import pl.isa.javasmugglers.web.model.user.UserType;

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



    public enum CourseType {LECTURE, SEMINAR, LAB, OTHER}
    @Enumerated(EnumType.STRING)
    @Column(
            columnDefinition = "enum('LECTURE', 'SEMINAR', 'LAB', 'OTHER')"
    )
    private CourseType CourseType;

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


    public Course(String name, String description, Date startDate, Date endDate, Integer ectsPoints, CourseType CourseType, User professorId) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ectsPoints = ectsPoints;
        this.CourseType = CourseType;
        this.professorId = professorId;
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
        if (user.getType() != UserType.PROFESOR) {
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

    public CourseType getCourseType() {
        return CourseType;
    }

    public void setCourseType(CourseType type) {
        this.CourseType = type;
    }

    public void setProfessorId(User professorId) {
        this.professorId = professorId;
    }

    public List<CourseRegistration> getCourseRegistrationList() {
        return courseRegistrationList;
    }

    public void setCourseRegistrationList(List<CourseRegistration> courseRegistrationList) {
        this.courseRegistrationList = courseRegistrationList;
    }

    public List<CourseSession> getCourseSessionList() {
        return courseSessionList;
    }

    public void setCourseSessionList(List<CourseSession> courseSessionList) {
        this.courseSessionList = courseSessionList;
    }
}
