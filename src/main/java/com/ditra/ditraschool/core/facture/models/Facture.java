package com.ditra.ditraschool.core.facture.models;

import com.ditra.ditraschool.core.articleFacture.models.ArticleFacture;
import com.ditra.ditraschool.core.inscription.models.Inscription;
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

@Getter
@Setter
@NoArgsConstructor
@Entity
@Where(clause = "deleted = false")
@SQLDelete(sql=" UPDATE facture SET deleted = true WHERE id = ?")
public class Facture extends Auditable<String>  {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long code;

  private Double tva;

  private Boolean avecTimbre = false;

  private Double totalTTC;

  private Double timbreFiscale = 0.0;

  private String totalTTcEnMot;

  private String tuteur;

  private boolean deleted = false;

  @ManyToOne
  @JsonIgnore
  private Inscription inscription;

  @OneToMany (mappedBy = "facture" , cascade = CascadeType.ALL , orphanRemoval = true)
  private Collection<ArticleFacture> articleFactures  = new ArrayList<>();
}
