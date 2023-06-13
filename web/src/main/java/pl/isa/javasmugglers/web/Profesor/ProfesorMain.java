package pl.isa.javasmugglers.web.Profesor;

import java.sql.Date;
import java.time.LocalDate;

public class ProfesorMain {
            private Long id;
            private String Course_Type;
            private String Descryption;
            private Long ECTS_Points;
            private LocalDate End_Date;
            private String name;
            private LocalDate Start_Date;

    public ProfesorMain() {
    }

    public ProfesorMain(Long id, String course_Type, String descryption, Long ECTS_Points, LocalDate end_Date, String name, LocalDate start_Date) {
        this.id = id;
        Course_Type = course_Type;
        Descryption = descryption;
        this.ECTS_Points = ECTS_Points;
        End_Date = end_Date;
        this.name = name;
        Start_Date = start_Date;
    }

    public ProfesorMain(String course_Type, String descryption, Long ECTS_Points, Date end_Date, String name, Date start_Date) {
        Course_Type = course_Type;
        Descryption = descryption;
        this.ECTS_Points = ECTS_Points;
        End_Date = end_Date.toLocalDate();
        this.name = name;
        Start_Date = start_Date.toLocalDate();
    }

    @Override
    public String toString() {
        return "ProfesorMain{" +
                "id=" + id +
                ", Course_Type='" + Course_Type + '\'' +
                ", Descryption='" + Descryption + '\'' +
                ", ECTS_Points=" + ECTS_Points +
                ", End_Date=" + End_Date +
                ", name='" + name + '\'' +
                ", Start_Date=" + Start_Date +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourse_Type() {
        return Course_Type;
    }

    public void setCourse_Type(String course_Type) {
        Course_Type = course_Type;
    }

    public String getDescryption() {
        return Descryption;
    }

    public void setDescryption(String descryption) {
        Descryption = descryption;
    }

    public Long getECTS_Points() {
        return ECTS_Points;
    }

    public void setECTS_Points(Long ECTS_Points) {
        this.ECTS_Points = ECTS_Points;
    }

    public LocalDate getEnd_Date() {
        return End_Date;
    }

    public void setEnd_Date(LocalDate end_Date) {
        End_Date = end_Date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStart_Date() {
        return Start_Date;
    }

    public void setStart_Date(LocalDate start_Date) {
        Start_Date = start_Date;
    }
}
