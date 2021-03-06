package com.ditra.ditraschool.core.facture;

import com.ditra.ditraschool.core.classe.models.Classe;
import com.ditra.ditraschool.core.facture.models.FactureUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class FactureController {

  @Autowired
  FactureServices factureServices;


  @GetMapping("/factures")
  public ResponseEntity<?> getAll() { return factureServices.getAll();  }

  @GetMapping("/facture/code")
  public ResponseEntity<?> getCode() { return factureServices.getCode();  }

  @GetMapping("/facture/{id}")
  public ResponseEntity<?> getOne(@PathVariable Long id) { return factureServices.getOne(id); }

  @GetMapping("/factures/byInscriptionId/{id}")
  public ResponseEntity<?> getByInscriptionId(@PathVariable Long id) { return factureServices.getByInscriptionId(id); }

  @GetMapping("/facture/getArticles/{id}")
  public ResponseEntity<?> getArticles(@PathVariable Long id){
    return factureServices.getArticles(id);
  }

  @PostMapping("/facture")
  public ResponseEntity<?> create(@RequestBody FactureUpdate factureUpdate) { return factureServices.create(factureUpdate); }

  @PutMapping("/facture/{id}")
  public ResponseEntity<?> update(@PathVariable Long id , @RequestBody FactureUpdate factureUpdate) { return factureServices.update(id , factureUpdate); }

  @DeleteMapping("/facture/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) { return factureServices.delete(id); }
}
