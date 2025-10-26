package ru.zyryanova.biblioteka_boot.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.zyryanova.biblioteka_boot.Model.Person;
import ru.zyryanova.biblioteka_boot.Service.BookService;
import ru.zyryanova.biblioteka_boot.Service.PersonService;


@Controller
@RequestMapping("/people")
public class PeopleController {
    PersonService personService;
    BookService bookService;

    @Autowired
    public PeopleController(PersonService personService, BookService bookService) {
        this.personService = personService;
        this.bookService = bookService;
    }

    @GetMapping()
    public String people(Model model){
        model.addAttribute("person",personService.people());
        return "people/list";
    }
    @GetMapping("/new")
    public String create(@ModelAttribute("person") Person person){
        return "people/new";
    }
    @GetMapping("/{id}")
    public String show(Model model,
                       @PathVariable("id") int id){
        model.addAttribute("person",personService.show(id));
        model.addAttribute("books", personService.booksOfPerson(id));
        return "people/show";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model,@PathVariable("id") int id){
        model.addAttribute("person",personService.show(id));
        return "people/edit";
    }
    @PostMapping()
    public String save(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        personService.save(person);
        return "redirect:/people";
    }
    @PatchMapping("/{id}")
    public String edit(@ModelAttribute("person") @Valid Person person,
                       BindingResult bindingResult,
                       @PathVariable("id") int id){
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        personService.update(id, person);
        return "redirect:/people";
    }
    @PatchMapping("/{book_id}/free")
    public String free(@ModelAttribute("person") Person person,
                       @PathVariable("book_id") int id){
        bookService.free(id);
        return "redirect:/books";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id" )int id){
        personService.delete(id);
        return "redirect:/people";
    }


}
