package com.ditra.ditraschool.core.eleve.Models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@Entity
@Where(clause = "deleted = false")
@SQLDelete(sql=" UPDATE eleve SET deleted = true WHERE id = ?")
public class Eleve {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String matricule;
  private String nom;
  private String prenom;
  private String sexe;
  private Date dateNaissance;
  private String lieuNaissance;
  private String adresse;
  private String tuteur;

  private String nomPere;
  private String numCinPere;
  private String delivreCinPere;
  private String telephonePere;

  private String nomMere;
  private String numCinMere;
  private String delivreCinMere;
  private String telephoneMere;

  private String nomAutre;
  private String numCinAutre;
  private String delivreCinAutre;
  private String telephoneAutre;

  private String lienDeRelation;
}
