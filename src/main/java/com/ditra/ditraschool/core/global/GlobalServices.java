package com.ditra.ditraschool.core.global;

import com.ditra.ditraschool.core.global.models.Global;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GlobalServices {
  public ResponseEntity<?> getAll() {
    return new ResponseEntity<>(HttpStatus.OK);

  }

  public ResponseEntity<?> getOne(Long id) {
    return new ResponseEntity<>(HttpStatus.OK);

  }

  public ResponseEntity<?> create(Global global) {
    return new ResponseEntity<>(HttpStatus.OK);


  }

  public ResponseEntity<?> update(Long id, Global global) {
    return new ResponseEntity<>(HttpStatus.OK);

  }

  public ResponseEntity<?> delete(Long id) {
    return new ResponseEntity<>(HttpStatus.OK);

  }
}
