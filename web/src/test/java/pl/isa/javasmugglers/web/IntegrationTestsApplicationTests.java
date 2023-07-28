package pl.isa.javasmugglers.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.web.context.WebApplicationContext;
import pl.isa.javasmugglers.web.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class IntegrationTestsApplicationTests {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    MockMvc mockMvc;


    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void getExamList() throws Exception {

        mockMvc.perform(get("/examlist/" + userService.findByID(3L).getAuthToken())
                        .with(SecurityMockMvcRequestPostProcessors.user("agata@gmail.com")
                                .password("lato24")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("examlist"));


    }

}
