package ru.zyryanova.biblioteka_boot.ServiceTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.zyryanova.biblioteka_boot.Exception.ResourceNotFoundException;
import ru.zyryanova.biblioteka_boot.Model.Book;
import ru.zyryanova.biblioteka_boot.Model.Person;
import ru.zyryanova.biblioteka_boot.Repository.BookRepo;
import ru.zyryanova.biblioteka_boot.Repository.PersonRepo;
import ru.zyryanova.biblioteka_boot.Service.BookService;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepo bookRepo;
    @Mock
    private PersonRepo personRepo;
    @InjectMocks
    private BookService bookService;

    private Book freeBook;
    private Book occupiedBook;
    private Book unOccupiedBook;
    private Person person;

    @BeforeEach
    public void setUp(){
        freeBook = new Book();
        freeBook.setBookId(1);
        freeBook.setBookName("Free Book");

        person = new Person();
        person.setPersonId(8);
        person.setPersonFio("Ivan");

        occupiedBook = new Book();
        occupiedBook.setBookId(2);
        occupiedBook.setBookName("Occupied Book");
        occupiedBook.setOwner(person);

        unOccupiedBook = new Book();
        occupiedBook.setBookId(3);
        occupiedBook.setBookName("unOccupied Book");
    }
    @Test
    public void bookShouldBeExist(){
        Mockito.when(bookRepo.findById(1)).thenReturn(Optional.of(freeBook));
        Book result = bookService.show(1);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getBookId());
        Mockito.verify(bookRepo).findById(1);
    }
    @Test
    public void bookShouldNotExist(){
        Mockito.when(bookRepo.findById(2)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> bookService.show(2));
        Mockito.verify(bookRepo).findById(2);
    }
    @Test
    public void personShouldBeAddToFreeBookSuccess(){
        Mockito.when(bookRepo.assignBookToPerson(1,8)).thenReturn(1);
        bookService.addPerson(1,8);
        Mockito.verify(bookRepo).assignBookToPerson(1, 8);
    }

    @Test
    public void personShouldNotBeAddToOccupiedBook(){
        Mockito.when(bookRepo.findById(2)).thenReturn(Optional.of(occupiedBook));
        Assertions.assertThrows(IllegalStateException.class, () -> bookService.addPerson(2,8));
        Mockito.verify(bookRepo).findById(2);
    }
    @Test
    public void freeBookWithOwner(){
        Mockito.when(bookRepo.freeBook(2)).thenReturn(1);
        bookService.free(2);
        Mockito.verify(bookRepo).freeBook(2);
    }
    @Test
    public void freeBookWithoutOwner(){
        Mockito.when(bookRepo.findById(1)).thenReturn(Optional.of(freeBook));
        Assertions.assertThrows(IllegalStateException.class, () -> bookService.free(1));
        Mockito.verify(bookRepo).findById(1);
    }
    @Test
    public void deleteSuccess(){
        Mockito.when(bookRepo.findById(1)).thenReturn(Optional.of(freeBook));
        bookService.delete(1);
        verify(bookRepo).delete(freeBook);
        Mockito.verify(bookRepo).findById(1);
    }
    @Test
    public void deleteFail(){
        Mockito.when(bookRepo.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () ->  bookService.delete(1));
        Mockito.verify(bookRepo).findById(1);

    }
    @Test
    public void updateSuccess(){
        Mockito.when(bookRepo.findById(1)).thenReturn(Optional.of(freeBook));
        freeBook.setBookName("Hello world");
        bookService.update(1, freeBook);
        Assertions.assertEquals("Hello world",freeBook.getBookName());
        Mockito.verify(bookRepo).findById(1);
    }
    @Test
    public void updateFail(){
        freeBook.setBookName("Hello world");
        Assertions.assertThrows(ResourceNotFoundException.class, () -> bookService.update(1, freeBook));
        Mockito.verify(bookRepo).findById(1);

    }
    @Test
    public void nameOfTheBookStartsWithTextSuccess() {
        String prefix = "Free";
        List<Book> expectedBooks = List.of(freeBook);
        when(bookRepo.findByBookNameStartingWith(prefix))
                .thenReturn(expectedBooks);
        List<Book> result = bookService.find(prefix);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(expectedBooks, result);
        verify(bookRepo).findByBookNameStartingWith(prefix);
    }
    @Test
    public void nameOfTheBookStartsWithTextFail() {
        String prefix = "Unknown";
        when(bookRepo.findByBookNameStartingWith(prefix))
                .thenReturn(List.of());
        List<Book> result = bookService.find(prefix);
        Assertions.assertTrue(result.isEmpty());
        verify(bookRepo).findByBookNameStartingWith(prefix);
    }
}
