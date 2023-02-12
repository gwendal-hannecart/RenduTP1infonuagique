package com.example.demo.Config_and_auth;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.PersonConnection;
import com.example.demo.model.PersonConnexionRepo;

/* Service une annotation spéciale qui indique que c'est une classe fournissant un service (et appartient à la couche service de spring) */
@Service

/*  UserDetailsService est l'interface permettant de charger les informations de l'utilisateur, le DaoAuthentificationProvider charge les informations de l'utilisateur durant   l'autehntification*/

public class UserService  implements  UserDetailsService {

    @Autowired
    private PersonConnexionRepo repository;

    @Override
    /* loadUserByUsername est une fonction de userDetailsService pouvant être customisée afin de retrouver l'utilisateur en retournant un UserDetails */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        PersonConnection userConnection = repository.findByUsername(username); // cherche l'utilisateur par son username
    
      //  System.out.println(userConnection.toString());
        return userConnection;//renvoie les informations de l'utilisateur
    }
}
