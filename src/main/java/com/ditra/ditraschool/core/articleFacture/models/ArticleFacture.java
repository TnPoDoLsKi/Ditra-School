package com.ditra.ditraschool.core.articleFacture.models;

import com.ditra.ditraschool.core.facture.models.Facture;
import com.ditra.ditraschool.core.inscription.models.Inscription;
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
public class ArticleFacture extends Auditable<String> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long code;
  private Double montantHT;
  private String designation;

  @ManyToOne
  @JsonIgnore
  private Facture facture;
}
