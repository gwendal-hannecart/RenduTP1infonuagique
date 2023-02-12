package com.example.demo.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Person;
import com.example.demo.model.PersonConnection;
import com.example.demo.model.PersonConnexionRepo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HomeController {

/* @Autoriwerd permet à Spring de résoudre et d'injecter les beans dépendant dans l'instance de la classe */
  @Autowired
  /* JdbcTemplate facilite l'utilisation de jdbc, il execute flux de travail */
  private JdbcTemplate jdbcTemplate; 
  

  @Autowired
  private PersonConnexionRepo personConnexionRepo;
    @GetMapping("/home")
    /* renvoie la page d'accuiel une fois que l'utilisateur s'est authentifié dans login */
    public ModelAndView  Home(Model model) {
      model.addAttribute("PersonConnection", new PersonConnection()); // on crée un nouveau model afin de rassembler ses informations.
      ModelAndView home = new ModelAndView("home");
      final String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName(); //permet de récupérer le nom de l'utilisateur (pour ensuite l'afficher dans la page home)
	  	home.addObject("PersonConnection", personConnexionRepo.findByUsername(currentUserName)); //  on ajoute à la vue un modèle, le nom de l'utilisateur de la session
		  return home; //on retourne la vue et le modele
    }

    @PostMapping("/home")
    /* requête POST provennant de /home (formulaire de la page de login) 
     * normallement plus utile car c'est spring security avec le WebSecurityConfig qui gère l'authentification
    */
    public String HomePost(@ModelAttribute PersonConnection personconnection, Model model) {
      model.addAttribute("PersonConnection", personconnection);
      return "home";
    }
   
    @PostMapping("/recordperson")
    /* traite la requête POST du formulaire d'ajout de la page home.html (demande POST envoyer par home.js)
     * HttpServletRequest représente la requête HTTP au serveur et HttpServletResponse  la réponse du serveur en HTTP
     */
    public      void  AddRecord(HttpServletRequest request,HttpServletResponse response) throws IOException {
      String requestData = request.getReader().lines().collect(Collectors.joining()); // cette ligne permet de récuperer le body de la requête HTTP faite au serveur (ici celle de home.js dont on a définit comment les informations étaient placées)
      //request data de la forme {
      String[] array = requestData.split(",");// requestData séparé en plusieurs partie, séparer {name:valeurName avec size:valeurSize et weight:valeurWeight}
      String[] arrayName = array[0].split(":"); // permet de séparer {name  et la valeurName
      String[] arrayName2 = arrayName[1].split("\""); // permet de supprimer le caractère " de valeurName
      String nameStr=arrayName2[1]; //obtention de l'username de l'utilisateur
      String[] arraySize =array[1].split(":"); // obtention de la partie "size":"valeur" et séparation en deux éléments : "size" , "valeur"
      String[] arraySize2= arraySize[1].split("\""); // suppresion du caractère " de la partie valeur
      String sizeStr=arraySize2[1]; //obtention de la valeur size en chaine de caractères à ajouter dans la BDD
      String[] arrayWeight =array[2].split(":"); // obtention de la partie "weight":"valeurWeight" et séparation en deux parties: "weight" et "valeurWeight"
      String[] arrayWeight2= arrayWeight[1].split("\""); //decomposition de "valeurWeight"}" ""
      String weightStr=arrayWeight2[1];//obtention de la valeur weight en string
      Float size=Float.parseFloat(sizeStr); //convertion en float
      Float weight=Float.parseFloat(weightStr);
      float imc=weight/(size*size);// calcule de l'imc avec les nouvelles valeurs
      Person person =new Person(nameStr, size, weight,imc); // création d'une nouvelle personn avec les nouvelles valeurs
      jdbcTemplate.update(" INSERT INTO  imcpeoplecompte ( name, size, weight, imc, date) VALUES ( ?, ?,?,?, default);", person.getName(), person.getSize(),person.getWeight(),person.getImc()); //insertion dans la table imcpeoplecompte réservé à ceux qui ont un compte 
      response.getWriter().println("[{size="+sizeStr+",weight="+weight+",imc="+imc+",date="+java.time.LocalDate.now()+"}"); // écrit dans la réponse  avec l'imc et la date d'enregistrement
      response.setStatus(200); //indique status Ok
     
    }


   @PostMapping("/obtainperson")
   /* permet d'obtenir l'ensemble des valeur enregistré des informations d'imc d'un utilisateur ayant un compte (table imcpeoplecompte) à partir de home.html (requête réalisée par home.js) */
    public     void  ObtainRecord(HttpServletRequest request,HttpServletResponse response) throws IOException {
      String requestData = request.getReader().lines().collect(Collectors.joining()); //obtention du body de la requête html (et ainsi de l'username de l'utilisateur)
      String sql="SELECT size,weight,imc, date FROM imcpeoplecompte where name='"+requestData.toString()+"'"; // requête permettant de retrouver l'ensemble des données d'imc d'une personne ayant un compte car l'username est unique
      List<Map<String,Object>> list=jdbcTemplate.queryForList(sql); 
      /* format de la chaine de caractère de reponse : [{size=sizeValue, weight=weightValue, imc=imcValue, date=dateValue}, {size=sizeValue, weight=weightValue, imc=imcValue, date=dateValue}, ...] */
      response.getWriter().println(list.toString());// les données sous forme de chaine de caractère dans le body de la response HTTP
      response.setStatus(200); //status OK
    }

    @PostMapping("/delete")
    /* Permet de supprimer une ligne de la table imcpeoplecompte base de données concernant un enregistrement d'une personne ayant un compte à partir de home.html (raquête faite dans le fichier home.js )*/
    public      void  deleteRecord(HttpServletRequest request,HttpServletResponse response) throws IOException, DataAccessException, ParseException {
      String requestData = request.getReader().lines().collect(Collectors.joining()); // obtiention du body de la requête faite au serveur
      /* requete de la forme "username:valeurSize:valeurWeight:valeurImc:valeurDate" */
      String[] array = requestData.split(":"); // on sépare la chaîne de caractère avec le ":""
      String name=array[0];// obtention de l'username de l'utilisateur
      String sizeStr=array[1];// obtention de l'username de la taille en string
      String weightStr=array[2]; // obtention du poids en String
      String imcStr=array[3];// obtention de l'username de l'imc en String
      String date=array[4]; // obtention de l'username de la date
      Float size=Float.parseFloat(sizeStr); // conversion en float
      Float weight=Float.parseFloat(weightStr);
      float imc=Float.parseFloat(imcStr);
      jdbcTemplate.update(" DELETE FROM imcpeoplecompte WHERE  name=? AND size=? AND weight=? AND imc=? AND date=?;", name, size,weight,imc,new SimpleDateFormat("yyyy-MM-dd").parse(date)); // suppression dans la table  imcpeoplecompte de cette ligne (SimpleDateFormat("yyyy-MM-dd").parse(date) permet de convertir le string date au bon format et au type date )
      response.getWriter().println("success"); // reponse body 
      response.setStatus(200);// status OK
 
    }
}
