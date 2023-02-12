package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Person;

/* @Controller indique que la classe est un controlleur */
@Controller

public class IndexController  {
  /* @GetMapping sert à mmapper une requête HTTP GEt avec un handler spécifique , ici une requête provenant de "/" avec imcForm*/
    @GetMapping("/")
    /* requête du formulaire de la page index.html */
    /*  Un objet model est utilisé afin d'exposer un nouvel objet de type person à la vue templates index*/
    public String imcForm(Model model) {
      model.addAttribute("person", new Person()); //utile pour thymeleaf et th:object, Thymeleaf analyse le modèle index.html et évalue les différentes expressions du modèle afin de fournir le formulaire
      return  "index"; //return la vue index
    }

    /* @PostMapping sert à mapper une requête HTTP POST avec un handler spécifique, ici imcSubmit avec une requête POST "/" 
     * cela correspond au formulaire de la page index.html
    */
    @PostMapping("/")
    /* requête du formulaire de la page index.html */
    /* @ModelAttribute indique au controlleur qu'un oblet de type person doit être récupéré dans notre cas  */
    public String imcSubmit(@ModelAttribute Person person, Model model) {
      model.addAttribute("person", person);
      person.setImc( person.getWeight()/(person.getSize()*person.getSize())); //calcule de l'imc
      model.addAttribute("imc", person.getWeight()/(person.getSize()*person.getSize())); //ajoute
      return "result"; //return la vue result
    }
  }
    
