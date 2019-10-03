package com.ditra.ditraschool.core.classe;

import com.ditra.ditraschool.core.classe.models.Classe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClasseController {

  @Autowired
  ClasseServcies classeServcies;


  @GetMapping("/classes")
  public ResponseEntity<?> getAll() { return classeServcies.getAll();  }

  @GetMapping("/classes/{id}")
  public ResponseEntity<?> getOne(@PathVariable Long id) { return classeServcies.getOne(id); }

  @PostMapping("/classe")
  public ResponseEntity<?> create(@RequestBody Classe classe) { return classeServcies.create(classe); }

  @PutMapping("/classe/{id}")
  public ResponseEntity<?> update(@PathVariable Long id , @RequestBody Classe classe) { return classeServcies.update(id , classe); }

  @DeleteMapping("/classe/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) { return classeServcies.delete(id); }
}
