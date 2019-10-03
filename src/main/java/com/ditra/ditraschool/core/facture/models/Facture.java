package com.ditra.ditraschool.core.facture.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Where(clause = "deleted = false")
@SQLDelete(sql=" UPDATE facture SET deleted = true WHERE id = ?")
public class Facture {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String code;
  private Date date;
  private Double tva;
  private Boolean avecTimbre;
  private Double totalTTC;
}
