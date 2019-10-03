package com.ditra.ditraschool.core.inscription;

import com.ditra.ditraschool.core.inscription.models.InscriptionSave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class InscriptionController {

  @Autowired
  InscriptionServices inscriptionServices;

  @GetMapping("/inscriptions")
  public ResponseEntity<?> getAll() { return inscriptionServices.getAll();  }

  @GetMapping("/inscription/{id}")
  public ResponseEntity<?> getOne(@PathVariable Long id) { return inscriptionServices.getOne(id); }

  @PostMapping("/inscription")
  public ResponseEntity<?> create(@RequestBody InscriptionSave inscriptionSave) { return inscriptionServices.create(inscriptionSave); }

  @PutMapping("/inscription/{id}")
  public ResponseEntity<?> update(@PathVariable Long id , @RequestBody InscriptionSave inscriptionSave) { return inscriptionServices.update(id , inscriptionSave); }

  @DeleteMapping("/inscription/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) { return inscriptionServices.delete(id); }

}
