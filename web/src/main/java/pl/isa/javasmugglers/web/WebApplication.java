package pl.isa.javasmugglers.web;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import pl.isa.javasmugglers.web.model.*;
import pl.isa.javasmugglers.web.model.user.UserType;
import pl.isa.javasmugglers.web.model.user.User;
import pl.isa.javasmugglers.web.repository.UserRepository;
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
}



