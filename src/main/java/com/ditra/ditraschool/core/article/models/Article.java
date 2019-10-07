package com.ditra.ditraschool.core.article.models;

import com.ditra.ditraschool.utils.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Where(clause = "deleted = false")
@SQLDelete(sql=" UPDATE article SET deleted = true WHERE id = ?")
public class Article extends Auditable<String> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long code;
  private String designation;
  private Double montantHT;

  private boolean deleted = false;
}
