package com.ditra.ditraschool.core.eleve.models;



import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EleveList {
  private Long id;

  private String matricule;
  private String nom;
  private String sexe;
  private Date dateNaissance;
  private String nomTuteur;
  private String telephoneTuteur;

  public EleveList (Eleve eleve) {
    this.id = eleve.getId();
    this.matricule = eleve.getMatricule();
    this.nom = eleve.getNom()+" "+eleve.getPrenom();
    this.sexe = eleve.getSexe();
    this.dateNaissance = eleve.getDateNaissance();

    switch (eleve.getTuteur()) {
      case "pere":
        this.nomTuteur = eleve.getNomPere();
        this.telephoneTuteur = eleve.getTelephonePere();
        break;
      case "mere":
        this.nomTuteur = eleve.getNomMere();
        this.telephoneTuteur = eleve.getTelephoneMere();
        break;
      case "autre":
        this.nomTuteur = eleve.getNomAutre();
        this.telephoneTuteur = eleve.getTelephoneAutre();
        break;
    }

  }
}
