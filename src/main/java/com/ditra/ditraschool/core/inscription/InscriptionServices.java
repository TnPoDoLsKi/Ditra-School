package com.ditra.ditraschool.core.inscription;

import com.ditra.ditraschool.core.classe.ClasseRepository;
import com.ditra.ditraschool.core.classe.models.Classe;
import com.ditra.ditraschool.core.eleve.EleveRepository;
import com.ditra.ditraschool.core.eleve.models.Eleve;
import com.ditra.ditraschool.core.inscription.models.Inscription;
import com.ditra.ditraschool.core.inscription.models.InscriptionList;
import com.ditra.ditraschool.core.inscription.models.InscriptionSave;
import com.ditra.ditraschool.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class InscriptionServices {

  @Autowired
  InscriptionRepository inscriptionRepository;

  @Autowired
  EleveRepository eleveRepository;

  @Autowired
  ClasseRepository classeRepository;


  public ResponseEntity<?> getAll() {
    ArrayList<Inscription> inscriptionArrayList = (ArrayList<Inscription>) inscriptionRepository.findAll();
    ArrayList<InscriptionList> inscriptionLists = new ArrayList<>();

    for (Inscription inscription : inscriptionArrayList)
      inscriptionLists.add(new InscriptionList(inscription));

    return new ResponseEntity<>(inscriptionLists, HttpStatus.OK);
  }

  public ResponseEntity<?> getOne(Long id) {


    Optional<Inscription> inscription = inscriptionRepository.findById(id);

    if(!inscription.isPresent())
      return Utils.badRequestResponse(600, "identifiant introuvable");

    InscriptionList inscriptionList = new InscriptionList(inscription.get());
    return new ResponseEntity<>(inscriptionList ,HttpStatus.OK);
  }

  public ResponseEntity<?> create(InscriptionSave inscriptionSave) {

    Inscription inscription = new Inscription();

    if(inscriptionSave.getCode() == null)
      return Utils.badRequestResponse(606, "code requis");

    Optional<Inscription> inscriptionOptional = inscriptionRepository.findInscriptionByCode(inscriptionSave.getCode());

    if(inscriptionOptional.isPresent() )
        return Utils.badRequestResponse(611, "code deja utilise");

    if(inscriptionSave.getClasseId() == null)
      return Utils.badRequestResponse(607, "classeId requis");

    if(inscriptionSave.getEleveId() == null)
      return Utils.badRequestResponse(609, "eleveId requis");



    Optional<Eleve> eleveOptional = eleveRepository.findById(inscriptionSave.getEleveId());

    if(!eleveOptional.isPresent())
      return Utils.badRequestResponse(610, "eleve introuvable");

    Optional<Classe> classe = classeRepository.findById(inscriptionSave.getClasseId());

    if(!classe.isPresent())
      return Utils.badRequestResponse(608, "classe introuvable");

    inscription.setCode(inscriptionSave.getCode());
    inscription.setClasse(classe.get());
    inscription.setEleve(eleveOptional.get());
    inscription.setReglement("NR");
    inscription.setMontantRestant(0.0);
    inscription.setMontantTotal(0.0);

    inscription = inscriptionRepository.save(inscription);

    InscriptionList inscriptionList = new InscriptionList(inscription);

    return new ResponseEntity<>(inscriptionList ,HttpStatus.OK);

  }

  public ResponseEntity<?> delete(Long id) {

    Optional<Inscription> inscription = inscriptionRepository.findById(id);

    if(!inscription.isPresent())
      return Utils.badRequestResponse(600, "identifiant introuvable");

    inscriptionRepository.delete(inscription.get());

    return new ResponseEntity<>(HttpStatus.OK);

  }


  public ResponseEntity<?> update(Long id, InscriptionSave inscriptionSave) {

    Optional<Inscription> inscriptionLocal = inscriptionRepository.findById(id);

    if(!inscriptionLocal.isPresent())
      return Utils.badRequestResponse(600, "identifiant introuvable");

    if (inscriptionSave.getCode() != null) {
      Optional<Inscription> inscriptionOptional = inscriptionRepository.findInscriptionByCode(inscriptionSave.getCode());

      if (inscriptionOptional.isPresent() && !inscriptionLocal.get().getCode().equals(inscriptionSave.getCode()))
        return Utils.badRequestResponse(611, "code deja utilise");
    }

    Inscription inscription = new Inscription();

    Optional<Eleve> eleveOptional = eleveRepository.findById(inscriptionSave.getEleveId());

    if(!eleveOptional.isPresent())
      inscription.setEleve(null);

    Optional<Classe> classe = classeRepository.findById(inscriptionSave.getClasseId());

    if(!classe.isPresent())
      inscription.setClasse(null);


    inscription.setCode(inscriptionSave.getCode());


    inscription = Utils.merge(inscriptionLocal.get() , inscription);

    inscription = inscriptionRepository.save(inscription);

    InscriptionList inscriptionList = new InscriptionList(inscription);
    return new ResponseEntity<>(inscriptionList ,HttpStatus.OK);
  }
}
