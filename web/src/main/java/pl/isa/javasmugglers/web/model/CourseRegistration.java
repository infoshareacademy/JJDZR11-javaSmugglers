package pl.isa.javasmugglers.web.model;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import pl.isa.javasmugglers.web.model.user.User;
import pl.isa.javasmugglers.web.model.user.UserType;
import pl.isa.javasmugglers.web.repository.CourseRegistrationRepository;

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
        if (user.getType() != UserType.STUDENT) {
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
