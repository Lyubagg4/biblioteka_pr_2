package ru.zyryanova.biblioteka_boot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zyryanova.biblioteka_boot.Model.Librarian;

@Repository
public interface LibrarianRegistrationRepo extends JpaRepository<Librarian, Integer> {
    Librarian findByUsername(String username);
}
