package pl.isa.javasmugglers.web;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import pl.isa.javasmugglers.web.model.*;
import pl.isa.javasmugglers.web.model.User.User;
import pl.isa.javasmugglers.web.model.User.UserRepository;
import pl.isa.javasmugglers.web.repository.*;

import java.sql.Date;
import java.sql.Time;
import java.time.Duration;

@SpringBootApplication
@EntityScan()
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    /* Poniższe beany wypełniają bazę danych do celów development'u. Baza aktualnie ustwiona jest tak by tworzyła się przy
    boot'cie springa i niszczyła po jego zakończeniu. W związku czym poniższy kod odpala się przy każdym odpaleniu
    springa by ułatwić pracę nad fukncjonalnością aplikacji webowej. Wszystkie operacje CRUD na bazie danych opierają
    się na interfejsach w package "repository" które rozszerzają fukcjonalność z JpaRepository.
   */
    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            User blazej = new User(
                    "bj@gmail.com",
                    User.userType.STUDENT,
                    "lato23",
                    "Błażej",
                    "Jendrzejewski",
                    User.accountStatus.ACTIVE);
            User agata = new User(
                    "agata@gmail.com",
                    User.userType.PROFESSOR,
                    "lato23",
                    "Agata",
                    "Kowalska",
                    User.accountStatus.ACTIVE);
            User tomek = new User(
                    "tom@gmail.com",
                    User.userType.STUDENT,
                    "lato23",
                    "Tomek",
                    "Korek",
                    User.accountStatus.ACTIVE);
            User magda = new User(
                    "magda@gmail.com",
                    User.userType.PROFESSOR,
                    "lato23",
                    "Magda",
                    "Kowalska",
                    User.accountStatus.ACTIVE);

            userRepository.save(blazej);
            userRepository.save(agata);
            userRepository.save(tomek);
            userRepository.save(magda);

        };
    }

    @Bean
    CommandLineRunner commandLineRunner2(CourseRepository courseRepository, UserRepository userRepository) {
        return args -> {
            User professor = userRepository.findById(2L).get();
            Course filozofia = new Course("Java",
                    "Programowanie Java",
                    Date.valueOf("2023-10-10"),
                    Date.valueOf("2024-01-10"),
                    professor,
                    45
            );

            User professor2 = userRepository.findById(4L).get();
            Course arytmetyka = new Course("filozofia",
                    "Dział nauki zajmujący się liczbami",
                    Date.valueOf("2023-10-06"),
                    Date.valueOf("2024-01-16"),
                    professor2,
                    65
            );

            courseRepository.save(filozofia);
            courseRepository.save(arytmetyka);

        };
    }

    @Bean
    CommandLineRunner commandLineRunner3(CourseRegistrationRepository courseRegistrationRepository, UserRepository userRepository, CourseRepository courseRepository) {
        return args -> {
            User student1 = userRepository.findById(1L).get();
            Course course1 = courseRepository.findById(1L).get();
            CourseRegistration courseRegistration1 = new CourseRegistration(
                    student1,
                    course1);

            Course course2 = courseRepository.findById(2L).get();
            CourseRegistration courseRegistration2 = new CourseRegistration(
                    student1,
                    course2);

            User student2 = userRepository.findById(3L).get();
            Course course3 = courseRepository.findById(2L).get();
            CourseRegistration courseRegistration3 = new CourseRegistration(
                    student2,
                    course3);


            courseRegistrationRepository.save(courseRegistration1);
            courseRegistrationRepository.save(courseRegistration2);
            courseRegistrationRepository.save(courseRegistration3);

        };
    }

    @Bean
    CommandLineRunner commandLineRunner4(CourseSessionRepository courseSessionRepository, CourseRepository courseRepository) {
        return args -> {
            Course course1 = courseRepository.findById(1L).get();
            CourseSession courseSession1 = new CourseSession(
                    course1,
                    Date.valueOf("2023-10-15"),
                    Time.valueOf("17:30:00"),
                    Time.valueOf("19:30:00"),
                    CourseSession.sessionType.LECTURE, "sala 5c"
            );
            CourseSession courseSession2 = new CourseSession(
                    course1,
                    Date.valueOf("2023-10-22"),
                    Time.valueOf("17:30:00"),
                    Time.valueOf("19:30:00"),
                    CourseSession.sessionType.LECTURE, "sala 6a"
            );
            CourseSession courseSession3 = new CourseSession(
                    course1,
                    Date.valueOf("2023-11-05"),
                    Time.valueOf("18:00:00"),
                    Time.valueOf("20:00:00"),
                    CourseSession.sessionType.LECTURE, "sala 5c"
            );
            CourseSession courseSession4 = new CourseSession(
                    course1,
                    Date.valueOf("2023-11-12"),
                    Time.valueOf("16:00:00"),
                    Time.valueOf("18:00:00"),
                    CourseSession.sessionType.LECTURE, "sala 5c"
            );
            courseSessionRepository.save(courseSession1);
            courseSessionRepository.save(courseSession2);
            courseSessionRepository.save(courseSession3);
            courseSessionRepository.save(courseSession4);

        };
    }

    @Bean
    CommandLineRunner commandLineRunner5(ExamRepository examRepository, CourseRepository courseRepository) {
        return args -> {
            Course course1 = courseRepository.findById(1L).get();
            Exam exam1 = new Exam(
                    "Egzamin końcowy",
                    "Egzamin w formie testu. Na egzaminie mogą pojawić się pytania z całego semestru",
                    course1,
                    Exam.status.ACTIVE, Duration.ofMinutes(15));
            examRepository.save(exam1);
        };
    }

    @Bean
    CommandLineRunner commandLineRunner6(ExamQuestionRepository examQuestionRepository, ExamRepository examRepository) {
        return args -> {
            Exam exam1 = examRepository.findById(1L).get();
            ExamQuestion examQuestion1 = new ExamQuestion(
                    "Jak nazywa się mechanizm, który pozwala na wielokrotne użycie tego samego nazewnictwa dla różnych metod, pod warunkiem że różnią się one listą argumentów",
                    ExamQuestion.questionType.SINGLE,
                    exam1);

            ExamQuestion examQuestion2 = new ExamQuestion(
                    "W jakim kontekście używany jest modyfikator final w Javie?",
                    ExamQuestion.questionType.MULTIPLE,
                    exam1);

            ExamQuestion examQuestion3 = new ExamQuestion(
                    "Która z poniższych klas jest klasą nadrzędną dla wszystkich klas w Javie?",
                    ExamQuestion.questionType.SINGLE,
                    exam1);

            examQuestionRepository.save(examQuestion1);
            examQuestionRepository.save(examQuestion2);
            examQuestionRepository.save(examQuestion3);

        };
    }

    @Bean
    CommandLineRunner commandLineRunner7(ExamAnswerRepository examAnswerRepository, ExamQuestionRepository examQuestionRepository) {
        return args -> {
            ExamQuestion examQuestion1 = examQuestionRepository.findById(1L).get();
            ExamAnswer examAnswer1a = new ExamAnswer(
                    "Polimorfizm",
                    false, examQuestion1);
            ExamAnswer examAnswer1b = new ExamAnswer(
                    "Przeładowanie (overloading)",
                    true, examQuestion1);
            ExamAnswer examAnswer1c = new ExamAnswer(
                    "Przesłonięcie (overriding)",
                    false, examQuestion1);
            ExamAnswer examAnswer1d = new ExamAnswer(
                    "Dziedziczenie (inheritance)",
                    false, examQuestion1);

            ExamQuestion examQuestion2 = examQuestionRepository.findById(2L).get();
            ExamAnswer examAnswer2a = new ExamAnswer(
                    "Aby zadeklarować zmienną, której wartości nie można zmienić po inicjalizacji",
                    true, examQuestion2);
            ExamAnswer examAnswer2b = new ExamAnswer(
                    "Aby zadeklarować metodę, która nie może być przesłonięta w klasie dziedziczącej.",
                    true, examQuestion2);
            ExamAnswer examAnswer2c = new ExamAnswer(
                    "Aby zadeklarować klasę, która nie może być dziedziczona.",
                    true, examQuestion2);
            ExamAnswer examAnswer2d = new ExamAnswer(
                    "Aby zadeklarować interfejs, którego metody są domyślnie publiczne i abstrakcyjne.",
                    false, examQuestion2);

            ExamQuestion examQuestion3 = examQuestionRepository.findById(3L).get();
            ExamAnswer examAnswer3a = new ExamAnswer(
                    "String",
                    false, examQuestion3);
            ExamAnswer examAnswer3b = new ExamAnswer(
                    "Object",
                    true, examQuestion3);
            ExamAnswer examAnswer3c = new ExamAnswer(
                    "Class",
                    false, examQuestion3);
            ExamAnswer examAnswer3d = new ExamAnswer(
                    "System",
                    false, examQuestion3);

            examAnswerRepository.save(examAnswer1a);
            examAnswerRepository.save(examAnswer1b);
            examAnswerRepository.save(examAnswer1c);
            examAnswerRepository.save(examAnswer1d);

            examAnswerRepository.save(examAnswer2a);
            examAnswerRepository.save(examAnswer2b);
            examAnswerRepository.save(examAnswer2c);
            examAnswerRepository.save(examAnswer2d);

            examAnswerRepository.save(examAnswer3a);
            examAnswerRepository.save(examAnswer3b);
            examAnswerRepository.save(examAnswer3c);
            examAnswerRepository.save(examAnswer3d);

        };
    }

    @Bean
    CommandLineRunner commandLineRunner8(ExamResultsRepository examResultsRepository, ExamRepository examRepository, UserRepository userRepository) {
        return args -> {
            Exam exam1 = examRepository.findById(1L).get();
            User student1 = userRepository.findById(1L).get();
            ExamResult student1Results = new ExamResult(
                    student1,
                    exam1,
                    5,
                    5);
            examResultsRepository.save(student1Results);
        };
    }
}
