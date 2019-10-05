package com.ditra.ditraschool.core.classe;

import com.ditra.ditraschool.core.classe.models.Classe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClasseRepository extends JpaRepository<Classe,Long> {
  Optional<Classe> findClasseByClasse(String classe);
}
