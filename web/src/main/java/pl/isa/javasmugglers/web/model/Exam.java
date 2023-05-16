package pl.isa.javasmugglers.web.model;

public class Exam {

    private Long id;
    private String name;
    private String description; 
    private Long courseId;
    private enum status {ACTIVE, INACTIVE}
    private status status;

    public Exam(Long id, String name, String description, Long courseId, Exam.status status) {
        this.id = id;
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

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Exam.status getStatus() {
        return status;
    }

    public void setStatus(Exam.status status) {
        this.status = status;
    }
}
