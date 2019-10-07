package com.ditra.ditraschool.core.global;

import com.ditra.ditraschool.core.global.models.Global;
import com.ditra.ditraschool.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GlobalServices {

  @Autowired
  GlobalRepository globalRepository;

  public ResponseEntity<?> getOne(Long id) {
    return new ResponseEntity<>(globalRepository.findAll().get(0),HttpStatus.OK);
  }


  public ResponseEntity<?> update(Global global) {

    Global global1 = globalRepository.findAll().get(0);

    global = Utils.merge(global1,global);

    globalRepository.save(global);

    return new ResponseEntity<>( global , HttpStatus.OK);

  }


}
