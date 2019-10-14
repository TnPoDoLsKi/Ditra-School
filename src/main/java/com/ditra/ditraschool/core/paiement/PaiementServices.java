package com.ditra.ditraschool.core.paiement;

import com.ditra.ditraschool.core.global.GlobalRepository;
import com.ditra.ditraschool.core.global.models.Global;
import com.ditra.ditraschool.core.inscription.InscriptionRepository;
import com.ditra.ditraschool.core.inscription.models.Inscription;
import com.ditra.ditraschool.core.paiement.models.Paiement;
import com.ditra.ditraschool.core.paiement.models.PaiementModel;
import com.ditra.ditraschool.core.paiement.models.PaiementUpdate;
import com.ditra.ditraschool.core.paiement.models.PrintModel;
import com.ditra.ditraschool.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import pl.allegro.finance.tradukisto.MoneyConverters;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

@Service
public class PaiementServices {

  @Autowired
  PaiementRepository paiementRepository;

  @Autowired
  InscriptionRepository inscriptionRepository;
  @Autowired
  GlobalRepository globalRepository;


  public ResponseEntity<?> getAll(){
    return new ResponseEntity<>(paiementRepository.findAll(),HttpStatus.OK);
  }

  public ResponseEntity<?> getLastCode() {
    List<Paiement> paiements = paiementRepository.findAll();

    if (paiements.size() == 0){
      return new ResponseEntity<>(0, HttpStatus.OK);

    }

    return new ResponseEntity<>(paiements.get(paiements.size()-1).getCode()+1, HttpStatus.OK);
  }

  public ResponseEntity<?> getByInscription(Long id) {

    if(id == null)
      return Utils.badRequestResponse(650, "identifiant requis");

    Optional<Inscription> inscription  = inscriptionRepository.findById(id);

    if (!inscription.isPresent())
      return Utils.badRequestResponse(600, "identifiant introuvable");

    return new ResponseEntity<>(inscription.get().getPaiements() , HttpStatus.OK);

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

    switch (inscription.get().getEleve().getTuteur()) {
      case "pere":
        paiement.setTuteur(inscription.get().getEleve().getNomPere());
        break;
      case "mere":
        paiement.setTuteur(inscription.get().getEleve().getNomMere());
        break;
      case "autre":
        paiement.setTuteur(inscription.get().getEleve().getNomAutre());
        break;
    }

    paiement.setMontant(paiementModel.getMontant());

    String formatedTotalTTC = new DecimalFormat("#.###").format(paiementModel.getMontant());
    int fractionalValue = Integer.parseInt(formatedTotalTTC.split("\\.")[1]);

    while(fractionalValue <= 99)
      fractionalValue *= 10;

    MoneyConverters converter = MoneyConverters.FRENCH_BANKING_MONEY_VALUE;
    String montantEnLettre = converter.asWords(new BigDecimal(paiementModel.getMontant().intValue())).split("€")[0] + "dinars ";
    montantEnLettre += converter.asWords(new BigDecimal(fractionalValue)).split("€")[0] + "millimes";

    paiement.setMontantEnMot(montantEnLettre.toUpperCase());

    Double montantRestant =  inscription.get().getMontantRestant() - paiementModel.getMontant();
    inscription.get().setMontantRestant(montantRestant);

    if (montantRestant <= 0)
      inscription.get().setReglement("R");
    else
      inscription.get().setReglement("PR");

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

      Optional<Paiement> paiementByCode = paiementRepository.findPaiementByCode(paiementUpdate.getCode());

      if(paiementByCode.isPresent() && !paiementLocal.get().getCode().equals(paiementUpdate.getCode()))
        return Utils.badRequestResponse(611, "code deja utilise");
    }

    Paiement paiement = new Paiement();

      paiement.setMontant(paiementUpdate.getMontant());



    paiement.setCode(paiementUpdate.getCode());

    paiement.setEcheance(paiementUpdate.getEcheance());

    paiement.setMode(paiementUpdate.getMode());

    if (paiementUpdate.getMontant() != null) {

      String formatedTotalTTC = new DecimalFormat("#.###").format(paiementUpdate.getMontant());
      int fractionalValue = Integer.parseInt(formatedTotalTTC.split("\\.")[1]);

      while(fractionalValue <= 99)
        fractionalValue *= 10;

      MoneyConverters converter = MoneyConverters.FRENCH_BANKING_MONEY_VALUE;
      String montantEnLettre = converter.asWords(new BigDecimal(paiementUpdate.getMontant().intValue())).split("€")[0] + "dinars ";
      montantEnLettre += converter.asWords(new BigDecimal(fractionalValue)).split("€")[0] + "millimes";

      paiement.setMontantEnMot(montantEnLettre.toUpperCase());

      Inscription inscription = paiementLocal.get().getInscription();
      inscription.setMontantRestant(inscription.getMontantRestant() + paiementLocal.get().getMontant() - paiementUpdate.getMontant());

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
      return Utils.badRequestResponse(600, "identifiant introuvable");

    Inscription inscription =  paiement.get().getInscription();
    Double montantRestant = inscription.getMontantRestant();
    inscription.setMontantRestant(montantRestant + paiement.get().getMontant());

    inscriptionRepository.save(inscription);
    paiementRepository.delete(paiement.get());

    return new ResponseEntity<>(HttpStatus.OK);
  }
}
