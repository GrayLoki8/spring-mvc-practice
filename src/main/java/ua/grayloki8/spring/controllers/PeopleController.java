package ua.grayloki8.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.grayloki8.spring.dao.PersonDAO;
import ua.grayloki8.spring.models.Person;
import ua.grayloki8.spring.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people",personDAO.index());
        return "people/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id")int id,Model model){
       model.addAttribute("person",personDAO.show(id));
        return "people/show";
    }
    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person",new Person());
        return "people/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors()){

            return "people/new";
        }
        personDAO.save(person);
        return "redirect:/people";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model,@PathVariable("id")int id){
        model.addAttribute("person",personDAO.show(id));
        return "people/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person")Person person,
                         @PathVariable("id")int id){
        System.out.println("patch");
        personDAO.update(id,person);
        return "redirect:/people";
    }
    @PostMapping("/{id}")
    public String update2(@ModelAttribute("person") @Valid Person person,
                         @PathVariable("id")int id,BindingResult bindingResult){
        System.out.println("post");
        boolean same=personDAO.same(person,id);
        System.out.println(same);
        if (!same){
        personValidator.validate(person,bindingResult);

        if (bindingResult.hasErrors()){
            return "people/edit";
        }}
        personDAO.update(id,person);
        return "redirect:/people";
    }
    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id")int id){
        System.out.println("delete");

        personDAO.delete(id);
        return "redirect:/people";
    }
    @PostMapping("/{id}/delete")
    public String delete2(@PathVariable("id")int id){
        System.out.println("post");

        personDAO.delete(id);
        return "redirect:/people";
    }
}




















