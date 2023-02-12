package com.example.demo.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class AffichageBDDController {
    /* @Autoriwerd permet à Spring de résoudre et d'injecter les beans dépendant dans l'instance de la classe */
    @Autowired
    private PersonRepository personRepo; // permet dans ce controller de de gérer des données de typePerson

    @GetMapping("/AffichageBDD")
    /* Affichage des données de infopeopleimc dans AffichageBDD.html (personne qui n'ont pas de compte) */
    public  ModelAndView View(Model model) {
      ModelAndView AffichageBDD = new ModelAndView("AffichageBDD"); //Modelandview contient à la fois le modele et la vue afin que le controlleur puissent renvoyer les deux en une seule valeur de retour, ici la vue AffichageBDD
	  	AffichageBDD.addObject("persons", personRepo.findAll()); // permet de récupérer l'ensemble des résultats de la BDD concernant la table infopeopleimc
		  return AffichageBDD; // le modèle (l'ensemble des données person de la table infpeopleimc ) et la vue (affichage BDD)
    }
}
