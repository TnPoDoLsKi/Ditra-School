package com.ditra.ditraschool.core.inscription;

import com.ditra.ditraschool.core.classe.ClasseRepository;
import com.ditra.ditraschool.core.classe.ClasseServcies;
import com.ditra.ditraschool.core.classe.models.Classe;
import com.ditra.ditraschool.core.eleve.EleveRepository;
import com.ditra.ditraschool.core.eleve.EleveServices;
import com.ditra.ditraschool.core.eleve.Models.Eleve;
import com.ditra.ditraschool.core.eleve.Models.EleveList;
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
      return Utils.badRequestResponse(602, "");

    InscriptionList inscriptionList = new InscriptionList(inscription.get());
    return new ResponseEntity<>(inscriptionList ,HttpStatus.OK);
  }

  public ResponseEntity<?> create(InscriptionSave inscriptionSave) {

    Inscription inscription = new Inscription();

    Optional<Eleve> eleveOptional = eleveRepository.findById(inscriptionSave.getEleveId());

    if(!eleveOptional.isPresent())
      return Utils.badRequestResponse(600, "");

    Optional<Classe> classe = classeRepository.findById(inscriptionSave.getClasseId());

    if(!classe.isPresent())
      return Utils.badRequestResponse(601, "");

    inscription.setClasse(classe.get());
    inscription.setEleve(eleveOptional.get());
    inscription.setReglement(inscriptionSave.getReglement());

    inscription = inscriptionRepository.save(inscription);

    InscriptionList inscriptionList = new InscriptionList(inscription);
    return new ResponseEntity<>(inscriptionList ,HttpStatus.OK);

  }

  public ResponseEntity<?> delete(Long id) {

    Optional<Inscription> inscription = inscriptionRepository.findById(id);

    if(!inscription.isPresent())
      return Utils.badRequestResponse(602, "");

    inscriptionRepository.delete(inscription.get());

    return new ResponseEntity<>(HttpStatus.OK);

  }


  public ResponseEntity<?> update(Long id, InscriptionSave inscriptionSave) {

    Optional<Inscription> inscriptionLocal = inscriptionRepository.findById(id);

    if(!inscriptionLocal.isPresent())
      return Utils.badRequestResponse(602, "");


    Inscription inscription = new Inscription();

    Optional<Eleve> eleveOptional = eleveRepository.findById(inscriptionSave.getEleveId());

    if(!eleveOptional.isPresent())
      return Utils.badRequestResponse(600, "");

    Optional<Classe> classe = classeRepository.findById(inscriptionSave.getClasseId());

    if(!classe.isPresent())
      return Utils.badRequestResponse(601, "");

    inscription.setClasse(classe.get());
    inscription.setEleve(eleveOptional.get());
    inscription.setReglement(inscriptionSave.getReglement());

    inscription = Utils.merge(inscriptionLocal.get() , inscription);

    inscription = inscriptionRepository.save(inscription);

    InscriptionList inscriptionList = new InscriptionList(inscription);
    return new ResponseEntity<>(inscriptionList ,HttpStatus.OK);
  }
}
