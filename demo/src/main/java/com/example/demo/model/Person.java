package com.example.demo.model;

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
@Table(name = "infopeopleimc")

public class Person {

    @Id // indique la clef primaire de la table
    @GeneratedValue(strategy=GenerationType.AUTO) //GenerateValue permet de générer de manière automatique la valeur de la clef primaire, la stategie  AUTO generationtype.auto, la valeur est déterminée en fonction du type de la clef primaire
    @Column(name = "id") //indique la colonne de la table de la base de données auquelle appartient la variable
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "size")
    private Float size;
    @Column(name = "weight")
    private Float weight;
    @Column(name = "imc")
    private Float imc;
    @Column(name = "date")
    private String date;
 

   
    public void setDate(String date){
        this.date=date;
    }
    
    public String getDate(){
        return this.date;
    }

    public void setImc(Float imc){
        this.imc=imc;
    }
    
    public Float getImc(){
        return this.imc;
    }

    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return this.name;
    }
    public void setSize(Float size){
        this.size=size;
    }
    public Float getSize(){
        return this.size;
    }
    public void setWeight( Float weight){
        this.weight=weight;
    }
    public Float getWeight(){
        return this.weight;
    }
   
    public Long getId(){
        return this.getId();
    }
    public Person(){}

    public Person(String NamePers, Float SizePers, Float WeightPers){
        this.setName(NamePers);
        this.setSize(SizePers);
        this.setWeight(WeightPers);
    }
    public Person(String NamePers, Float SizePers, Float WeightPers,Long id, Float imc ){
        this.setName(NamePers);
        this.setSize(SizePers);
        this.setWeight(WeightPers);
        this.id=id;
        this.setImc(imc);
    }
    public Person(String NamePers, Float SizePers, Float WeightPers, Float imc ){
        this.setName(NamePers);
        this.setSize(SizePers);
        this.setWeight(WeightPers);
        this.setImc(imc);
    }
    
    @Override
	public String toString() {
		return "Tutorial [id=" + id + ",name=" + name + ",size=" + size + ",weight=" + weight +",imc="+imc+"]";
	}


}