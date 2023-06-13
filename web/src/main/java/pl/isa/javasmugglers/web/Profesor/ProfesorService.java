package pl.isa.javasmugglers.web.Profesor;

import org.springframework.stereotype.Component;
import pl.isa.javasmugglers.web.model.Courses;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Component
public class ProfesorService {
        public List<ProfesorMain> getProfesorMain() {
            return List.of(
                   new ProfesorMain(
                           3L,
                           "Seminary" ,
                           "blablabla",
                           5L,
                           LocalDate.of(2020,12,20),
                           "nazwa",
                           LocalDate.of(2020, 12, 31)


                    )

            );
        }
    }

//        "id=" + id +
//        ", Course_Type='" + Course_Type + '\'' +
//        ", Descryption='" + Descryption + '\'' +
//        ", ECTS_Points=" + ECTS_Points +
//        ", End_Date=" + End_Date +
//        ", name='" + name + '\'' +
//        ", Start_Date=" + Start_Date