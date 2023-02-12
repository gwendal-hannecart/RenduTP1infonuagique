package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GoobyeController {
    @GetMapping("/goodbye")
    /* Page que l'utilisateur voit lorqu'il se déconnecte en cliquant sur le bouton logout de home.html */
    public  String  ViewGoodbye() {
      return "goodbye"; //retourne vue goobye
    }

    @PostMapping("/goodbye")
     /* Page que l'utilisateur voit lorqu'il se déconnecte en cliquant sur le bouton logout de home.html */
    public  String ViewGoodByePost() {
      return "goodbye";
    }
}
