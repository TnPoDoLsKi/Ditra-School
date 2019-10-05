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
      return Utils.badRequestResponse(611, "inscription introuvable");

    Optional<Facture> factureOptional = factureRepository.findFactureByCode(factureUpdate.getCode());

    if (!factureOptional.isPresent())
      return Utils.badRequestResponse(611, "code deja utilise");
    Facture facture = new Facture();

    facture.setInscription(inscription.get());

    Global global = globalRepository.findAll().get(0);
    facture.setTva(global.getTva());
    facture.setTimbreFiscale(global.getTimbreFiscale());
    facture.setCode(factureUpdate.getCode());


    Double somme = Double.valueOf(0);

    for (Long id : factureUpdate.getArticles()) {
      Optional<Article> article = articleRepository.findById(id);

      if (!article.isPresent())
        return Utils.badRequestResponse(615, "idArticle Introuvable");

      facture.addArticle(article.get());

      if (article.get().getCode().equals("1")){
        somme = somme+ ((inscription.get().getClasse().getFrais()/100)*facture.getTva()) + inscription.get().getClasse().getFrais();
      }
      else {
      somme = somme+((article.get().getMontantHT()/100)*facture.getTva())+article.get().getMontantHT()  ;
      }
    }

    if (factureUpdate.getAvecTimbre()){
      somme = somme + global.getTimbreFiscale();
    }

    facture.setTotalTTC(somme);

    inscription.get().setMontantTotal(somme);

    inscriptionRepository.save(inscription.get());

    facture = factureRepository.save(facture);

    return new ResponseEntity<>(facture,HttpStatus.OK);

  }

  public ResponseEntity<?> update(Long id, FactureUpdate factureUpdate) {

    Optional<Facture> factureLocal = factureRepository.findById(id);

    if(!factureLocal.isPresent())
      return Utils.badRequestResponse(600, "identifiant introuvable");

    Inscription inscription = factureLocal.get().getInscription();
    Facture facture = new Facture();
    facture.setCode(factureUpdate.getCode());


    Double somme = Double.valueOf(0);

    for (Long idArticle : factureUpdate.getArticles()) {
      Optional<Article> article = articleRepository.findById(idArticle);

      if (!article.isPresent())
        return Utils.badRequestResponse(615, "idArticle Introuvable");

      facture.addArticle(article.get());

      if (article.get().getCode().equals("1")){
        somme = somme+ ((inscription.getClasse().getFrais()/100)*facture.getTva()) + inscription.getClasse().getFrais();
      }
      else {
        somme = somme+((article.get().getMontantHT()/100)*facture.getTva())+article.get().getMontantHT()  ;
      }
    }

    if (factureUpdate.getAvecTimbre()){
      somme = somme + facture.getTimbreFiscale();
    }

    facture.setTotalTTC(somme);

    inscription.setMontantTotal(inscription.getMontantTotal() - factureLocal.get().getTotalTTC() + somme);

    inscriptionRepository.save(inscription);

    facture = Utils.merge(factureLocal.get(),facture);

    facture = factureRepository.save(facture);

    return new ResponseEntity<>(facture,HttpStatus.OK);
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
      return Utils.badRequestResponse(600, "identifiant introuvable");

    List<Article> articles = articleRepository.findAll();

    Float montant =inscription.get().getClasse().getFrais();

    for (int i=0 ; i< articles.size() ; i++)
      if (articles.get(i).getCode() == "1")
        articles.get(i).setMontantHT(Double.valueOf(montant));

    return new ResponseEntity<>(articles,HttpStatus.OK);
  }
}
