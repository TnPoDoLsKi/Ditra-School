package com.ditra.ditraschool.core.facture;

import com.ditra.ditraschool.core.article.ArticleRepository;
import com.ditra.ditraschool.core.article.models.Article;
import com.ditra.ditraschool.core.classe.models.Classe;
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

import java.util.ArrayList;
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

  @Autowired
  GlobalRepository globalRepository;



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

    Global global = globalRepository.getOne(Long.valueOf(1));
    facture.setTva(global.getTva());
    facture.setTimbreFiscale(global.getTimbreFiscale());

    Double somme = Double.valueOf(0);
    for (Long id : factureUpdate.getArticles()) {
      Optional<Article> article = articleRepository.findById(id);

      if (!article.isPresent())
        return Utils.badRequestResponse(605, "");

      facture.addArticle(article.get());
      somme = somme+((article.get().getMontantHT()/100)*facture.getTva())+article.get().getMontantHT()  ;
    }


    somme = somme+ ((inscription.get().getClasse().getFrais()/100)*facture.getTva()) + inscription.get().getClasse().getFrais();

    if (factureUpdate.getAvecTimbre()){
      somme = somme + global.getTimbreFiscale();
    }

    facture.setCode(factureUpdate.getCode());
    facture.setTotalTTC(somme);

    inscription.get().setMontantTotal(somme);

    inscriptionRepository.save(inscription.get());
    facture = factureRepository.save(facture);
    return new ResponseEntity<>(facture,HttpStatus.OK);

  }

  public ResponseEntity<?> update(Long id, FactureUpdate factureUpdate) {

    Optional<Facture> factureLocal = factureRepository.findById(id);

    Facture facture = new Facture();
    if(!factureLocal.isPresent())
      return Utils.badRequestResponse(603, "");


    Optional<Inscription> inscription = inscriptionRepository.findById(factureUpdate.getInscriptionId());

    if(!inscription.isPresent())
      return Utils.badRequestResponse(602, "");


    Global global = globalRepository.getOne(Long.valueOf(1));
    facture.setTva(global.getTva());
    facture.setTimbreFiscale(global.getTimbreFiscale());

    Double somme = Double.valueOf(0);
    for (Long idArticle : factureUpdate.getArticles()) {
      Optional<Article> article = articleRepository.findById(idArticle);

      if (!article.isPresent())
        return Utils.badRequestResponse(605, "");

      facture.addArticle(article.get());
      somme = somme+((article.get().getMontantHT()/100)*facture.getTva())+article.get().getMontantHT()  ;
    }


    somme = somme+ ((inscription.get().getClasse().getFrais()/100)*facture.getTva()) + inscription.get().getClasse().getFrais();

    if (factureUpdate.getAvecTimbre()){
      somme = somme + global.getTimbreFiscale();
    }

    facture.setCode(factureUpdate.getCode());
    facture.setTotalTTC(somme);

    inscription.get().setMontantTotal(somme);

    facture = Utils.merge(factureLocal.get() , facture);

    inscriptionRepository.save(inscription.get());
    facture = factureRepository.save(facture);

    return new ResponseEntity<>(facture , HttpStatus.OK);
  }

  public ResponseEntity<?> delete(Long id) {

    Optional<Facture> facture = factureRepository.findById(id);

    if(!facture.isPresent())
      return Utils.badRequestResponse(604, "");

    factureRepository.delete(facture.get());

    return new ResponseEntity<>(HttpStatus.OK);
  }

  public ResponseEntity<?> getArticles(Long id) {


    Optional<Inscription> inscription  = inscriptionRepository.findById(id);

    if(!inscription.isPresent())
      return Utils.badRequestResponse(602, "");

    List<Article> articles = new ArrayList<>();

    articles = articleRepository.findAll();

    Float montant =inscription.get().getClasse().getFrais();

    articles.get(0).setMontantHT(Double.valueOf(montant));

    return new ResponseEntity<>(articles,HttpStatus.OK);
  }
}
