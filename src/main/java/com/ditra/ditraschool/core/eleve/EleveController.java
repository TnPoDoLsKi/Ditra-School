package com.ditra.ditraschool.core.eleve;

import com.ditra.ditraschool.core.eleve.Models.Eleve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class EleveController {

  @Autowired
  EleveServices eleveServices;

  @GetMapping("/eleves")
  public ResponseEntity<?> getAll() { return eleveServices.getAll();  }

  @GetMapping("/eleves/notInscripted")
  public ResponseEntity<?> getAllNotInscripted() { return eleveServices.getAllNotInscripted();  }

  @GetMapping("/eleve/{id}")
  public ResponseEntity<?> getOne(@PathVariable Long id) { return eleveServices.getOne(id); }

  @PostMapping("/eleve")
  public ResponseEntity<?> create(@RequestBody Eleve eleve) { return eleveServices.create(eleve); }

  @PutMapping("/eleve/{id}")
  public ResponseEntity<?> update(@PathVariable Long id , @RequestBody Eleve eleve) { return eleveServices.update(id , eleve); }

  @DeleteMapping("/eleve/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) { return eleveServices.delete(id); }
}
