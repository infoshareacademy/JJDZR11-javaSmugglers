package pl.isa.javasmugglers.web.service;

import org.springframework.stereotype.Service;
import pl.isa.javasmugglers.web.StudentConfig.ProfessorDTO;
import pl.isa.javasmugglers.web.model.user.User;
import pl.isa.javasmugglers.web.repository.CourseRegistrationRepository;
import pl.isa.javasmugglers.web.repository.CourseRepository;
import pl.isa.javasmugglers.web.repository.CourseSessionRepository;
import pl.isa.javasmugglers.web.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessorService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public ProfessorService(UserRepository userRepository, CourseRepository courseRepository, CourseSessionRepository courseSessionRepository, CourseRegistrationRepository courseRegistrationRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public List<ProfessorDTO> getAllProfessors() {
        List<User> professors = userRepository.findAllProfessors();
        return professors.stream()
                .map(this::createProfessorDTO)
                .collect(Collectors.toList());
    }

    private ProfessorDTO createProfessorDTO(User professor) {
        ProfessorDTO professorDTO = new ProfessorDTO();
        professorDTO.setId(professor.getId());
        professorDTO.setFirstName(professor.getFirstName());
        professorDTO.setLastName(professor.getLastName());
        professorDTO.setCourses(courseRepository.findAllByProfessorId(professor));
        return professorDTO;
    }
}

