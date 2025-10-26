package ru.zyryanova.biblioteka_boot.Service;

import org.springframework.stereotype.Service;
import ru.zyryanova.biblioteka_boot.Exception.ResourceNotFoundException;
import ru.zyryanova.biblioteka_boot.Model.Book;
import ru.zyryanova.biblioteka_boot.Model.Person;
import ru.zyryanova.biblioteka_boot.Repository.PersonRepo;


import java.util.*;

@Service
public class PersonService {
    private PersonRepo personRepository;

    public PersonService(PersonRepo personRepository) {
        this.personRepository = personRepository;
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
        findPersonOrThrow(id);
        person.setPersonId(id);
        personRepository.save(person);
    }
    public void delete(int id){
        findPersonOrThrow(id);
        personRepository.deleteById(id);
    }
    public List<Book> booksOfPerson(int id){
        Person person = findPersonOrThrow(id);
        return person.getBooks();
    }
    private Person findPersonOrThrow(int id){
        return personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Читатель с id " + id + " не найден"));
    }
}

