package com.ditra.ditraschool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.DecimalFormat;


@SpringBootApplication
public class DitraSchoolApplication {
  public static void main(String[] args) {

    System.out.println(Integer.valueOf(new DecimalFormat("#").format(15.55554181874)));
    SpringApplication.run(DitraSchoolApplication.class, args); }
}
