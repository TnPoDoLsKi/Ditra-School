package com.ditra.ditraschool.core.inscription.models;


import com.ditra.ditraschool.core.classe.models.Classe;
import com.ditra.ditraschool.core.eleve.Models.Eleve;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Inscription {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private Date date;

  private String reglement;

  @ManyToOne
  private Classe classe;

  @ManyToOne
  private Eleve eleve;



}
