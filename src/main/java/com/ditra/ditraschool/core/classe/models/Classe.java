package com.ditra.ditraschool.core.classe.models;

import com.ditra.ditraschool.core.inscription.models.Inscription;
import com.ditra.ditraschool.utils.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.w3c.dom.Text;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;


@NoArgsConstructor
@Setter
@Getter
@Entity
@Where(clause = "deleted = false")
@SQLDelete(sql=" UPDATE classe SET deleted = true WHERE id = ?")
public class Classe extends Auditable<String>  {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String classe;
  private String anneeScolaire;
  private Float frais = (float) 0;

  @Column(columnDefinition = "text")
  private String observation;

  private boolean deleted = false;

  @OneToMany(mappedBy = "classe" , cascade = CascadeType.ALL , orphanRemoval = true)
  @JsonIgnore
  private Collection<Inscription> inscriptions = new ArrayList<>();
}
