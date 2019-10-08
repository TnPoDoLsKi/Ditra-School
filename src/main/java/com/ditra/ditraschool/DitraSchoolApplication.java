package com.ditra.ditraschool;

import com.ditra.ditraschool.utils.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.allegro.finance.tradukisto.MoneyConverters;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;


@SpringBootApplication
public class DitraSchoolApplication {
  public static void main(String[] args) {
    SpringApplication.run(DitraSchoolApplication.class, args); }
}
