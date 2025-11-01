package ru.zyryanova.biblioteka_boot.ControllerTest;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.zyryanova.biblioteka_boot.Controller.AuthController;
import ru.zyryanova.biblioteka_boot.Security.SecurityConfig;
import ru.zyryanova.biblioteka_boot.Service.LibrarianDetailsService;
import ru.zyryanova.biblioteka_boot.Service.RegistrationService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private RegistrationService registrationService;
    @MockitoBean
    private LibrarianDetailsService librarianDetailsService;

    @Test
    @WithMockUser
    void registrationPageLoad() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }


    @Test
    @WithMockUser
    void loginPageLoads() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }
    @Test
    @WithMockUser
    void postRegistration() throws Exception{
        mockMvc.perform(post("/registration").with(csrf())
                .param("personFio","Ivanov Ivan Ivanovich")
                .param("personYear", "2003")
                .param("username", "Ivan2006")
                .param("password", "IvanDurak"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
    }
}
