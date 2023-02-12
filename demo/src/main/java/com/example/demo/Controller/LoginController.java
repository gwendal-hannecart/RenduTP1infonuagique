package com.example.demo.Controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.PersonConnection;

import org.springframework.ui.Model;

@Controller
public class LoginController {
    @GetMapping("/login")
    /* page de connexion login.html*/
    public String LoginGet(Model model) {
      model.addAttribute("PersonConnection", new PersonConnection());
      return "login";// return la vue login
    }
    
  @PostMapping("/login")
    /* page de connexion login.html*/
    public String LoginGet(@ModelAttribute PersonConnection personconnection, Model model) {
      return "home"; //normallement inutile car l'authentification est géré par le WebSecurityConfig
    }


}
