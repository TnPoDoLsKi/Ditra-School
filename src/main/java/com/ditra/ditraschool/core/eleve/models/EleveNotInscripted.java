package com.ditra.ditraschool.core.eleve.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EleveNotInscripted {

  private Long matricule;
  private String nom;


 public EleveNotInscripted (Eleve eleve){
   matricule = eleve.getMatricule();
   nom = eleve.getPrenom() + " " + eleve.getNom()  ;
 }
}
