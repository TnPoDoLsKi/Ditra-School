package com.ditra.ditraschool.core.eleve;

import com.ditra.ditraschool.core.eleve.Models.Eleve;
import com.ditra.ditraschool.core.eleve.Models.EleveList;
import com.ditra.ditraschool.utils.ErrorResponseModel;
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

  public ResponseEntity<?> getOne (Long id) {

    Optional<Eleve> eleveOptional = eleveRepository.findById(id);

    if(!eleveOptional.isPresent())
      return Utils.badRequestResponse(600, "");

    return new ResponseEntity<>(HttpStatus.OK);
  }

  public ResponseEntity<?> create (Eleve eleve) {



    eleve = eleveRepository.save(eleve);

    return new ResponseEntity<>(eleve , HttpStatus.OK );
  }

  public ResponseEntity<?> update(Long id, Eleve eleve) {

    Optional<Eleve> eleveOptional = eleveRepository.findById(id);

    if(!eleveOptional.isPresent())
      return Utils.badRequestResponse(600, "");


    eleve = Utils.merge(eleveOptional.get(),eleve);

    eleveRepository.save(eleve);

    return new ResponseEntity<>(HttpStatus.OK);
  }

  public ResponseEntity<?> delete (Long id) {

    Optional<Eleve> eleveOptional = eleveRepository.findById(id);

    if(!eleveOptional.isPresent())
      return Utils.badRequestResponse(600, "");


    eleveRepository.delete(eleveOptional.get());

    return new ResponseEntity<>(HttpStatus.OK);
  }
}
