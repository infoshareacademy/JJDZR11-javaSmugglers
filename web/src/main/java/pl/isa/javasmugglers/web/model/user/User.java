package pl.isa.javasmugglers.web.model.user;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
public class User implements UserDetails {



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


    @Enumerated(EnumType.STRING)
    @Column(
            columnDefinition = "enum('STUDENT', 'PROFESOR', 'ADMIN')"
    )
    private UserType type;

    @Enumerated(EnumType.STRING)
    @Column(
            columnDefinition = "enum('ACTIVE', 'WAITING_FOR_CONFIRMATION')"
    )
    private UserStatus status;

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

    @Column(
            nullable = false
    )
    private String authToken;


    //relacje do innych tabeli
    @OneToMany(mappedBy = "professorId", orphanRemoval = true)
    private List<Course> courses;

    @OneToMany(mappedBy = "studentId", orphanRemoval = true)
    private List<CourseRegistration> courseRegistrationsList;

    @OneToMany(mappedBy = "studentId", orphanRemoval = true)
    private List<ExamResult> examResultList;


    public User(String firstName,
                String lastName,
                String email,
                String password,
                UserStatus status
                UserType type,
                String authToken

    ) {
        this.email = email;
        this.type = type;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.authToken = authToken;


    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(type.name());
        return Collections.singletonList(authority);
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
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
