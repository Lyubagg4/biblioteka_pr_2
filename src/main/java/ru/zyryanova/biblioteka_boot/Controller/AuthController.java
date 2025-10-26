package ru.zyryanova.biblioteka_boot.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.zyryanova.biblioteka_boot.Model.Librarian;
import ru.zyryanova.biblioteka_boot.Service.RegistrationService;

@Controller
public class AuthController {
    private final RegistrationService registrationService;

    @Autowired
    public AuthController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }
    @GetMapping("/registration")
    public String registration(@ModelAttribute("librarian") Librarian librarian){
        return "registration";
    }
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) boolean error,
                            @RequestParam(value = "logout", required = false) boolean logout,
                            Model model) {
        if(error){
            model.addAttribute("errorMessage"," Неверный логин или пароль");
        }
        if(logout){
            model.addAttribute("logoutMessage","Вы успешно вышли из системы");
        }
        return "login";
    }
    @PostMapping("/registration")
    public String createPerson(@ModelAttribute("librarian") @Valid Librarian librarian, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/registration";
        }
        registrationService.register(librarian);
        return"redirect:/books";
    }

}
