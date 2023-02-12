package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


/* @Entity indique juste à JPA que cette classe est un POJO représentant des données qui peuvent-être enregistrées dans une base de données 
 * POJO pour Plain Old Java Object”, c'est un objet Java ordinaire
*/
@Entity

/* @Table permet d'indiquer la table dans la base de données enregistrant les informations */
@Table(name = "infoconnectionperson")

/* User Details fournit les informations essentielles d'un utilisateur tel que un adresse électronique. User détails n'est pas utulisé directement pour l'authentification, il est encapsulé dans un objet authentification ( UserDetailsService)
 * certaines fonctions doivent être implémentées
 */
public class PersonConnection implements UserDetails {
    @Id // indique la clef primaire de la table
    @GeneratedValue(strategy=GenerationType.AUTO) //GenerateValue permet de générer de manière automatique la valeur de la clef primaire, la stategie  AUTO generationtype.auto, la valeur est déterminée en fonction du type de la clef primaire
    @Column(name = "id") //indique la colonne de la table de la base de données auquelle appartient la variable
    private Long id;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "email")
    private String email;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    
    public void setId(Long id){
        this.id=id;
    }
   
    public Long getId(){
        return this.id;
    }

    public void setFirstname( String firstname){
        this.firstname=firstname;
    }

    public String getFirstname(){
        return this.firstname;
    }
    public void setLastname( String lastname){
        this.lastname=lastname;
    }

    public String getLastname(){
        return this.lastname;
    }

    public void setEmail( String email){
        this.email=email;
    }

    public String getEmail(){
        return this.email;
    }
    
    public void setUsername( String username){
        this.username=username;
    }

    
    public String getUsername(){
        return this.username;
    }

    public void setPassword( String password){
        this.password=password;
    }

    public String getPassword(){
        return this.password;
    }


    public PersonConnection(){}

    public PersonConnection(String firstname, String lastname, String email, String username,String password ){
        this.setFirstname(firstname);
        this.setLastname(lastname);
        this.setEmail(email);
        this.setUsername(username);
        this.setPassword(password);
    }
    public PersonConnection( String firstname, String lastname, String email, String username,String password, Long Id){
        this.setFirstname(firstname);
        this.setLastname(lastname);
        this.setEmail(email);
        this.setUsername(username);
        this.setPassword(password);
        this.setId(Id);
    }

    @Override
	public String toString() {
		return "Tutorial [id=" + this.getId() + ", firstname=" + this.getFirstname() + ",lastname=" + this.getLastname() + ", email=" + this.getEmail() +",username="+this.getUsername()+"]";
	}

    /* Implémentation des fonctions  de l'interface UserDetails */
    @Override
    /* getAuthorities définie l'autorité des utilisateurs, comme l'application en n'a pas plus besoin, le role USER est donné*/
    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        return authorities;
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return true;
    }

}