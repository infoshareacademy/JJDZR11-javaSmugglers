package pl.isa.javasmugglers.model;

public class Exam {

    private Integer id;
    private String name;
    private String description; 
    private Integer course_id;
    private enum status {ACTIVE, INACTIVE}
    private status status;

    public Exam(Integer id, String name, String description, Integer course_id, Exam.status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.course_id = course_id;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Integer course_id) {
        this.course_id = course_id;
    }

    public Exam.status getStatus() {
        return status;
    }

    public void setStatus(Exam.status status) {
        this.status = status;
    }
}
