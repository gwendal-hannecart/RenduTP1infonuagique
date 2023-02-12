package com.example.demo.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/* @Repository est un composant particulier qui indique qu'une classe fournie des moyens de gérer des données (Insert, delete, update, read, write)  */
@Repository

/* JpaRepository (JPA = Java Persistante API) cet API contient tous les éléments nécessaire pour insérer, détruire, lire, écrire, mettre à jour des données ) 
et  sert donc dans notre cas à manipuler les données de type Person*/

public interface PersonRepository extends  JpaRepository<Person, Long> {

 // List<Person> findAllPerson();

  //Person findById(long id);
}

