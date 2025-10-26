package ru.zyryanova.biblioteka_boot.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "librarian")
public class Librarian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Column(name = "person_fio")
    private String personFio;

    @Column(name = "person_year")
    @Min(value = 0)
    private int personYear;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Column(name = "password")
    private String password;

    public Librarian() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPersonFio() { return personFio; }
    public void setPersonFio(String personFio) { this.personFio = personFio; }

    public int getPersonYear() { return personYear; }
    public void setPersonYear(int personYear) { this.personYear = personYear; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
