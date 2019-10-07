package com.ditra.ditraschool.core.paiement;


import com.ditra.ditraschool.core.paiement.models.PaiementModel;
import com.ditra.ditraschool.core.paiement.models.PaiementUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class PaiementController {


  @Autowired
  PaiementServices paiementServices;


  @GetMapping("/paiements")
  public ResponseEntity<?> getAll() { return paiementServices.getAll();  }

  @GetMapping("/paiement/{id}")
  public ResponseEntity<?> getOne(@PathVariable Long id) { return paiementServices.getOne(id); }

  @PostMapping("/paiement")
  public ResponseEntity<?> create(@RequestBody PaiementModel paiementModel) { return paiementServices.create(paiementModel); }

  @PutMapping("/paiement/{id}")
  public ResponseEntity<?> update(@PathVariable Long id , @RequestBody PaiementUpdate paiementUpdate) { return paiementServices.update(id , paiementUpdate); }

  @DeleteMapping("/paiement/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) { return paiementServices.delete(id); }


}
