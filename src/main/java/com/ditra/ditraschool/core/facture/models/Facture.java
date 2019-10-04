package com.ditra.ditraschool.core.facture.models;

import com.ditra.ditraschool.core.article.models.Article;
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
import java.util.Date;
import java.util.Optional;

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

  private String code;

  private Double tva;

  private Double timbreFiscale;

  private Double totalTTC;

  private boolean deleted = false;

  @ManyToOne
  @JsonIgnore
  private Inscription inscription;

  @ManyToMany
  @JoinTable(name = "facture_article",
      joinColumns = { @JoinColumn(name = "factureId") },
      inverseJoinColumns = { @JoinColumn(name = "articleId") })
  private Collection<Article> articles = new ArrayList<>();

  public void addArticle(Article article) {

    articles.add(article);
  }
}
