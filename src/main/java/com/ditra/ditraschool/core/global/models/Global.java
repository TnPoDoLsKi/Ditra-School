package com.ditra.ditraschool.core.global.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Global {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String gerant;
  private String anneeScolaire;
  private String raisonSociale;
  private String adresse;
  private String ville;
  private String telephone;
  private String matriculeFiscale;
  private Double tva;
  private Double timbreFiscale;
  private boolean deleted = false;

}
