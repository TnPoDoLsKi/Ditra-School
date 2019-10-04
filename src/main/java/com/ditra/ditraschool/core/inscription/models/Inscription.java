package com.ditra.ditraschool.core.inscription.models;


import com.ditra.ditraschool.core.classe.models.Classe;
import com.ditra.ditraschool.core.eleve.models.Eleve;
import com.ditra.ditraschool.core.facture.models.Facture;
import com.ditra.ditraschool.core.paiement.models.Paiement;
import com.ditra.ditraschool.utils.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Where(clause = "deleted = false")
@SQLDelete(sql=" UPDATE inscription SET deleted = true WHERE id = ?")
public class Inscription extends Auditable<String>  {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String matricule ;

  private Boolean deleted = false;

  private String reglement;

  private Double montantTotal;

  private Double montantRestant;

  @ManyToOne
  private Classe classe;

  @ManyToOne
  private Eleve eleve;

  @OneToMany (mappedBy = "inscription" , cascade = CascadeType.ALL)
  private Collection<Facture> factures = new ArrayList<>();

  @OneToMany (mappedBy = "inscription" , cascade = CascadeType.ALL)
  private Collection<Paiement> paiements = new ArrayList<>();
}
