package pl.isa.javasmugglers.model;

import java.sql.Date;

public class Course {

    private Integer id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private Integer professorId;
    private Integer ectsPoints;

    public Course(Integer id, String name, String description, Date startDate, Date endDate, Integer professorId, Integer ectsPoints) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.professorId = professorId;
        this.ectsPoints = ectsPoints;
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

    public Integer getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Integer professorId) {
        this.professorId = professorId;
    }

    public Integer getEctsPoints() {
        return ectsPoints;
    }

    public void setEctsPoints(Integer ectsPoints) {
        this.ectsPoints = ectsPoints;
    }
}
