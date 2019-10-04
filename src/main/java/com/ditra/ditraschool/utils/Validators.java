package com.ditra.ditraschool.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validators {

    public boolean emailValidator(String email){
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public boolean passwordValidator(String password){
       /*(?=.*[a-z])     : This matches the presence of at least one lowercase letter.
        (?=.*d)         : This matches the presence of at least one digit i.e. 0-9.
        ((?=.*[A-Z])    : This matches the presence of at least one capital letter.
        {6,16}          : This limits the length of password from minimum 6 letters to maximum 16 letters.
       */

        //String regex = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,16})";
        String regex = "((?=.*[a-z]).{8,16})";


        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public boolean usernameValidator(String name){
        /*Description
        A regexp for general username entry. Which doesn't allow special characters other than underscore. Username must be of length ranging(3-30). starting letter should be a number or a character.
        Matches
        fname_lastname, fname, f_name, 1_fname, 1_f
        Non-Matches
        _fname, _f, f_ , ff, 11,
        */
        String regex = "^[a-zA-Z0-9 ][a-zA-Z0-9_ ]{2,29}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);

        return matcher.matches();
    }
}
