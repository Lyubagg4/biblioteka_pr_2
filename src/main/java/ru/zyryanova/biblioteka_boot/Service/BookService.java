package ru.zyryanova.biblioteka_boot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zyryanova.biblioteka_boot.Exception.BookOptimisticLockException;
import ru.zyryanova.biblioteka_boot.Exception.ResourceNotFoundException;
import ru.zyryanova.biblioteka_boot.Model.Book;
import ru.zyryanova.biblioteka_boot.Model.Person;
import ru.zyryanova.biblioteka_boot.Repository.BookRepo;
import ru.zyryanova.biblioteka_boot.Repository.PersonRepo;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class  BookService{
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
    @Transactional
    public void save(Book book){
        bookRepository.save(book);
    }
    public Book show(int id){
        return findOrThrow(id);
    }
    @Transactional
    public void update(int id,Book book){
        findOrThrow(id);
        book.setBookId(id);
        try{
            bookRepository.save(book);
        }catch (OptimisticLockingFailureException ex){
            throw new BookOptimisticLockException("Данные книги были изменены другим пользователем. " +
                    "Пожалуйста, обновите страницу и попробуйте снова.",ex);
        }
    }
    @Transactional
    public void delete(int id){
        Book book = findOrThrow(id);
        bookRepository.delete(book);
    }
    public Person personOfBook(int id) {
        Book book = findOrThrow(id);
        return book != null ? book.getOwner() : null;
    }
    @Transactional
    public void addPerson(int idOfTheBook, int idOfThePerson){
        int updatedRows = bookRepository.assignBookToPerson(idOfTheBook, idOfThePerson);
        if (updatedRows == 0) {
            Book book = findOrThrow(idOfTheBook);
            throw new IllegalStateException("Книга уже выдана другому человеку");
        }
    }
    @Transactional
    public void free(int id){
        int updatedRows = bookRepository.freeBook(id);
        if(updatedRows==0){
            Book book = findOrThrow(id);
            throw  new IllegalStateException("У книги нет пользователя");
        }
    }
    private Book findOrThrow(int id){
        return bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Книга с id " + id + " не найдена"));
    }
}

