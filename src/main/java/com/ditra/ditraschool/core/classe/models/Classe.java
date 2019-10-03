package com.ditra.ditraschool.core.classe.models;

import com.ditra.ditraschool.core.inscription.models.Inscription;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;


@NoArgsConstructor
@Setter
@Getter
@Entity
@Where(clause = "deleted = false")
@SQLDelete(sql=" UPDATE classe SET deleted = true WHERE id = ?")
public class Classe {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String classe;
  private String anneeScolaire;
  private float frais;
  private String observation;

  private boolean deleted = false;

  @OneToMany(mappedBy = "classe" , cascade = CascadeType.ALL)
  private Collection<Inscription> inscriptions = new ArrayList<>();
}
