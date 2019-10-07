package com.ditra.ditraschool.core.eleve.models;

import com.ditra.ditraschool.core.inscription.models.Inscription;
import com.ditra.ditraschool.utils.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Where(clause = "deleted = false")
@SQLDelete(sql=" UPDATE eleve SET deleted = true WHERE id = ?")
public class Eleve extends Auditable<String>  {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long matricule;
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
  private boolean deleted = false;

  @OneToMany (mappedBy = "eleve" , cascade = CascadeType.ALL , orphanRemoval = true)
  @JsonIgnore
  private Collection<Inscription> inscriptions = new ArrayList<>();
}
