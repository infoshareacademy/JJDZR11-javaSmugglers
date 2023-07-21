package pl.isa.javasmugglers.web.security.config;



import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.isa.javasmugglers.web.service.UserService;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfiguration {



    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http
                .csrf().disable()
                .authorizeHttpRequests()
                                .requestMatchers("/examlist/**").hasAuthority("PROFESOR") // Tylko użytkownicy z rolą PROFESOR mają dostęp
                                .requestMatchers("registration/**", "registration/professor/**")
                                .permitAll()
                                .requestMatchers("/userinactive/**","/registrationFailed", "/rf/**","/login/**","register/**", "/save/**","/registrationsuccesfull/**", "/", "/addnew/**","/logo.gif","/logo_blue.jpg")
                                .permitAll()
                .anyRequest()
                .authenticated().and()
                .formLogin()
                    .passwordParameter(bCryptPasswordEncoder.toString())
                    .loginProcessingUrl("/login")
                    .loginPage("/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .permitAll()
                    .defaultSuccessUrl("/succeslogin.html", false)
                //przekierowanie jeśli warunek authenticated() nie jest spełniony
                .and()
                .exceptionHandling()
                .accessDeniedPage("/login");



        return http.build();
    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }
}


