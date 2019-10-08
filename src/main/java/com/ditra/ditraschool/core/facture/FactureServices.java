package com.ditra.ditraschool.core.facture;

import com.ditra.ditraschool.core.article.ArticleRepository;
import com.ditra.ditraschool.core.article.models.Article;
import com.ditra.ditraschool.core.articleFacture.models.ArticleFacture;
import com.ditra.ditraschool.core.articleFacture.ArticleFactureRepository;
import com.ditra.ditraschool.core.facture.models.Facture;
import com.ditra.ditraschool.core.facture.models.FactureUpdate;
import com.ditra.ditraschool.core.global.GlobalRepository;
import com.ditra.ditraschool.core.global.models.Global;
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
  ArticleFactureRepository articleFactureRepository;

  @Autowired
  GlobalRepository globalRepository;

  @Autowired
  ArticleRepository articleRepository;

  public ResponseEntity<?> getAll() {
    List<Facture> factures = factureRepository.findAll();

    if (factures.size() == 0) {
      return new ResponseEntity<>(0, HttpStatus.OK);
    }
    return new ResponseEntity<>(factures.get(factures.size()-1).getCode()+1, HttpStatus.OK);
  }

  public ResponseEntity<?> getByInscriptionId(Long inscriptionId) {

    if(inscriptionId == null)
      return Utils.badRequestResponse(650, "identifiant requis");

    Optional<Inscription> inscription  = inscriptionRepository.findById(inscriptionId);

    if(!inscription.isPresent())
      return Utils.badRequestResponse(600, "identifiant introuvable");

    List<Facture> factures = factureRepository.findByInscriptionId(inscriptionId);
    return new ResponseEntity<>(factures, HttpStatus.OK);
  }

  public ResponseEntity<?> getOne(Long id) {

    if(id == null)
      return Utils.badRequestResponse(650, "identifiant requis");

    Optional<Facture> facture = factureRepository.findById(id);

    if(!facture.isPresent())
      return Utils.badRequestResponse(600, "identifiant introuvable");

    return new ResponseEntity<>(facture,HttpStatus.OK);
  }

  public ResponseEntity<?> create(FactureUpdate factureUpdate) {

    if(factureUpdate.getInscriptionId() == null)
      return Utils.badRequestResponse(612, "inscriptionId requis");

    if(factureUpdate.getCode() == null)
      return Utils.badRequestResponse(606, "code requis");

    if(factureUpdate.getAvecTimbre() == null)
      return Utils.badRequestResponse(613, "avec timbre requis");

    if(factureUpdate.getArticles().isEmpty())
      return Utils.badRequestResponse(614, "articles requis");

    Optional<Inscription> inscription = inscriptionRepository.findById(factureUpdate.getInscriptionId());

    if(!inscription.isPresent())
      return Utils.badRequestResponse(652, "inscription introuvable");

    Optional<Facture> factureOptional = factureRepository.findFactureByCode(factureUpdate.getCode());

    if (factureOptional.isPresent())
      return Utils.badRequestResponse(611, "code deja utilise");

    Facture facture = new Facture();

    facture.setInscription(inscription.get());

    Global global = globalRepository.findAll().get(0);
    facture.setTva(global.getTva());
    facture.setCode(factureUpdate.getCode());

    Double somme = 0.0;

    for (ArticleFacture article : factureUpdate.getArticles()) {
      if (article.getMontantHT() == null)
        return Utils.badRequestResponse(618, "monatant requis");

      if (article.getDesignation() == null)
        return Utils.badRequestResponse(617, "designation requis");

      somme += ((article.getMontantHT()/100)*facture.getTva()) + article.getMontantHT()  ;
    }

    if (factureUpdate.getAvecTimbre()){
      facture.setTimbreFiscale(global.getTimbreFiscale());
      somme += global.getTimbreFiscale();
    }

    facture.setTotalTTC(somme);

    inscription.get().setMontantTotal(inscription.get().getMontantTotal() + somme);

    inscriptionRepository.save(inscription.get());

    facture = factureRepository.save(facture);

    for (ArticleFacture article : factureUpdate.getArticles()) {
      article = articleFactureRepository.save(article);
      article.setFacture(facture);
    }

    return new ResponseEntity<>(facture, HttpStatus.OK);
  }

  public ResponseEntity<?> update(Long id, FactureUpdate factureUpdate) {

    if(id == null)
      return Utils.badRequestResponse(650, "identifiant requis");

    Optional<Facture> factureLocal = factureRepository.findById(id);

    if(!factureLocal.isPresent())
      return Utils.badRequestResponse(600, "identifiant introuvable");

    Optional<Facture> factureOptional = factureRepository.findFactureByCode(factureUpdate.getCode());

    if (factureOptional.isPresent() && !factureLocal.get().getCode().equals(factureUpdate.getCode()))
      return Utils.badRequestResponse(611, "code deja utilise");

    Inscription inscription = factureLocal.get().getInscription();
    Facture facture = new Facture();
    facture.setCode(factureUpdate.getCode());

    Double somme = 0.0;

    for (ArticleFacture article : factureUpdate.getArticles()) {
      if (article.getMontantHT() == null)
        return Utils.badRequestResponse(618, "monatant requis");

      if (article.getDesignation() == null)
        return Utils.badRequestResponse(617, "designation requis");
    }

    for (ArticleFacture article : factureUpdate.getArticles()) {
      article.setFacture(factureLocal.get());
      article = articleFactureRepository.save(article);
      somme += ((article.getMontantHT()/100)*facture.getTva()) + article.getMontantHT()  ;
    }

    if (factureUpdate.getAvecTimbre())
      somme = somme + facture.getTimbreFiscale();

    facture.setTotalTTC(somme);

    inscription.setMontantTotal(inscription.getMontantTotal() - factureLocal.get().getTotalTTC() + somme);

    inscriptionRepository.save(inscription);

    facture = Utils.merge(factureLocal.get(), facture);

    facture = factureRepository.save(facture);

    return new ResponseEntity<>(facture,HttpStatus.OK);
  }

  public ResponseEntity<?> delete(Long id) {

    if(id == null)
      return Utils.badRequestResponse(650, "identifiant requis");

    Optional<Facture> facture = factureRepository.findById(id);

    if(!facture.isPresent())
      return Utils.badRequestResponse(600, "identifiant introuvable");

    factureRepository.delete(facture.get());

    return new ResponseEntity<>(HttpStatus.OK);
  }

  public ResponseEntity<?> getArticles(Long id) {

    if(id == null)
      return Utils.badRequestResponse(650, "identifiant requis");

    Optional<Inscription> inscription  = inscriptionRepository.findById(id);

    if(!inscription.isPresent())
      return Utils.badRequestResponse(600, "identifiant introuvable");

    List<Article> articles = articleRepository.findAll();

    Float montant =inscription.get().getClasse().getFrais();

    for (int i=0 ; i< articles.size() ; i++)
      if (articles.get(i).getCode() == 1)
        articles.get(i).setMontantHT(Double.valueOf(montant));

    return new ResponseEntity<>(articles, HttpStatus.OK);
  }
}
