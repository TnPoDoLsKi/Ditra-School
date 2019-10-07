package com.ditra.ditraschool.core.facture.models;

import com.ditra.ditraschool.core.article.models.Article;
import com.ditra.ditraschool.core.articleFacture.models.ArticleFacture;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FactureUpdate {

  private Long code;

  private Boolean avecTimbre;

  private Long inscriptionId;

  private List<ArticleFacture> articles;

}
