package com.ditra.ditraschool.core.article;

import com.ditra.ditraschool.core.article.models.Article;
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

    Optional<Article> article = articleRepository.findById(id);

    if (!article.isPresent())
      return Utils.badRequestResponse(605,"");

    return new ResponseEntity<>(article , HttpStatus.OK);

  }

  public ResponseEntity<?> create(Article article) {

    article = articleRepository.save(article);

    return new ResponseEntity<>(article,HttpStatus.OK);
  }

  public ResponseEntity<?> update(Long id, Article article) {

    Optional<Article> articleLocal = articleRepository.findById(id);

    if (!articleLocal.isPresent())
      return Utils.badRequestResponse(605,"");

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
