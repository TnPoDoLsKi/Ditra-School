package com.ditra.ditraschool.core.paiement.models;

import com.ditra.ditraschool.core.inscription.models.Inscription;
import com.ditra.ditraschool.utils.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Where(clause = "deleted = false")
@SQLDelete(sql=" UPDATE paiement SET deleted = true WHERE id = ?")
public class Paiement extends Auditable<String>  {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long code;

  private String mode;

  private Double montant;

  private Date echeance;

  private boolean deleted = false;


  @ManyToOne
  @JsonIgnore
  private Inscription inscription;
}
