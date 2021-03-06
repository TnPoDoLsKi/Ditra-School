package com.ditra.ditraschool.core.inscription.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class InscriptionList {

  Long id;
  Long code;
  Long matriculeEleve;
  String nomEleve;
  String anneeScolaire;
  String nomClasse;
  Date date;
  String reglement;
  Double montantRestant;

  public  InscriptionList(Inscription inscription){
    id = inscription.getId();
    matriculeEleve = inscription.getEleve().getMatricule();
    code = inscription.getCode();
    date = inscription.getCreatedDate();
    nomEleve = inscription.getEleve().getPrenom()+" "+inscription.getEleve().getNom();
    anneeScolaire = inscription.getClasse().getAnneeScolaire();
    nomClasse = inscription.getClasse().getClasse();
    reglement = inscription.getReglement();
    montantRestant = Double.valueOf(new DecimalFormat("#.###").format(inscription.getMontantRestant()));
  }
}
