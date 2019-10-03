package com.ditra.ditraschool.core.classe.models;

import com.ditra.ditraschool.core.inscription.models.Inscription;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;


@NoArgsConstructor
@Setter
@Getter
@Entity
public class Classe {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String classe;
  private String anneeScolaire;
  private float frais;
  private String observation;

  @OneToMany(mappedBy = "classe" , cascade = CascadeType.ALL)
  private ArrayList<Inscription> inscriptions = new ArrayList<>();
}
