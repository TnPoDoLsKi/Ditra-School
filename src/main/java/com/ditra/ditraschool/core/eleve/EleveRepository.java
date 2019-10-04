package com.ditra.ditraschool.core.eleve;

import com.ditra.ditraschool.core.eleve.models.Eleve;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EleveRepository extends JpaRepository<Eleve, Long> {
  List<Eleve> findAllByInscriptionsEmpty();
}
