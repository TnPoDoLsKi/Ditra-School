package com.ditra.ditraschool.core.classe;


import com.ditra.ditraschool.core.classe.models.Classe;
import com.ditra.ditraschool.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClasseServcies {

  @Autowired
  ClasseRepository classeRepository;



  public ResponseEntity<?> getAll() {

    List<Classe> classes = classeRepository.findAll();

    return new ResponseEntity<>(classes, HttpStatus.OK);
  }

  public ResponseEntity<?> getOne(Long id) {

    Optional<Classe> classe = classeRepository.findById(id);

    if(!classe.isPresent())
      return Utils.badRequestResponse(601, "");

    return new ResponseEntity<>(classe,HttpStatus.OK);
  }

  public ResponseEntity<?> create(Classe classe) {

    classe = classeRepository.save(classe);

    return new ResponseEntity<>(classe,HttpStatus.OK);
  }

  public ResponseEntity<?> update(Long id, Classe classe) {

    Optional<Classe> classeLocal = classeRepository.findById(id);

    if(!classeLocal.isPresent())
      return Utils.badRequestResponse(601, "");

    classe = Utils.merge(classeLocal.get(),classe);

    classe = classeRepository.save(classe);
    return new ResponseEntity<>(classe ,HttpStatus.OK);
  }

  public ResponseEntity<?> delete(Long id) {

    Optional<Classe> classeLocal = classeRepository.findById(id);

    if(!classeLocal.isPresent())
      return Utils.badRequestResponse(601, "");

    classeRepository.delete(classeLocal.get());

    return new ResponseEntity<>(HttpStatus.OK);
  }

}
