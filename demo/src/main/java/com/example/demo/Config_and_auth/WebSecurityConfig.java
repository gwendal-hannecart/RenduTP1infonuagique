package com.example.demo.Config_and_auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/* @Configuration est utiliser pour l'injection de dépendance et sert à indiquer au Spring Container
 * que des elements Bean sont présents et doivent être traités
 */
@Configuration
/* @EnableWebSecurity permet d'activer Srping Security notamment pour le Web et le Spring MVC */
@EnableWebSecurity


/* WebMvcConfiger permet de personnaliser la configuration par defaut du Spring MVC*/
public class WebSecurityConfig implements WebMvcConfigurer  {

	/* @Autoriwerd permet à Spring de résoudre et d'injecter les beans dépendant dans l'instance de la classe */
	@Autowired
	/* UserDetailsService sert à retrouver le password et le username de l'utilsiateur */
    private UserDetailsService userDetailsService; 


	/* Bean est simpllement un element qui doit être géré par le spring container */
	@Bean
	/* La fonction passwEncoder sert à hasher les mots de passe car par defaut ils sont en clair */
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); // BCryptEncoder est utilisé
    }

	@Bean
	/* SecurityFilterChain  */
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http	
			.authorizeHttpRequests((requests) -> requests
			
				.requestMatchers("/", "/login","/delete" ,"/person","/goodbye","/sauvegarde","/goodbye","/recordperson","/obtainperson","/PersonConnection","/signup", "/signup", "/AffichageBDD","/resources/**").permitAll() // pas nécessaire d'être connecté afin d'avoir accès à ces pages
				.requestMatchers("/static/**","/ressources/static/js/home.js","/js/home.js").authenticated() //être authentifié afin que de pouvoir accèder à ces chemin (et que les requetes js fonctionnent)
				.requestMatchers("/home","/home?continue").authenticated() //authentifie pour avoir accès à la page login
				.requestMatchers(HttpMethod.POST, "/js/home.js").authenticated() //authentifie pour faire les requete post 
			
				)
				.formLogin(
					form -> form
					
							.loginPage("/login")
							.loginProcessingUrl("/login") // page de connexion
							.defaultSuccessUrl("/home") //page par defaut si l'authentification est réussie
							.permitAll()
			).logout(
					logout -> logout
							.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
							.logoutSuccessUrl("/goodbye")// page après déconnexion
							.invalidateHttpSession(true) //invalider la session
							.deleteCookies("JSESSIONID") //détruire le cookie
							.permitAll()
							
			)
			.cors().and().csrf().disable(); //permet de regler le problème CORS 
	return http.build(); //build obkect
				
		


	}

	@Autowired
	/* AuthenticationManagerBuilder permet de fournir les fonctionnalités courantes du gestionnaire d'authentification et ainsi ajouter un userDetailsService customisé
	 * AuthentificationProvider est nécessaire avec Sping Security pour l'utilisation de JPA et hibernate 
	 */
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)//UserDetailsService est utilisé par DaoAuthenticationProvider afin de retrouver le username et le password
                .passwordEncoder(passwordEncoder());// specifie le passwordENcoder utiulisé par DaoAuthentificationProvider 
		
    } 

	@Override
	/* enregistre les emplacement des gestionnaires de ressources pour servir les ressources statiques */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
          .addResourceHandler("/resources/**") //ajoute un handler
          .addResourceLocations("/resources/");	//indique la localisation des ressources
    }

	@Bean
	/*  WebSecurityCustomizer permet de personnaliser webSecurity*/
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/fonts/**", "/css/**"); //permet d'ignorer les requêtes venant de ces chemin pour SpringSecurity
    }
	
	@Override
	/* addCorsMappings permet de configurer les cross-origin requêtes (CORS)*/
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*"); //permet d'autoriser les requêtes CORS pour tous les URl  du site
    }

}