package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;

@Controller
public class PersonController {
    @Autowired
    /* JdbcTemplate facilite l'utilisation de jdbc, il execute flux de travail */
    private JdbcTemplate jdbcTemplate; 

    /* GET MAPPING avec URL person (page permettant de créer une person) */
    @GetMapping("/person")
    public String PersonForm(Model model) {
      model.addAttribute("person", new Person()); // utile pour thymeleaf
      return "index";
    }

 /* sert à enregistrer les resultats provenant de result.html  (requêtes POST) "/" */ 
  @PostMapping("/person")
    public String PersonSubmit(@ModelAttribute Person person, Model model) {
      model.addAttribute("person", person);
      jdbcTemplate.update(" INSERT INTO infopeopleimc (id, name, size, weight, imc, date) VALUES (default, ?, ?,?,?, default);", person.getName(), person.getSize(),person.getWeight(),person.getImc()); //insertion dans la BDD des valeurs de l'utilisateurs non enregistré
      return "sauvegarde"; // return la vue sauvegarde
    }
}
