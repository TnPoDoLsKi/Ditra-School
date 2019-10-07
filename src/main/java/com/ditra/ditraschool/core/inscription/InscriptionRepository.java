package com.ditra.ditraschool.core.inscription;

import com.ditra.ditraschool.core.inscription.models.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InscriptionRepository extends JpaRepository<Inscription , Long> {

  Optional<Inscription> findInscriptionByCode(Long code);
}
