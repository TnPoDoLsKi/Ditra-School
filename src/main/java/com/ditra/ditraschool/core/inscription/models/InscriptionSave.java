package com.ditra.ditraschool.core.inscription.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class InscriptionSave {

  private String reglement;
  private long classeId;
  private long eleveId;
}
