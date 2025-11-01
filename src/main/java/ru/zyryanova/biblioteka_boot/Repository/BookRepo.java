package ru.zyryanova.biblioteka_boot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.zyryanova.biblioteka_boot.Model.Book;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {
    List<Book> findByBookNameStartingWith(String prefix);
    @Modifying
    @Query("UPDATE Book b SET b.owner.id = :personId WHERE b.bookId = :bookId AND b.owner IS NULL")
    int assignBookToPerson(@Param("bookId") int bookId, @Param("personId") int personId);
    @Modifying
    @Query("UPDATE Book b SET b.owner = NULL WHERE b.bookId = :bookId AND b.owner IS NOT NULL")
    int freeBook(@Param("bookId") int bookId);
    List<Book> findByOwnerPersonId(int id);

}
