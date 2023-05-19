package pl.isa.javasmugglers.web.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.isa.javasmugglers.web.model.user.User;

import java.util.Optional;
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByEmail(String email);

}

