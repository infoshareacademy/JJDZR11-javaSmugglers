package pl.isa.javasmugglers.web.StudentConfig;

import pl.isa.javasmugglers.web.model.Course;

import java.util.List;

public class ProfessorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Course> courses;

    public ProfessorDTO() {
    }

    public ProfessorDTO(Long id, String firstName, String lastName, List<Course> courses) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.courses = courses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}

