package ru.zyryanova.biblioteka_boot.ServiceTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.zyryanova.biblioteka_boot.Model.Librarian;
import ru.zyryanova.biblioteka_boot.Repository.LibrarianRegistrationRepo;
import ru.zyryanova.biblioteka_boot.Service.RegistrationService;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

    @Mock
    private LibrarianRegistrationRepo librarianRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegistrationService registrationService;

    @Test
    public void passwordShouldBeEncodedBeforeSaving() {
        Librarian librarian = new Librarian();
        librarian.setId(1);
        librarian.setUsername("admin");
        librarian.setPassword("123");

        when(passwordEncoder.encode("123")).thenReturn("encoded_pass");

        registrationService.register(librarian);

        Assertions.assertEquals("encoded_pass", librarian.getPassword());
        verify(passwordEncoder).encode("123");
        verify(librarianRepo).save(librarian);
    }

    @Test
    public void registerLibrarianSuccess() {
        Librarian librarian = new Librarian();
        librarian.setUsername("test");

        registrationService.register(librarian);

        verify(librarianRepo).save(librarian);
    }
}
