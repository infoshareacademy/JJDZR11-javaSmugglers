package pl.isa.javasmugglers.web.model;

import jakarta.persistence.*;

@Entity(name = "courseRegistrations")
public class CourseRegistration {

    @Id
    @SequenceGenerator(
            name = "course_registrations_sequence",
            sequenceName = "course_registrations_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_registrations_sequence"
    )
    @Column(
            updatable = false
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "studentId", referencedColumnName = "id")
    private User studentId;

    @ManyToOne
    @JoinColumn(name = "courseId", referencedColumnName = "id")
    private Course courseId;

    public CourseRegistration() {
    }

    public CourseRegistration(User studentId, Course courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getStudentId() {
        return studentId;
    }

    public void setStudentId(User user) {
        if (user.getType() != User.userType.STUDENT) {
            throw new IllegalArgumentException("User must be a student to register");
        }
        this.studentId = user;
    }

    public Course getCourseId() {
        return courseId;
    }

    public void setCourseId(Course courseId) {
        this.courseId = courseId;
    }

}
