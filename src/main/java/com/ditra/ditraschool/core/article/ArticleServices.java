package com.ditra.ditraschool.core.article;

import com.ditra.ditraschool.core.article.models.Article;
import com.ditra.ditraschool.core.facture.models.Facture;
import com.ditra.ditraschool.core.paiement.models.Paiement;
import com.ditra.ditraschool.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleServices {

  @Autowired
  ArticleRepository articleRepository;


  public ResponseEntity<?> getAll() {
    List<Article> articles = articleRepository.findAll();
    return new ResponseEntity<>(articles, HttpStatus.OK);
  }

  public ResponseEntity<?> getOne(Long id) {

    if(id == null)
      return Utils.badRequestResponse(650, "identifiant requis");


    Optional<Article> article = articleRepository.findById(id);

    if (!article.isPresent())
      return Utils.badRequestResponse(600, "identifiant introuvable");

    return new ResponseEntity<>(article , HttpStatus.OK);

  }

  public ResponseEntity<?> create(Article article) {

    if(article.getCode() == null)
      return Utils.badRequestResponse(606, "code requis");

    if(article.getDesignation() == null)
      return Utils.badRequestResponse(617, "designation requis");

    if(article.getMontantHT() == null)
      return Utils.badRequestResponse(618, "monatant requis");


    Optional<Article> article1 = articleRepository.findArticleByCode(article.getCode());

    if (article1.isPresent())
      return Utils.badRequestResponse(611, "code deja utilise");


    article = articleRepository.save(article);

    return new ResponseEntity<>(article,HttpStatus.OK);
  }

  public ResponseEntity<?> update(Long id, Article article) {

    Optional<Article> articleLocal = articleRepository.findById(id);

    if (!articleLocal.isPresent())
      return Utils.badRequestResponse(600, "identifiant introuvable");


    if (article.getCode() != null) {
      Optional<Article> article1 = articleRepository.findArticleByCode(article.getCode());

      if (article1.isPresent() && !articleLocal.get().getCode().equals(article.getCode()))
        return Utils.badRequestResponse(611, "code deja utilise");
    }


    article = Utils.merge(articleLocal.get() , article);

    return new ResponseEntity<>(article,HttpStatus.OK);
  }

  public ResponseEntity<?> delete(Long id) {

    if (id.equals(Long.valueOf(1)))
      return Utils.badRequestResponse(606,"");

    Optional<Article> articleLocal = articleRepository.findById(id);

    if (!articleLocal.isPresent())
      return Utils.badRequestResponse(605,"");

    articleRepository.delete(articleLocal.get());

    return new ResponseEntity<>(HttpStatus.OK);
  }
}
