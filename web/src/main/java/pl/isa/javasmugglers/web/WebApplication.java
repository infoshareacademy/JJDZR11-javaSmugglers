package pl.isa.javasmugglers.web;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.isa.javasmugglers.web.model.*;
import pl.isa.javasmugglers.web.model.user.User;
import pl.isa.javasmugglers.web.model.user.UserType;
import pl.isa.javasmugglers.web.repository.*;
import pl.isa.javasmugglers.web.service.UserService;

import java.sql.Date;
import java.sql.Time;

@SpringBootApplication
@EntityScan()
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }


    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository,
                                        BCryptPasswordEncoder bCryptPasswordEncoder,
                                        UserService userService) {
        return args -> {
            User blazej = new User(
                    "Błażej",
                    "Jendrzejewski",
                    "bj@gmail.com",
                    bCryptPasswordEncoder.encode("lato23"),
                    UserType.STUDENT,
                    userService.generateAuthToken()
            );

            User agata = new User(
                    "Agata",
                    "Kowalska",
                    "agata@gmail.com",
                    bCryptPasswordEncoder.encode("lato24"),
                    UserType.PROFESOR,
                    userService.generateAuthToken()
            );

            User tomek = new User(
                    "Tomek",
                    "Korek",
                    "tom@gmail.com",
                    bCryptPasswordEncoder.encode("lato25"),
                    UserType.STUDENT,
                    userService.generateAuthToken()
            );

            User magda = new User(
                    "Magda",
                    "Kowalska",
                    "magda@gmail.com",
                    bCryptPasswordEncoder.encode("lato26"),
                    UserType.PROFESOR,
                    userService.generateAuthToken()
            );

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
            Course programowanie = new Course("Java",
                    "Programowanie Java",
                    Date.valueOf("2023-10-10"),
                    Date.valueOf("2024-01-10"), 45, Course.CourseType.LECTURE, professor);


            User professor2 = userRepository.findById(2L).get();
            Course arytmetyka = new Course("arytmetyka",
                    "Dział nauki zajmujący się liczbami",
                    Date.valueOf("2023-10-06"),
                    Date.valueOf("2024-01-16"), 65, Course.CourseType.SEMINAR,
                    professor2
            );

            courseRepository.save(programowanie);
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
                    Time.valueOf("8:00:00"),
                    Time.valueOf("10:00:00"),
                    "sala 5c"
            );

            courseSessionRepository.save(courseSession1);

            Course course2 = courseRepository.findById(2L).get();
            CourseSession courseSession2 = new CourseSession(
                    course2,
                    Date.valueOf("2023-10-25"),
                    Time.valueOf("10:30:00"),
                    Time.valueOf("12:30:00"),
                    "sala 212B"
            );
            courseSessionRepository.save(courseSession2);


            CourseSession courseSession3 = new CourseSession(
                    course1,
                    Date.valueOf("2023-11-05"),
                    Time.valueOf("18:00:00"),
                    Time.valueOf("20:00:00"),
                    "sala 5c"
            );

            courseSessionRepository.save(courseSession3);

            CourseSession courseSession4 = new CourseSession(
                    course1,
                    Date.valueOf("2023-11-12"),
                    Time.valueOf("16:00:00"),
                    Time.valueOf("18:00:00"),
                    "sala 5c"
            );

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
                    Exam.status.ACTIVE, 10
            );
            examRepository.save(exam1);

            Exam exam2 = new Exam(
                    "Egzamin mid-semetralny",
                    "Egzamin w formie testu. Na egzaminie mogą pojawić się pytania z dotychczasowego materiału omawianego na zajęciach",
                    course1,
                    Exam.status.INACTIVE,
                    15);
            examRepository.save(exam2);
        };
    }

    @Bean
    CommandLineRunner commandLineRunner6(ExamQuestionRepository examQuestionRepository, ExamRepository examRepository) {
        return args -> {
            Exam exam1 = examRepository.findById(1L).get();
            ExamQuestion examQuestion1 = new ExamQuestion(
                    "Jak nazywa się mechanizm, który pozwala na wielokrotne użycie tego samego nazewnictwa dla różnych metod, pod warunkiem że różnią się one listą argumentów?",
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
            User student1 = userRepository.findById(3L).get();
            ExamResult student1Results = new ExamResult(
                    student1,
                    exam1,
                    5.0,
                    5.0);
            examResultsRepository.save(student1Results);
        };
    }

}

