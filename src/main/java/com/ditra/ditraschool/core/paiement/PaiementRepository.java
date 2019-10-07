package com.ditra.ditraschool.core.paiement;

import com.ditra.ditraschool.core.paiement.models.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {

  Optional<Paiement> findPaiementByCode(String code);
}
