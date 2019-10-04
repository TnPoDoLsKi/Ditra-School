package com.ditra.ditraschool.seeds;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.ditra.ditraschool.core.classe.ClasseRepository;
import com.ditra.ditraschool.core.classe.models.Classe;
import com.ditra.ditraschool.core.eleve.EleveRepository;
import com.ditra.ditraschool.core.eleve.models.Eleve;
import com.ditra.ditraschool.core.inscription.InscriptionRepository;
import com.ditra.ditraschool.core.inscription.models.Inscription;
import com.ditra.ditraschool.core.user.User;
import com.ditra.ditraschool.core.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;

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



    @EventListener
    public void seed(ContextRefreshedEvent event) throws ParseException {
        seedUser();
        ArrayList<Classe> classes = classeSeed();
        ArrayList<Eleve> eleves = eleveSeed();
        ArrayList<Inscription> inscriptions= inscriptionSeed(classes,eleves);
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
        ArrayList<Eleve> eleves = new ArrayList<>();

        Eleve eleve = new Eleve();
        eleve.setNom("ousema");
        eleve.setMatricule("a1");
        eleve.setAdresse("sfax");

        eleves.add(eleveRepository.save(eleve));

        eleve = new Eleve();
        eleve.setNom("bassem");
        eleve.setMatricule("a2");
        eleve.setAdresse("werdenin");

        eleves.add(eleveRepository.save(eleve));

        eleve = new Eleve();
        eleve.setNom("sofien");
        eleve.setMatricule("a3");
        eleve.setAdresse("medenin");

        eleves.add(eleveRepository.save(eleve));

        eleve = new Eleve();
        eleve.setNom("wael");
        eleve.setMatricule("a4");
        eleve.setAdresse("maloul");

        eleves.add(eleveRepository.save(eleve));

        eleve = new Eleve();
        eleve.setNom("aziz");
        eleve.setMatricule("a5");
        eleve.setAdresse("manzel bourgiba");

        eleves.add(eleveRepository.save(eleve));

        return eleves;

    }


    public ArrayList<Classe> classeSeed(){

        ArrayList<Classe> classes = new ArrayList<>();
        Classe classe = new Classe();

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

        return classes;
    }

    public ArrayList<Inscription> inscriptionSeed(ArrayList<Classe> classes , ArrayList<Eleve> eleves){

        ArrayList<Inscription> inscriptions = new ArrayList<>();

        Inscription inscription =  new Inscription();

        inscription.setEleve(eleves.get(0));
        inscription.setClasse(classes.get(0));
        inscription.setReglement("R");

        inscriptions.add(inscriptionRepository.save(inscription));

        inscription =  new Inscription();

        inscription.setEleve(eleves.get(0));
        inscription.setClasse(classes.get(1));
        inscription.setReglement("R");

        inscriptions.add(inscriptionRepository.save(inscription));


        inscription =  new Inscription();

        inscription.setEleve(eleves.get(1));
        inscription.setClasse(classes.get(0));
        inscription.setReglement("RN");

        inscriptions.add(inscriptionRepository.save(inscription));


        inscription =  new Inscription();

        inscription.setEleve(eleves.get(1));
        inscription.setClasse(classes.get(2));
        inscription.setReglement("RN");

        inscriptions.add(inscriptionRepository.save(inscription));




        inscription =  new Inscription();

        inscription.setEleve(eleves.get(2));
        inscription.setClasse(classes.get(1));
        inscription.setReglement("R");

        inscriptions.add(inscriptionRepository.save(inscription));




        inscription =  new Inscription();

        inscription.setEleve(eleves.get(2));
        inscription.setClasse(classes.get(3));
        inscription.setReglement("R");

        inscriptions.add(inscriptionRepository.save(inscription));





        inscription =  new Inscription();

        inscription.setEleve(eleves.get(3));
        inscription.setClasse(classes.get(2));
        inscription.setReglement("R");

        inscriptions.add(inscriptionRepository.save(inscription));




        inscription =  new Inscription();

        inscription.setEleve(eleves.get(3));
        inscription.setClasse(classes.get(2));
        inscription.setReglement("R");

        inscriptions.add(inscriptionRepository.save(inscription));




        inscription =  new Inscription();

        inscription.setEleve(eleves.get(4));
        inscription.setClasse(classes.get(3));
        inscription.setReglement("R");

        inscriptions.add(inscriptionRepository.save(inscription));



        inscription =  new Inscription();

        inscription.setEleve(eleves.get(1));
        inscription.setClasse(classes.get(3));
        inscription.setReglement("RN");

        inscriptions.add(inscriptionRepository.save(inscription));

        return inscriptions;

    }


}
