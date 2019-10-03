package com.ditra.ditraschool.core.facture.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FactureUpdate {

  private String code;

  private Double tva;

  private Double timbreFiscale;

  private Double totalTTC;

  private Long inscriptionId;

  private List<Long> articles;

}
