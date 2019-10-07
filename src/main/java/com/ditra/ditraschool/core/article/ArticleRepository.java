package com.ditra.ditraschool.core.article;


import com.ditra.ditraschool.core.article.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
  Optional<Article> findArticleByCode(Long code);
}
