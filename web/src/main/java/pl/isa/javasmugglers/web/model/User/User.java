package pl.isa.javasmugglers.web.model.User;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.usertype.UserType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.isa.javasmugglers.web.model.Course;
import pl.isa.javasmugglers.web.model.CourseRegistration;
import pl.isa.javasmugglers.web.model.ExamResult;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity(name = "users")
@Table(
        name = "users",
        uniqueConstraints = {@UniqueConstraint(name = "user_email_unique", columnNames = "email")
}
)
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class User implements UserDetails{
    private boolean locked;
    private boolean enabled;


    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Column(
            updatable = false
    )
    private Long id;

    @Column(
            name = "email",
            nullable = false
    )
    private String email;

    public enum userType {STUDENT, PROFESSOR, ADMIN}
    @Enumerated(EnumType.STRING)
    @Column(
            columnDefinition = "enum('STUDENT', 'PROFESSOR', 'ADMIN')"
    )
    private userType type;




    @Column(
            nullable = false
    )
    private String password;

    @Column(
            nullable = false
    )
    private String firstName;

    @Column(
            nullable = false
    )
    private String lastName;

    public enum accountStatus {ACTIVE, PENDING, REJECTED}
    @Enumerated(EnumType.STRING)
    @Column(
            columnDefinition = "enum('ACTIVE', 'PENDING', 'REJECTED')"
    )
    private accountStatus status;

    //relacje do innych tabeli
    @OneToMany(mappedBy = "professorId")
    private List<Course> courses;

    @OneToMany(mappedBy = "studentId")
    private List<CourseRegistration> courseRegistrationsList;

    @OneToMany(mappedBy = "studentId")
    private List<ExamResult> examResultList;

    public User(String email,
                userType type,
                String password,
                String firstName,
                String lastName,
                accountStatus status)
    {
        this.email = email;
        this.type = type;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public userType getType() {
        return type;
    }

    public void setType(userType type) {
        this.type = type;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(type.name());
        return Collections.singleton(authority);
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public accountStatus getStatus() {
        return status;
    }

    public void setStatus(accountStatus status) {
        this.status = status;
    }
}
