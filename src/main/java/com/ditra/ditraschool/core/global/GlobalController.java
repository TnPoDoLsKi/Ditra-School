package com.ditra.ditraschool.core.global;

import com.ditra.ditraschool.core.article.ArticleServices;
import com.ditra.ditraschool.core.article.models.Article;
import com.ditra.ditraschool.core.global.models.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class GlobalController {

  @Autowired
  GlobalServices globalServices;


  @GetMapping("/global")
  public ResponseEntity<?> getAll() { return globalServices.getAll();  }

  @GetMapping("/global/{id}")
  public ResponseEntity<?> getOne(@PathVariable Long id) { return globalServices.getOne(id); }

  @PostMapping("/global")
  public ResponseEntity<?> create(@RequestBody Global global) { return globalServices.create(global); }

  @PutMapping("/global/{id}")
  public ResponseEntity<?> update(@PathVariable Long id , @RequestBody Global global) { return globalServices.update(id , global); }

  @DeleteMapping("/global/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) { return globalServices.delete(id); }


}
