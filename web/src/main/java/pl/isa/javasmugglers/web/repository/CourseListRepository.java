package pl.isa.javasmugglers.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.isa.javasmugglers.web.model.CourseList;

public interface CourseListRepository extends JpaRepository<CourseList, Long> {

}