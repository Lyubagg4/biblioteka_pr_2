package ru.zyryanova.biblioteka_boot.ControllerTest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.zyryanova.biblioteka_boot.Controller.BooksController;
import ru.zyryanova.biblioteka_boot.Model.Book;
import ru.zyryanova.biblioteka_boot.Model.Person;
import ru.zyryanova.biblioteka_boot.Service.BookService;
import ru.zyryanova.biblioteka_boot.Service.PersonService;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BooksController.class)
@AutoConfigureMockMvc
public class BooksControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PersonService personService;

    @MockitoBean
    private BookService bookService;

    @Test
    @WithMockUser
    void booksPageLoads() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("books/list"))
                .andExpect(model().attributeExists("books"));
    }
    @Test
    @WithMockUser
    void showBookPage() throws Exception {
        Mockito.when(bookService.show(1)).thenReturn(new Book());
        Mockito.when(bookService.personOfBook(1)).thenReturn(new Person());
        Mockito.when(personService.people()).thenReturn(java.util.Collections.emptyList());
        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("books/show"))
                .andExpect(model().attributeExists("book", "person", "people"));
    }
    @Test
    @WithMockUser
    void newBookPageLoads() throws Exception {
        mockMvc.perform(get("/books/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("books/new"))
                .andExpect(model().attributeExists("book"));
    }
    @Test
    @WithMockUser
    void saveBookSuccess() throws Exception {
        mockMvc.perform(post("/books").with(csrf())
                        .param("bookName", "Test Book")
                        .param("bookAuthor", "Some Author")
                        .param("bookYear", "2022"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
    }

}
