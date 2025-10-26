package ru.zyryanova.biblioteka_boot.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class LibrarianDetails implements UserDetails{


    private final Librarian librarian;


    @Autowired
    public LibrarianDetails(Librarian librarian) {
        this.librarian = librarian;
    }

    @Override
    public String getUsername() {
        return librarian.getUsername();
    }

    @Override
    public String getPassword() {
        return librarian.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
}
