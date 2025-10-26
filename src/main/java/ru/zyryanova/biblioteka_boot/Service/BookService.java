package ru.zyryanova.biblioteka_boot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.zyryanova.biblioteka_boot.Exception.ResourceNotFoundException;
import ru.zyryanova.biblioteka_boot.Model.Book;
import ru.zyryanova.biblioteka_boot.Model.Person;
import ru.zyryanova.biblioteka_boot.Repository.BookRepo;
import ru.zyryanova.biblioteka_boot.Repository.PersonRepo;


import java.util.List;

@Service
public class BookService{
    private final BookRepo bookRepository;
    private final PersonRepo personRepo;
    @Autowired
    public BookService(BookRepo bookRepository, PersonRepo personRepo) {
        this.bookRepository = bookRepository;
        this.personRepo = personRepo;
    }
    public List<Book> books(){
        return bookRepository.findAll();
    }
    public List<Book> indexWithSort(){
        return bookRepository.findAll(Sort.by("bookYear"));
    }
    public List<Book> indexWithPage(int page, int book_per_page){
        return bookRepository.findAll(PageRequest.of(page,book_per_page)).getContent();
    }
    public List<Book> indexWithPageWithSort(int page, int book_per_page){
        return bookRepository.findAll(PageRequest.of(page,book_per_page,Sort.by("bookYear"))).getContent();
    }
    public List<Book> find(String text){
        return bookRepository.findByBookNameStartingWith(text);
    }
    public void save(Book book){
        bookRepository.save(book);
    }
    public Book show(int id){
        return findOrThrow(id);
    }
    public void update(int id,Book book){
        findOrThrow(id);
        book.setBookId(id);
        bookRepository.save(book);
    }
    public void delete(int id){
        Book book = findOrThrow(id);
        bookRepository.delete(book);
    }
    public Person personOfBook(int id) {
        Book book = findOrThrow(id);
        return book != null ? book.getOwner() : null;
    }
    public void addPerson(int idOfTheBook, int idOfThePerson){
        Book book = findOrThrow(idOfTheBook);
        if(book.getOwner()!=null){
            throw new IllegalStateException("Книга уже выдана другому человеку");
        }
        book.setOwner(personRepo.findById(idOfThePerson).orElseThrow(() -> new ResourceNotFoundException("Человек с id " + idOfThePerson + " не найдена")));
        bookRepository.save(book);

    }
    public void free(int id){
        Book book = findOrThrow(id);
        if(book.getOwner()==null){
            throw new IllegalStateException("У книги нет пользователя");
        }
        book.setOwner(null);
        bookRepository.save(book);
    }
    private Book findOrThrow(int id){
        return bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Книга с id " + id + " не найдена"));
    }
}
