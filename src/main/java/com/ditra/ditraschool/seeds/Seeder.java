package com.ditra.ditraschool.seeds;

import at.favre.lib.crypto.bcrypt.BCrypt;
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

    @EventListener
    public void seed(ContextRefreshedEvent event) throws ParseException {
        seedUser();
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

}
