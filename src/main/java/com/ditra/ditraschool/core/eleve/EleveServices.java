package com.ditra.ditraschool.core.eleve;

import com.ditra.ditraschool.core.eleve.models.Eleve;
import com.ditra.ditraschool.core.eleve.models.EleveList;
import com.ditra.ditraschool.core.eleve.models.EleveNotInscripted;
import com.ditra.ditraschool.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class EleveServices {

  @Autowired
  EleveRepository eleveRepository;

  public ResponseEntity<?> getAll () {
    ArrayList<Eleve> eleveArrayList = (ArrayList<Eleve>) eleveRepository.findAll();
    ArrayList<EleveList> eleves = new ArrayList<>();

    for (Eleve eleve : eleveArrayList)
      eleves.add(new EleveList(eleve));

    return new ResponseEntity<>(eleves, HttpStatus.OK);
  }

  public ResponseEntity<?> getOne (Long id)   {

    if(id == null)
      return Utils.badRequestResponse(650, "identifiant requis");

    Optional<Eleve> eleveOptional = eleveRepository.findById(id);

    if(!eleveOptional.isPresent())
      return Utils.badRequestResponse(600, "identifiant introuvable");

    return new ResponseEntity<>(eleveOptional.get() ,HttpStatus.OK);
  }

  public ResponseEntity<?> create (Eleve eleve)     {

    if(eleve.getMatricule() == null)
      return Utils.badRequestResponse(601, "matricule requis");

    Optional<Eleve> eleveOptional = eleveRepository.findEleveByMatricule(eleve.getMatricule());

    if(eleveOptional.isPresent())
      return Utils.badRequestResponse(620, "Matricule deja utilise");

    if(eleve.getNom() == null)
      return Utils.badRequestResponse(602, "nom requis");

    if(eleve.getPrenom() == null)
      return Utils.badRequestResponse(603, "prenom requis");


    if(eleve.getTuteur() == null)
      return Utils.badRequestResponse(604, "tuteur requis");


    if (eleve.getTuteur() == "pere"){

      if(eleve.getNomPere() == null)
        return Utils.badRequestResponse(641, "Nom tuteur requis");

      if(eleve.getTelephonePere() == null)
        return Utils.badRequestResponse(642, "telephone tuteur requis");

      if(eleve.getNumCinPere() == null)
        return Utils.badRequestResponse(643, "Cin tuteur requis");
    }


    if (eleve.getTuteur() == "mere"){

      if(eleve.getNomMere() == null)
        return Utils.badRequestResponse(641, "Nom tuteur requis");

      if(eleve.getTelephoneMere() == null)
        return Utils.badRequestResponse(642, "telephone tuteur requis");

      if(eleve.getNumCinMere() == null)
        return Utils.badRequestResponse(643, "Cin tuteur requis");
    }


    if (eleve.getTuteur() == "autre"){

      if(eleve.getNomAutre() == null)
        return Utils.badRequestResponse(641, "Nom tuteur requis");

      if(eleve.getTelephoneAutre() == null)
        return Utils.badRequestResponse(642, "telephone tuteur requis");

      if(eleve.getNumCinAutre() == null)
        return Utils.badRequestResponse(643, "Cin tuteur requis");
    }


    eleve = eleveRepository.save(eleve);

    return new ResponseEntity<>(eleve ,HttpStatus.OK);
  }

  public ResponseEntity<?> update(Long id, Eleve eleve) {

    if(id == null)
      return Utils.badRequestResponse(650, "identifiant requis");

    Optional<Eleve> eleveOptional = eleveRepository.findById(id);

    if(!eleveOptional.isPresent())
      return Utils.badRequestResponse(600, "identifiant introuvable");

    Optional<Eleve> eleveByMatricule = eleveRepository.findEleveByMatricule(eleve.getMatricule());

    if(eleveByMatricule.isPresent() && !eleveOptional.get().getMatricule().equals( eleve.getMatricule()))
      return Utils.badRequestResponse(620, "Matricule deja utilise");

    eleve = Utils.merge(eleveOptional.get(), eleve);

    eleveRepository.save(eleve);

    return new ResponseEntity<>(HttpStatus.OK);
  }

  public ResponseEntity<?> delete (Long id) {

    if(id == null)
      return Utils.badRequestResponse(650, "identifiant requis");

    Optional<Eleve> eleveOptional = eleveRepository.findById(id);

    if(!eleveOptional.isPresent())
      return Utils.badRequestResponse(600, "identifiant introuvable");

    eleveRepository.delete(eleveOptional.get());

    return new ResponseEntity<>(HttpStatus.OK);
  }

  public ResponseEntity<?> getAllNotInscripted() {

    ArrayList<Eleve> eleveArrayList = (ArrayList<Eleve>) eleveRepository.findAllByInscriptionsEmpty();
    ArrayList<EleveNotInscripted> eleves = new ArrayList<>();

    for (Eleve eleve : eleveArrayList)
      eleves.add(new EleveNotInscripted(eleve));

    return new ResponseEntity<>(eleves, HttpStatus.OK);
  }
}
