package com.ditra.ditraschool.core.paiement;


import com.ditra.ditraschool.core.paiement.models.PaiementModel;
import com.ditra.ditraschool.core.paiement.models.PaiementUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class PaiementController {


  @Autowired
  PaiementServices paiementServices;

  @GetMapping("/paiements")
  public ResponseEntity<?> getAll() { return paiementServices.getAll();  }

  @GetMapping("/paiement/code")
  public ResponseEntity<?> getLastCode() { return paiementServices.getLastCode();  }

  @GetMapping("paiement/printInfo")
  public ResponseEntity<?> getPrintInfo() { return paiementServices.getPrintInfo();  }


  @GetMapping("/paiement/byInscription/{id}")
  public ResponseEntity<?> getByInscription(@PathVariable Long id) { return paiementServices.getByInscription(id); }

  @PostMapping("/paiement")
  public ResponseEntity<?> create(@RequestBody PaiementModel paiementModel) { return paiementServices.create(paiementModel); }

  @PutMapping("/paiement/{id}")
  public ResponseEntity<?> update(@PathVariable Long id , @RequestBody PaiementUpdate paiementUpdate) { return paiementServices.update(id , paiementUpdate); }

  @DeleteMapping("/paiement/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) { return paiementServices.delete(id); }


}
