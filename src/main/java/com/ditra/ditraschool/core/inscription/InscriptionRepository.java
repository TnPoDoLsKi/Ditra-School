package com.ditra.ditraschool.core.inscription;

import com.ditra.ditraschool.core.inscription.models.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscriptionRepository extends JpaRepository<Inscription , Long> {
}
