package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.model.PersonConnection;

@Controller
public class SignupController {

    @Autowired
    /* JdbcTemplate facilite l'utilisation de jdbc, il execute flux de travail */
    private JdbcTemplate jdbcTemplate; 

    @Autowired
    /* sert à chiffrer le passsword  lors de l'ajout du compte dans la BDD */
    private PasswordEncoder passwordEncoder ;
  

    @GetMapping("/signup")

    /* fonction pour renvoyer la vue de creation de compte de la page signup.html */
    public String CompteForm(Model model) {
      model.addAttribute("PersonConnection", new PersonConnection()); //utilise pour thymleaf prépare et rend le formulaire d'inscription
      return "PersonConnection";
    }

  @PostMapping("/signup")
  
  /* traite le formulaire d'inscription d'un nouvel utilisateur de la page signup.html 
   *  RedirectView permet comme son nom l'indique de rediriger vers un URL
  */
    public RedirectView CompteSubmission(@ModelAttribute PersonConnection personconnection, Model model) {
      model.addAttribute("PersonConnection", personconnection);
      jdbcTemplate.update(" INSERT INTO infoconnectionperson (id, firstname, lastname, email, username, password) VALUES (default, ?, ?,?,?,?);",
      personconnection.getFirstname(),personconnection.getLastname(), personconnection.getEmail(),personconnection.getUsername(), passwordEncoder.encode(personconnection.getPassword() )); //insertion du nouvel utilisateur dans la base de données dans la table infoconnectionperson en utilsisant jdbc
      return new RedirectView("login"); // return la vue vers la page de login
    }

}
