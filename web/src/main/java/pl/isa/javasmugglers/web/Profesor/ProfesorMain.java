package pl.isa.javasmugglers.web.Profesor;

import org.springframework.boot.CommandLineRunner;
import pl.isa.javasmugglers.web.model.Course;
import pl.isa.javasmugglers.web.model.Courses;
import pl.isa.javasmugglers.web.repository.CoursesRepository;

import java.sql.Date;

public class ProfesorMain {
            private Long id;
            private String Course_Type;
            private String Descryption;
            private Long ECTS_Points;
            private Date End_Date;
            private String name;
            private Date Start_Date;

    public ProfesorMain() {
    }

    public ProfesorMain(Long id, String course_Type, String descryption, Long ECTS_Points, Date end_Date, String name, Date start_Date) {
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
        End_Date = end_Date;
        this.name = name;
        Start_Date = start_Date;
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
}
