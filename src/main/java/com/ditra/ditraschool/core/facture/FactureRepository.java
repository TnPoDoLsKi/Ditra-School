package com.ditra.ditraschool.core.facture;

import com.ditra.ditraschool.core.facture.models.Facture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FactureRepository extends JpaRepository<Facture, Long> {
}
