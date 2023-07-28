package pl.isa.javasmugglers.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.isa.javasmugglers.web.model.ExamResult;
import pl.isa.javasmugglers.web.model.user.User;

import java.util.List;

@Repository
public interface ExamResultsRepository extends JpaRepository<ExamResult, Long> {
    List<ExamResult> findAllByStudentId(User user);
    List<ExamResult> deleteExamResultByStudentId(Long studentId);

/*
    @Query("SELECT examResults FROM examResults WHERE examResults.studentId.id = :studentsList")
    List<ExamResult> findAllByStudentList (@Param("studentsList") List<User> users);
*/

    List<ExamResult> findAllByStudentIdIn(List<User> users);

}
