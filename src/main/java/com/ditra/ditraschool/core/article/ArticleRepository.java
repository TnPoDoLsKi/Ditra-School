package com.ditra.ditraschool.core.article;


import com.ditra.ditraschool.core.article.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
