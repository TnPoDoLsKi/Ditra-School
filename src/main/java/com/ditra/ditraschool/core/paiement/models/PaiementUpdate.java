package com.ditra.ditraschool.core.paiement.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@NoArgsConstructor
@Setter
@Getter
public class PaiementUpdate {

  private Long code;
  private String mode;
  private Double montant;
  private Date echeance;

}
