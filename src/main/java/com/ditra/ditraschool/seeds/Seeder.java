package com.ditra.ditraschool.seeds;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.ditra.ditraschool.core.article.ArticleRepository;
import com.ditra.ditraschool.core.article.models.Article;
import com.ditra.ditraschool.core.articleFacture.ArticleFactureRepository;
import com.ditra.ditraschool.core.articleFacture.models.ArticleFacture;
import com.ditra.ditraschool.core.classe.ClasseRepository;
import com.ditra.ditraschool.core.classe.models.Classe;
import com.ditra.ditraschool.core.eleve.EleveRepository;
import com.ditra.ditraschool.core.eleve.models.Eleve;
import com.ditra.ditraschool.core.facture.FactureRepository;
import com.ditra.ditraschool.core.facture.models.Facture;
import com.ditra.ditraschool.core.global.GlobalRepository;
import com.ditra.ditraschool.core.global.models.Global;
import com.ditra.ditraschool.core.inscription.InscriptionRepository;
import com.ditra.ditraschool.core.inscription.models.Inscription;
import com.ditra.ditraschool.core.paiement.PaiementRepository;
import com.ditra.ditraschool.core.paiement.models.Paiement;
import com.ditra.ditraschool.core.user.User;
import com.ditra.ditraschool.core.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

@Component
public class Seeder {

    @Autowired
    UserRepository userRepository;
    @Autowired
    EleveRepository eleveRepository;
    @Autowired
    ClasseRepository classeRepository;
    @Autowired
    InscriptionRepository inscriptionRepository;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    FactureRepository factureRepository;
    @Autowired
    PaiementRepository paiementRepository;
    @Autowired
    GlobalRepository globalRepository;
    @Autowired
    ArticleFactureRepository articleFactureRepository;


    @EventListener
    public void seed(ContextRefreshedEvent event) throws ParseException {
        globalSeed();
        seedUser();
        ArrayList<Classe> classes = classeSeed();
        ArrayList<Eleve> eleves = eleveSeed();
        ArrayList<Inscription> inscriptions= inscriptionSeed(classes,eleves);
        ArrayList<Article> articles = articleSeed();
        ArrayList<Facture> factures= factureSeed(inscriptions);
        ArrayList<ArticleFacture> articleFactures = articleFactureSeed(factures);

        ArrayList<Paiement> paiements = paiementSeed(inscriptions);
    }

    public  void globalSeed(){

        ArrayList<Global> globals = (ArrayList<Global>) globalRepository.findAll();
        if(globals.size() == 0) {

            Global global = new Global();
            global.setTimbreFiscale(0.600);
            global.setTva(19.0);
            global.setRaisonSociale("DITRA Ecole");
            global.setAdresse("Sousse, trocadero");
            global.setMatriculeFiscale("MT2562145");
            global.setTelephone("24642979");

            globalRepository.save(global);
        }
    }

    public ArrayList<User> seedUser(){

        ArrayList<User> users = (ArrayList<User>) userRepository.findAll();
        if(users.size() == 0) {

            users = new ArrayList<>();

            User user = new User("mariam dammak", BCrypt.withDefaults().hashToString(10, "azerty123".toCharArray()), "mariamdammak6@gmail.com");
            users.add(userRepository.save(user));

            user = new User("medaziz bouchrit", BCrypt.withDefaults().hashToString(10, "azerty123".toCharArray()), "medazizbouchrit@gmail.com");
            users.add(userRepository.save(user));

            user = new User("Wael Ben Taleb", BCrypt.withDefaults().hashToString(10, "12345678".toCharArray()), "waelben7@gmail.com");
            users.add(userRepository.save(user));

            user = new User("oussema massmoudi", BCrypt.withDefaults().hashToString(10, "azerty123".toCharArray()), "masmoudiousema9@gmail.com");
            users.add(userRepository.save(user));

            user = new User("Sofien Msaddak", BCrypt.withDefaults().hashToString(10, "azerty123".toCharArray()), "sofien.msddak@gmail.com");
            users.add(userRepository.save(user));

            user = new User("Bassem  Karbia", BCrypt.withDefaults().hashToString(10, "azerty123".toCharArray()), "bassem.karbia@igc.tn");
            users.add(userRepository.save(user));

            user = new User("Baha Ferchichi", BCrypt.withDefaults().hashToString(10, "azerty123".toCharArray()), "baha.ferchichi@aiesec.net");
            users.add(userRepository.save(user));

            return users;
        }

        return users;
    }

    public ArrayList<Eleve> eleveSeed(){
        ArrayList<Eleve> eleves = (ArrayList<Eleve>) eleveRepository.findAll();
        if(eleves.size() == 0) {
            Eleve eleve = new Eleve();
            eleve.setPrenom("ousema");
            eleve.setNom("masmoudi");
            eleve.setMatricule(Long.valueOf(10001));
            eleve.setAdresse("sfax");
            eleve.setTuteur("pere");

            eleves.add(eleveRepository.save(eleve));

            eleve = new Eleve();
            eleve.setPrenom("bassem");
            eleve.setNom("karbia");
            eleve.setMatricule(Long.valueOf(10002));
            eleve.setAdresse("werdenin");
            eleve.setTuteur("pere");

            eleves.add(eleveRepository.save(eleve));

            eleve = new Eleve();
            eleve.setPrenom("sofien");
            eleve.setNom("msaddak");
            eleve.setMatricule(Long.valueOf(10003));
            eleve.setAdresse("medenin");
            eleve.setTuteur("pere");

            eleves.add(eleveRepository.save(eleve));

            eleve = new Eleve();
            eleve.setPrenom("wael");
            eleve.setNom("ben taleb");
            eleve.setMatricule(Long.valueOf(10004));
            eleve.setAdresse("maloul");
            eleve.setTuteur("pere");

            eleves.add(eleveRepository.save(eleve));

            eleve = new Eleve();
            eleve.setPrenom("aziz");
            eleve.setNom("bouchrit");
            eleve.setMatricule(Long.valueOf(10005));
            eleve.setAdresse("manzel bourgiba");
            eleve.setTuteur("pere");

            eleves.add(eleveRepository.save(eleve));
        }
        return eleves;
    }

    public ArrayList<Classe> classeSeed() {

        ArrayList<Classe> classes = (ArrayList<Classe>) classeRepository.findAll();
        Classe classe = new Classe();

        if (classes.size() == 0) {


        classe.setAnneeScolaire("2019-2020");
        classe.setClasse("al farachet");
        classe.setFrais(Float.valueOf(5000));
        classe.setObservation("classe 3a9el w behyin");

        classes.add(classeRepository.save(classe));

        classe = new Classe();

        classe.setAnneeScolaire("2019-2020");
        classe.setClasse("al 3asafir");
        classe.setFrais(Float.valueOf(6000));
        classe.setObservation("classe 3a9el w 9arayin");

        classes.add(classeRepository.save(classe));


        classe = new Classe();

        classe.setAnneeScolaire("2019-2020");
        classe.setClasse("al noumour");
        classe.setFrais(Float.valueOf(7000));
        classe.setObservation("classe thkiyin w el chwaten");

        classes.add(classeRepository.save(classe));


        classe = new Classe();

        classe.setAnneeScolaire("2019-2020");
        classe.setClasse("al 7amem");
        classe.setFrais(Float.valueOf(9000));
        classe.setObservation("classe mouselmin w mouthebrin");

        classes.add(classeRepository.save(classe));
    }
        return classes;
    }

    public ArrayList<Inscription> inscriptionSeed(ArrayList<Classe> classes , ArrayList<Eleve> eleves){

        ArrayList<Inscription> inscriptions = (ArrayList<Inscription>) inscriptionRepository.findAll();

        if(inscriptions.size() == 0) {


            Inscription inscription = new Inscription();

            inscription.setEleve(eleves.get(0));
            inscription.setClasse(classes.get(0));
            inscription.setReglement("PR");

            inscriptions.add(inscriptionRepository.save(inscription));

            inscription = new Inscription();

            inscription.setEleve(eleves.get(0));
            inscription.setClasse(classes.get(1));
            inscription.setReglement("R");

            inscriptions.add(inscriptionRepository.save(inscription));


            inscription = new Inscription();

            inscription.setEleve(eleves.get(1));
            inscription.setClasse(classes.get(0));
            inscription.setReglement("NR");

            inscriptions.add(inscriptionRepository.save(inscription));


            inscription = new Inscription();

            inscription.setEleve(eleves.get(1));
            inscription.setClasse(classes.get(2));
            inscription.setReglement("PR");

            inscriptions.add(inscriptionRepository.save(inscription));


            inscription = new Inscription();

            inscription.setEleve(eleves.get(2));
            inscription.setClasse(classes.get(1));
            inscription.setReglement("R");

            inscriptions.add(inscriptionRepository.save(inscription));


            inscription = new Inscription();

            inscription.setEleve(eleves.get(2));
            inscription.setClasse(classes.get(3));
            inscription.setReglement("R");

            inscriptions.add(inscriptionRepository.save(inscription));


            inscription = new Inscription();

            inscription.setEleve(eleves.get(3));
            inscription.setClasse(classes.get(2));
            inscription.setReglement("R");

            inscriptions.add(inscriptionRepository.save(inscription));


            inscription = new Inscription();

            inscription.setEleve(eleves.get(3));
            inscription.setClasse(classes.get(2));
            inscription.setReglement("R");

            inscriptions.add(inscriptionRepository.save(inscription));


            inscription = new Inscription();

            inscription.setEleve(eleves.get(4));
            inscription.setClasse(classes.get(3));
            inscription.setReglement("R");

            inscriptions.add(inscriptionRepository.save(inscription));


            inscription = new Inscription();

            inscription.setEleve(eleves.get(1));
            inscription.setClasse(classes.get(3));
            inscription.setReglement("NR");

            inscriptions.add(inscriptionRepository.save(inscription));

        }
        return inscriptions;

    }

    public ArrayList<Article> articleSeed() {

        ArrayList<Article> articles = (ArrayList<Article>) articleRepository.findAll();

        if (articles.size() == 0) {

            Article article = new Article();
            article.setDesignation("scolarité");
            article.setMontantHT(Double.valueOf(1500));
            article.setCode(Long.valueOf(1));

            articles.add(articleRepository.save(article));

            article.setDesignation("transport");
            article.setMontantHT(Double.valueOf(200));
            article.setCode(Long.valueOf(2));

            articles.add(articleRepository.save(article));

            article = new Article();
            article.setDesignation("accomodation");
            article.setMontantHT(Double.valueOf(500));
            article.setCode(Long.valueOf(3));

            articles.add(articleRepository.save(article));

            article = new Article();
            article.setDesignation("diner");
            article.setMontantHT(Double.valueOf(100));
            article.setCode(Long.valueOf(4));

            articles.add(articleRepository.save(article));

            article = new Article();
            article.setDesignation("dejeuner");
            article.setMontantHT(Double.valueOf(100));
            article.setCode(Long.valueOf(5));

            articles.add(articleRepository.save(article));

        }

        return articles;
    }

    public ArrayList<Facture> factureSeed(ArrayList<Inscription> inscriptions ){

        ArrayList<Facture> factures = (ArrayList<Facture>) factureRepository.findAll();

        if(factures.size() == 0) {


            Facture facture = new Facture();
            facture.setTva(Double.valueOf(19));
            facture.setCode(Long.valueOf(11001));
            facture.setTimbreFiscale(Double.valueOf(6));
            facture.setTotalTTC((double) 250);
            facture.setInscription(inscriptions.get(0));

            factures.add(factureRepository.save(facture));

            facture = new Facture();
            facture.setTva(Double.valueOf(19));
            facture.setCode(Long.valueOf(11002));
            facture.setTimbreFiscale(Double.valueOf(6));
            facture.setTotalTTC((double) 250);
            facture.setInscription(inscriptions.get(1));

            factures.add(factureRepository.save(facture));

            facture = new Facture();
            facture.setTva(Double.valueOf(19));
            facture.setCode(Long.valueOf(11003));
            facture.setTimbreFiscale(Double.valueOf(6));
            facture.setTotalTTC((double) 250);
            facture.setInscription(inscriptions.get(2));

            factures.add(factureRepository.save(facture));


            facture = new Facture();
            facture.setTva(Double.valueOf(19));
            facture.setCode(Long.valueOf(11004));
            facture.setTimbreFiscale(Double.valueOf(6));
            facture.setTotalTTC((double) 250);
            facture.setInscription(inscriptions.get(3));

            factures.add(factureRepository.save(facture));


            facture = new Facture();
            facture.setTva(Double.valueOf(19));
            facture.setCode(Long.valueOf(11005));
            facture.setTimbreFiscale(Double.valueOf(6));
            facture.setTotalTTC((double) 250);
            facture.setInscription(inscriptions.get(4));

            factures.add(factureRepository.save(facture));


        }
      return factures;
    }

    public ArrayList<Paiement> paiementSeed(ArrayList<Inscription> inscriptions){

        ArrayList<Paiement> paiements = (ArrayList<Paiement>) paiementRepository.findAll();

        if(paiements.size() == 0) {

            Paiement paiement = new Paiement();
            paiement.setInscription(inscriptions.get(0));
            paiement.setMode("cheque");
            paiement.setEcheance(new Date());
            paiement.setCode(Long.valueOf(12001));

            paiements.add(paiementRepository.save(paiement));

            paiement = new Paiement();
            paiement.setInscription(inscriptions.get(1));
            paiement.setMode("especes");
            paiement.setEcheance(new Date());
            paiement.setCode(Long.valueOf(12002));

            paiements.add(paiementRepository.save(paiement));


            paiement = new Paiement();
            paiement.setInscription(inscriptions.get(2));
            paiement.setMode("cheque");
            paiement.setEcheance(new Date());
            paiement.setCode(Long.valueOf(12003));

            paiements.add(paiementRepository.save(paiement));


            paiement = new Paiement();
            paiement.setInscription(inscriptions.get(3));
            paiement.setMode("especes");
            paiement.setEcheance(new Date());
            paiement.setCode(Long.valueOf(12004));

            paiements.add(paiementRepository.save(paiement));
        }
        return paiements;
    }

    public ArrayList<ArticleFacture> articleFactureSeed(ArrayList<Facture> factures) {

        ArrayList<ArticleFacture> articles = (ArrayList<ArticleFacture>) articleFactureRepository.findAll();

        if (articles.size() == 0) {

            ArticleFacture article = new ArticleFacture();
            article.setDesignation("scolarité");
            article.setMontantHT(Double.valueOf(1500));
            article.setFacture(factures.get(0));

            articles.add(articleFactureRepository.save(article));

            article = new ArticleFacture();
            article.setDesignation("transport");
            article.setMontantHT(Double.valueOf(200));
            article.setFacture(factures.get(0));

            articles.add(articleFactureRepository.save(article));

            article = new ArticleFacture();
            article.setDesignation("accomodation");
            article.setMontantHT(Double.valueOf(500));
            article.setFacture(factures.get(0));

            articles.add(articleFactureRepository.save(article));

            article = new ArticleFacture();
            article.setDesignation("diner");
            article.setMontantHT(Double.valueOf(100));
            article.setFacture(factures.get(1));

            articles.add(articleFactureRepository.save(article));

            article = new ArticleFacture();
            article.setDesignation("dejeuner");
            article.setMontantHT(Double.valueOf(100));
            article.setFacture(factures.get(1));

            articles.add(articleFactureRepository.save(article));

        }

        return articles;
    }
}
