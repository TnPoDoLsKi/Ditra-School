package com.ditra.ditraschool.core.article;

import com.ditra.ditraschool.core.article.models.Article;
import com.ditra.ditraschool.core.paiement.models.PaiementModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ArticleController {

  @Autowired
  ArticleServices articleServices;


  @GetMapping("/articles")
  public ResponseEntity<?> getAll() { return articleServices.getAll();  }

  @GetMapping("/article/{id}")
  public ResponseEntity<?> getOne(@PathVariable Long id) { return articleServices.getOne(id); }

  @PostMapping("/article")
  public ResponseEntity<?> create(@RequestBody Article article) { return articleServices.create(article); }

  @PutMapping("/article/{id}")
  public ResponseEntity<?> update(@PathVariable Long id , @RequestBody Article article) { return articleServices.update(id , article); }

  @DeleteMapping("/article/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) { return articleServices.delete(id); }


}
