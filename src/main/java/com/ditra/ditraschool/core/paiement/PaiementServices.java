package com.ditra.ditraschool.core.paiement;

import com.ditra.ditraschool.core.inscription.InscriptionRepository;
import com.ditra.ditraschool.core.inscription.models.Inscription;
import com.ditra.ditraschool.core.paiement.models.Paiement;
import com.ditra.ditraschool.core.paiement.models.PaiementModel;
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
    Optional<Paiement> paiement = paiementRepository.findById(id);

    if (!paiement.isPresent())
      return Utils.badRequestResponse(604,"");

    return new ResponseEntity<>(paiement , HttpStatus.OK);

  }

  public ResponseEntity<?> create(PaiementModel paiementModel) {

    Paiement paiement = new Paiement();

    paiement.setCode(paiementModel.getCode());

    paiement.setEcheance(paiementModel.getEcheance());

    paiement.setMode(paiementModel.getMode());

    Optional<Inscription> inscription = inscriptionRepository.findById(paiementModel.getInscriptionId());

    if(!inscription.isPresent())
      return Utils.badRequestResponse(602, "");


    paiement.setInscription(inscription.get());

    Double montantRestant =  inscription.get().getMontantTotal() - paiement.getMontant();

    inscription.get().setMontantRestant(montantRestant);

    inscriptionRepository.save(inscription.get());
    paiementRepository.save(paiement);

    return new ResponseEntity<>(paiement , HttpStatus.OK);
  }

  public ResponseEntity<?> update(Long id, PaiementModel paiementModel) {

    Optional<Paiement> paiementLocal = paiementRepository.findById(id);

    if (!paiementLocal.isPresent())
      return Utils.badRequestResponse(604,"");

    Paiement paiement = new Paiement();

    paiement.setCode(paiementModel.getCode());

    paiement.setEcheance(paiementModel.getEcheance());

    paiement.setMode(paiementModel.getMode());

    Optional<Inscription> inscription = inscriptionRepository.findById(paiementModel.getInscriptionId());

    if(!inscription.isPresent())
      return Utils.badRequestResponse(602, "");


    paiement.setInscription(inscription.get());

    paiement = Utils.merge(paiementLocal.get() , paiement);

    return new ResponseEntity<>(paiement , HttpStatus.OK);

  }

  public ResponseEntity<?> delete(Long id) {

    Optional<Paiement> paiement = paiementRepository.findById(id);

    if (!paiement.isPresent())
      return Utils.badRequestResponse(604,"");

    paiementRepository.delete(paiement.get());

    return new ResponseEntity<>(HttpStatus.OK);
  }
}
