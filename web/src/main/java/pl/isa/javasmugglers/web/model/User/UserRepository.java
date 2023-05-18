package pl.isa.javasmugglers.web.model.User;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.isa.javasmugglers.web.model.User.User;

import java.util.Optional;
@Repository
@Transactional
public interface UserRepository{
    Optional<User> findByEmail(String email);

}
