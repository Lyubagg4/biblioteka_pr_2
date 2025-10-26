package ru.zyryanova.biblioteka_boot.ControllerTest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.zyryanova.biblioteka_boot.Model.Person;
import ru.zyryanova.biblioteka_boot.Service.BookService;
import ru.zyryanova.biblioteka_boot.Service.PersonService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PeopleControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private PersonService personService;

    @MockitoBean
    private BookService bookService;

    @Test
    @WithMockUser
    void peoplePageLoads() throws Exception {
        mockMvc.perform(get("/people"))
                .andExpect(status().isOk())
                .andExpect(view().name("people/list"))
                .andExpect(model().attributeExists("person"));
    }
    @Test
    @WithMockUser
    void showPersonPage() throws Exception {
        Mockito.when(personService.show(1)).thenReturn(new Person());
        Mockito.when(personService.booksOfPerson(1)).thenReturn(java.util.Collections.emptyList());

        mockMvc.perform(get("/people/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("people/show"))
                .andExpect(model().attributeExists("person", "books"));
    }
    @Test
    @WithMockUser
    void newPersonPageLoads() throws Exception {
        mockMvc.perform(get("/people/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("people/new"))
                .andExpect(model().attributeExists("person"));
    }
    @Test
    @WithMockUser
    void createPersonSuccess() throws Exception {
        mockMvc.perform(post("/people").with(csrf())
                        .param("personFio", "Person")
                        .param("personYear", "1990"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/people"));
    }

}
