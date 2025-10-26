package ru.zyryanova.biblioteka_boot.ServiceTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.zyryanova.biblioteka_boot.Exception.ResourceNotFoundException;
import ru.zyryanova.biblioteka_boot.Model.Book;
import ru.zyryanova.biblioteka_boot.Model.Person;
import ru.zyryanova.biblioteka_boot.Repository.PersonRepo;
import ru.zyryanova.biblioteka_boot.Service.PersonService;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepo personRepo;

    @InjectMocks
    private PersonService personService;

    @Test
    public void personShouldBeFound() {
        Person person = new Person();
        person.setPersonId(1);

        when(personRepo.findById(1)).thenReturn(Optional.of(person));

        Person result = personService.show(1);

        Assertions.assertEquals(1, result.getPersonId());
        verify(personRepo).findById(1);
    }

    @Test
    public void personShouldNotBeFound() {
        when(personRepo.findById(5)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> personService.show(5));

        verify(personRepo).findById(5);
    }

    @Test
    public void deletePersonSuccess() {
        Person person = new Person();
        person.setPersonId(1);

        when(personRepo.findById(1)).thenReturn(Optional.of(person));

        personService.delete(1);

        verify(personRepo).deleteById(1);
    }

    @Test
    public void deletePersonFail() {
        when(personRepo.findById(1)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> personService.delete(1));

        verify(personRepo).findById(1);
        verify(personRepo, never()).deleteById(anyInt());
    }

    @Test
    public void personBooksShouldBeReturned() {
        Person person = new Person();
        Book book = new Book();
        person.setBooks(List.of(book));

        when(personRepo.findById(2)).thenReturn(Optional.of(person));

        List<Book> books = personService.booksOfPerson(2);

        Assertions.assertEquals(1, books.size());
        verify(personRepo).findById(2);
    }
}
