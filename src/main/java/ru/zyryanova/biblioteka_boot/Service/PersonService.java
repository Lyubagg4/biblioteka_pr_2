package ru.zyryanova.biblioteka_boot.Service;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import ru.zyryanova.biblioteka_boot.Exception.PersonOptimisticLockException;
import ru.zyryanova.biblioteka_boot.Exception.ResourceNotFoundException;
import ru.zyryanova.biblioteka_boot.Model.Book;
import ru.zyryanova.biblioteka_boot.Model.Person;
import ru.zyryanova.biblioteka_boot.Repository.BookRepo;
import ru.zyryanova.biblioteka_boot.Repository.PersonRepo;


import java.util.*;

@Service
public class PersonService {
    private PersonRepo personRepository;
    private BookRepo bookRepository;

    public PersonService(PersonRepo personRepository, BookRepo bookRepository) {
        this.personRepository = personRepository;
        this.bookRepository = bookRepository;
    }
    public List<Person> people(){
        return personRepository.findAll();
    }
    public void save(Person person){
        personRepository.save(person);
    }
    public Person show(int id){
        return findPersonOrThrow(id);
    }
    public void update(int id, Person person){
        Person existing = findPersonOrThrow(id);
        existing.setPersonId(id);
        existing.setPersonFio(person.getPersonFio());
        existing.setPersonYear(person.getPersonYear());
        existing.setVersion(person.getVersion());
        try{
            personRepository.save(existing);
        }catch (OptimisticLockingFailureException ex){
            throw new PersonOptimisticLockException("Данные человека были изменены другим пользователем. " +
                    "Пожалуйста, обновите страницу и попробуйте снова.",
                    ex);
        }
    }
    public void delete(int id){
        findPersonOrThrow(id);
        personRepository.deleteById(id);
    }
    public List<Book> booksOfPerson(int id){
        return bookRepository.findByOwnerPersonId(id);
    }
    private Person findPersonOrThrow(int id){
        return personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Читатель с id " + id + " не найден"));
    }
}
