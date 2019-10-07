package com.ditra.ditraschool.core.paiement;

import com.ditra.ditraschool.core.inscription.InscriptionRepository;
import com.ditra.ditraschool.core.inscription.models.Inscription;
import com.ditra.ditraschool.core.paiement.models.Paiement;
import com.ditra.ditraschool.core.paiement.models.PaiementModel;
import com.ditra.ditraschool.core.paiement.models.PaiementUpdate;
import com.ditra.ditraschool.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaiementServices {

  @Autowired
  PaiementRepository paiementRepository;

  @Autowired
  InscriptionRepository inscriptionRepository;


  public ResponseEntity<?> getAll() {
    List<Paiement> paiements = paiementRepository.findAll();
    return new ResponseEntity<>(paiements, HttpStatus.OK);
  }

  public ResponseEntity<?> getOne(Long id) {

    if(id == null)
      return Utils.badRequestResponse(650, "identifiant requis");

    Optional<Paiement> paiement = paiementRepository.findById(id);

    if (!paiement.isPresent())
      return Utils.badRequestResponse(600, "identifiant introuvable");

    return new ResponseEntity<>(paiement , HttpStatus.OK);

  }

  public ResponseEntity<?> create(PaiementModel paiementModel) {

    if(paiementModel.getInscriptionId() == null)
      return Utils.badRequestResponse(612, "inscriptionId requis");

    if(paiementModel.getCode() == null)
      return Utils.badRequestResponse(606, "code requis");

    if(paiementModel.getMontant() == null)
      return Utils.badRequestResponse(619, "montant requis");


    if(paiementModel.getMode() == null)
      return Utils.badRequestResponse(618, "mode requis");


    Optional<Inscription> inscription = inscriptionRepository.findById(paiementModel.getInscriptionId());

    if(!inscription.isPresent())
      return Utils.badRequestResponse(600, "identifiant introuvable");


    Optional<Paiement> paiement1 = paiementRepository.findPaiementByCode(paiementModel.getCode());

    if(paiement1.isPresent())
      return Utils.badRequestResponse(611, "code deja utilise");


    Paiement paiement = new Paiement();

    paiement.setCode(paiementModel.getCode());

    paiement.setEcheance(paiementModel.getEcheance());

    paiement.setMode(paiementModel.getMode());

    paiement.setInscription(inscription.get());

    Double montantRestant =  inscription.get().getMontantTotal() - paiementModel.getMontant();

    paiement.setMontant(paiementModel.getMontant());

    if (montantRestant == 0)
      inscription.get().setReglement("R");
    else
      inscription.get().setReglement("PR");

    inscription.get().setMontantRestant(montantRestant);

    inscriptionRepository.save(inscription.get());
    paiementRepository.save(paiement);

    return new ResponseEntity<>( paiement, HttpStatus.OK);
  }

  public ResponseEntity<?> update(Long id, PaiementUpdate paiementUpdate) {

    if(id == null)
      return Utils.badRequestResponse(650, "identifiant requis");

    Optional<Paiement> paiementLocal = paiementRepository.findById(id);

    if (!paiementLocal.isPresent())
      return Utils.badRequestResponse(600, "identifiant introuvable");


    if (paiementUpdate.getCode() != null){

      Optional<Paiement> paiement1 = paiementRepository.findPaiementByCode(paiementUpdate.getCode());

      if(paiement1.isPresent() && !paiementLocal.get().getCode().equals(paiementUpdate.getCode()))
        return Utils.badRequestResponse(611, "code deja utilise");

    }


    Paiement paiement = new Paiement();

    paiement.setCode(paiementUpdate.getCode());

    paiement.setEcheance(paiementUpdate.getEcheance());

    paiement.setMode(paiementUpdate.getMode());

    paiement.setMontant(paiementUpdate.getMontant());

    if (paiementUpdate.getMontant() != null) {
      Inscription inscription = paiementLocal.get().getInscription();


      inscription.setMontantRestant(inscription.getMontantTotal() - paiementUpdate.getMontant());

      inscriptionRepository.save(inscription);
    }

    paiement = Utils.merge(paiementLocal.get() , paiement);

    paiementRepository.save(paiement);

    return new ResponseEntity<>(paiement , HttpStatus.OK);

  }

  public ResponseEntity<?> delete(Long id) {

    if(id == null)
      return Utils.badRequestResponse(650, "identifiant requis");

    Optional<Paiement> paiement = paiementRepository.findById(id);

    if (!paiement.isPresent())
      return Utils.badRequestResponse(604,"");

    paiementRepository.delete(paiement.get());

    return new ResponseEntity<>(HttpStatus.OK);
  }
}
