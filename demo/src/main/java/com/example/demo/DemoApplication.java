package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.demo.Controller.PersonController;
import com.example.demo.Controller.SignupController;
import com.example.demo.model.Person;
import com.example.demo.model.PersonConnection;
import com.example.demo.model.PersonConnexionRepo;
import com.example.demo.model.PersonRepository;
import com.example.demo.Config_and_auth.UserService;
import com.example.demo.Config_and_auth.WebSecurityConfig;
import com.example.demo.Controller.AffichageBDDController;
import com.example.demo.Controller.GoobyeController;
import com.example.demo.Controller.HomeController;
import com.example.demo.Controller.IndexController;
import com.example.demo.Controller.LoginController;

/* scanBasePackageClasses permet d'indiquer à spring où chercher les classes avec des annotations  */
@SpringBootApplication(scanBasePackageClasses = { GoobyeController.class ,HomeController.class,LoginController.class, AffichageBDDController.class ,SignupController.class,PersonController.class,Person.class, IndexController.class, PersonConnection.class, WebSecurityConfig.class, UserService.class, PersonConnexionRepo.class, PersonRepository.class})
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}