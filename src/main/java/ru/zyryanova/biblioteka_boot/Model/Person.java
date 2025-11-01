package ru.zyryanova.biblioteka_boot.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int personId;

    @NotEmpty(message = "not empty")
    @Column(name = "person_fio")
    private String personFio;

    @Min(value = 0, message = "min value is 0")
    @Column(name = "person_year")
    private int personYear;

    @OneToMany(mappedBy = "owner")  // ← "owner" - это поле в классе Book
    private List<Book> books;

    @Version
    private Integer version;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getPersonFio() {
        return personFio;
    }

    public void setPersonFio(String personFio) {
        this.personFio = personFio;
    }

    public int getPersonYear() {
        return personYear;
    }

    public void setPersonYear(int personYear) {
        this.personYear = personYear;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

}