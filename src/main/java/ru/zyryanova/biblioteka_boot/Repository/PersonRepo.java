package ru.zyryanova.biblioteka_boot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.zyryanova.biblioteka_boot.Model.Person;

@Repository
public interface PersonRepo extends JpaRepository<Person, Integer> {

}
