package com.ditra.ditraschool.core.facture;

import com.ditra.ditraschool.core.facture.models.Facture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FactureRepository extends JpaRepository<Facture, Long> {
  Optional<Facture> findFactureByCode(Long code);
}
