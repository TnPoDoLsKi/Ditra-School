package com.ditra.ditraschool.core.articleFacture.models;

import com.ditra.ditraschool.core.facture.models.Facture;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ArticleFacture {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Double montantHT;

  private String designation;


  @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, mappedBy = "articles")
  @JsonIgnore
  private Collection<Facture> factures = new ArrayList<>();
}