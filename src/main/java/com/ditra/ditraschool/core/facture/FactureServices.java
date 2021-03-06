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
import org.springframework.transaction.annotation.Transactional;
import pl.allegro.finance.tradukisto.MoneyConverters;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
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



  public ResponseEntity<?> getAll(){
    return new ResponseEntity<>(factureRepository.findAll(),HttpStatus.OK);
  }

  public ResponseEntity<?> getCode() {
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

    Optional<Inscription> inscriptionOptional = inscriptionRepository.findById(factureUpdate.getInscriptionId());

    if(!inscriptionOptional.isPresent())
      return Utils.badRequestResponse(652, "inscription introuvable");

    Optional<Facture> factureOptional = factureRepository.findFactureByCode(factureUpdate.getCode());

    if (factureOptional.isPresent())
      return Utils.badRequestResponse(611, "code deja utilise");


    // initialisation de la facture

    Inscription inscription = inscriptionOptional.get();
    Facture facture = new Facture();

    facture.setInscription(inscription);
    facture.setCode(factureUpdate.getCode());

    Global global = globalRepository.findAll().get(0);
    facture.setTva(global.getTva());

    switch (inscription.getEleve().getTuteur()) {
      case "pere":
        facture.setTuteur(inscription.getEleve().getNomPere());
        break;
      case "mere":
        facture.setTuteur(inscription.getEleve().getNomMere());
        break;
      case "autre":
        facture.setTuteur(inscription.getEleve().getNomAutre());
        break;
    }


    // calcule du montant

    Double somme = 0.0;

    for (ArticleFacture article : factureUpdate.getArticles()) {
      if (article.getMontantHT() == null)
        return Utils.badRequestResponse(618, "monatant requis");

      if (article.getDesignation() == null)
        return Utils.badRequestResponse(617, "designation requis");

      somme += ((article.getMontantHT() / 100) * facture.getTva()) + article.getMontantHT() ;
    }

    facture.setAvecTimbre(factureUpdate.getAvecTimbre());

    if (factureUpdate.getAvecTimbre()){
      somme += global.getTimbreFiscale();
      facture.setTimbreFiscale(global.getTimbreFiscale());
    }

    inscription.setMontantTotal(inscription.getMontantTotal() + somme);
    inscription.setMontantRestant(inscription.getMontantRestant() + somme);



    // montant en toute lettres

    String formatedTotalTTC = new DecimalFormat("#.###").format(somme);
    int fractionalValue = Integer.parseInt(formatedTotalTTC.split("\\.")[1]);

    while(fractionalValue <= 99)
      fractionalValue *= 10;

    MoneyConverters converter = MoneyConverters.FRENCH_BANKING_MONEY_VALUE;

    String montantEnLettre = converter.asWords(new BigDecimal(somme.intValue())).split("€")[0] + "dinars ";
    montantEnLettre += converter.asWords(new BigDecimal(fractionalValue)).split("€")[0] + "millimes";

    facture.setTotalTTcEnMot(montantEnLettre.toUpperCase());
    facture.setTotalTTC(Double.valueOf(formatedTotalTTC));


    // sauvegarde des données

    inscriptionRepository.save(inscription);
    facture = factureRepository.save(facture);

    for (ArticleFacture article : factureUpdate.getArticles()) {
      article.setFacture(facture);
      articleFactureRepository.save(article);
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



    // initialisation de la facture

    Inscription inscription = factureLocal.get().getInscription();
    Facture facture = new Facture();

    facture.setCode(factureUpdate.getCode());



    // calcule du montant

    Double somme = 0.0;

    for (ArticleFacture article : factureUpdate.getArticles()) {
      if (article.getMontantHT() == null)
        return Utils.badRequestResponse(618, "monatant requis");

      if (article.getDesignation() == null)
        return Utils.badRequestResponse(617, "designation requis");
    }

    articleFactureRepository.deleteAllByFactureId(id);

    for (ArticleFacture article : factureUpdate.getArticles()) {
      article.setFacture(factureLocal.get());
      article = articleFactureRepository.save(article);
      somme += ((article.getMontantHT() / 100) * factureLocal.get().getTva()) + article.getMontantHT()  ;
    }

    facture.setAvecTimbre(factureUpdate.getAvecTimbre());

    if (factureUpdate.getAvecTimbre()) {

      Global global = globalRepository.findAll().get(0);
      somme += global.getTimbreFiscale();
      facture.setTimbreFiscale(global.getTimbreFiscale());

    } else {
      facture.setTimbreFiscale(0.0);
    }

    inscription.setMontantTotal(inscription.getMontantTotal() - factureLocal.get().getTotalTTC() + somme);
    inscription.setMontantRestant(inscription.getMontantRestant() - factureLocal.get().getTotalTTC() + somme);



    // montant en toute lettres

    String formatedTotalTTC = new DecimalFormat("#.###").format(somme);
    int fractionalValue = Integer.parseInt(formatedTotalTTC.split("\\.")[1]);

    while(fractionalValue <= 99)
      fractionalValue *= 10;

    MoneyConverters converter = MoneyConverters.FRENCH_BANKING_MONEY_VALUE;

    String montantEnLettre = converter.asWords(new BigDecimal(somme.intValue())).split("€")[0] + "dinars ";
    montantEnLettre += converter.asWords(new BigDecimal(fractionalValue)).split("€")[0] + "millimes";

    facture.setTotalTTcEnMot(montantEnLettre.toUpperCase());
    facture.setTotalTTC(Double.valueOf(formatedTotalTTC));



    // sauvegarde des données

    facture = Utils.merge(factureLocal.get(), facture);

    inscriptionRepository.save(inscription);
    facture = factureRepository.save(facture);

    return new ResponseEntity<>(facture, HttpStatus.OK);
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

    Float montant ;
    if (inscription.get().getClasse().getFrais() != null) {
       montant = inscription.get().getClasse().getFrais();
    }
    else {
      montant = Float.valueOf(0) ;
    }


    for (int i=0 ; i< articles.size() ; i++)
      if (articles.get(i).getCode() == 1)
        articles.get(i).setMontantHT(Double.valueOf(montant));

    return new ResponseEntity<>(articles,HttpStatus.OK);
  }

}