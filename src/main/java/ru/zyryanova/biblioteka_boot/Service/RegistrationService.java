package ru.zyryanova.biblioteka_boot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.zyryanova.biblioteka_boot.Model.Librarian;
import ru.zyryanova.biblioteka_boot.Repository.LibrarianRegistrationRepo;


@Service
public class RegistrationService {
    private final LibrarianRegistrationRepo librarianRegistrationRepo;
    private final PasswordEncoder passwordEncoder; // ← ДОБАВЬТЕ ЭТО

    @Autowired
    public RegistrationService(LibrarianRegistrationRepo librarianRegistrationRepo,
                               PasswordEncoder passwordEncoder) {
        this.librarianRegistrationRepo = librarianRegistrationRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(Librarian librarian) {
        librarian.setPassword(passwordEncoder.encode(librarian.getPassword()));
        librarianRegistrationRepo.save(librarian);
    }
}
