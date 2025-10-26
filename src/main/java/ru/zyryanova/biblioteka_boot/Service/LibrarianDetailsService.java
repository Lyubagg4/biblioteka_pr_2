package ru.zyryanova.biblioteka_boot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.zyryanova.biblioteka_boot.Model.Librarian;
import ru.zyryanova.biblioteka_boot.Model.LibrarianDetails;
import ru.zyryanova.biblioteka_boot.Repository.LibrarianRegistrationRepo;

@Service
public class LibrarianDetailsService implements UserDetailsService {
    private final LibrarianRegistrationRepo librarianRegistrationRepo;

    @Autowired
    public LibrarianDetailsService(LibrarianRegistrationRepo librarianRegistrationRepo) {
        this.librarianRegistrationRepo = librarianRegistrationRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Librarian librarian = librarianRegistrationRepo.findByUsername(username);
        if(librarian==null){
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return new LibrarianDetails(librarian);
    }
}
