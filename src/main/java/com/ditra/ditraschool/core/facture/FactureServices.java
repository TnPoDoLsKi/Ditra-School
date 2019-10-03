package com.ditra.ditraschool.core.facture;

import com.ditra.ditraschool.core.article.ArticleRepository;
import com.ditra.ditraschool.core.article.models.Article;
import com.ditra.ditraschool.core.classe.models.Classe;
import com.ditra.ditraschool.core.facture.models.Facture;
import com.ditra.ditraschool.core.facture.models.FactureUpdate;
import com.ditra.ditraschool.core.inscription.InscriptionRepository;
import com.ditra.ditraschool.core.inscription.models.Inscription;
import com.ditra.ditraschool.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FactureServices {

  @Autowired
  FactureRepository factureRepository;

  @Autowired
  InscriptionRepository inscriptionRepository;

  @Autowired
  ArticleRepository articleRepository;


  public ResponseEntity<?> getAll() {
    List<Facture> factures = factureRepository.findAll();
    return new ResponseEntity<>(factures, HttpStatus.OK);
  }

  public ResponseEntity<?> getOne(Long id) {
    Optional<Facture> facture = factureRepository.findById(id);

    if(!facture.isPresent())
      return Utils.badRequestResponse(604, "");

    return new ResponseEntity<>(facture,HttpStatus.OK);
  }

  public ResponseEntity<?> create(FactureUpdate factureUpdate) {

    Facture facture = new Facture();

    Optional<Inscription> inscription = inscriptionRepository.findById(factureUpdate.getInscriptionId());

    if(!inscription.isPresent())
      return Utils.badRequestResponse(602, "");

    facture.setInscription(inscription.get());


    for (Long id : factureUpdate.getArticles()) {
      Optional<Article> article = articleRepository.findById(id);

      if (!article.isPresent())
        return Utils.badRequestResponse(605, "");

      facture.addArticle(article.get());
    }

    facture.setCode(factureUpdate.getCode());
    facture.setTimbreFiscale(factureUpdate.getTimbreFiscale());
    facture.setTotalTTC(factureUpdate.getTotalTTC());
    facture.setTva(factureUpdate.getTva());


    facture = factureRepository.save(facture);
    return new ResponseEntity<>(facture,HttpStatus.OK);

  }

  public ResponseEntity<?> update(Long id, FactureUpdate factureUpdate) {

    Optional<Facture> factureLocal = factureRepository.findById(id);

    if(!factureLocal.isPresent())
      return Utils.badRequestResponse(603, "");

    Facture facture = new Facture();

    Optional<Inscription> inscription = inscriptionRepository.findById(factureUpdate.getInscriptionId());

    if(!inscription.isPresent())
      return Utils.badRequestResponse(602, "");

    facture.setInscription(inscription.get());


    for (Long articleId : factureUpdate.getArticles()) {
      Optional<Article> article = articleRepository.findById(articleId);

      if (!article.isPresent())
        return Utils.badRequestResponse(605, "");

      facture.addArticle(article.get());
    }

    facture.setCode(factureUpdate.getCode());
    facture.setTimbreFiscale(factureUpdate.getTimbreFiscale());
    facture.setTotalTTC(factureUpdate.getTotalTTC());
    facture.setTva(factureUpdate.getTva());

    facture = Utils.merge(factureLocal.get(),facture);

    return new ResponseEntity<>(facture , HttpStatus.OK);
  }

  public ResponseEntity<?> delete(Long id) {

    Optional<Facture> facture = factureRepository.findById(id);

    if(!facture.isPresent())
      return Utils.badRequestResponse(604, "");

    factureRepository.delete(facture.get());

    return new ResponseEntity<>(HttpStatus.OK);
  }
}
