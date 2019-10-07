package com.ditra.ditraschool.core.inscription.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class InscriptionList {

  Long id;
  Long code;
  String matriculeEleve;
  String nomEleve;
  String anneeScolaire;
  String nomClasse;
  Date date;
  String reglement;

  public  InscriptionList(Inscription inscription){
    id = inscription.getId();
    code = inscription.getCode();
    date = inscription.getCreatedDate();
    nomEleve = inscription.getEleve().getNom();
    anneeScolaire = inscription.getClasse().getAnneeScolaire();
    nomClasse = inscription.getClasse().getClasse();
    reglement = inscription.getReglement();
  }

}
