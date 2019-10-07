package com.ditra.ditraschool.core.global;

import com.ditra.ditraschool.core.global.models.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class GlobalController {

  @Autowired
  GlobalServices globalServices;

  @GetMapping("/global")
  public ResponseEntity<?> getOne() { return globalServices.getOne(); }

  @PutMapping("/global")
  public ResponseEntity<?> update( @RequestBody Global global) { return globalServices.update(global); }

}
