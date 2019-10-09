package com.ditra.ditraschool.core.articleFacture;

import com.ditra.ditraschool.core.articleFacture.models.ArticleFacture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleFactureRepository extends JpaRepository<ArticleFacture, Long> {
    void  deleteAllByFactureId(long factureId);
}
