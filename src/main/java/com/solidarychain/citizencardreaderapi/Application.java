package com.solidarychain.citizencardreaderapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// import pt.gov.cartaodecidadao.*;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
    try {
      System.loadLibrary("pteidlibj");
      // System.out.println("pteidlibj loaded");
    } catch (UnsatisfiedLinkError e) {
      System.err.println("Native code library failed to load. \n" + e);
      System.exit(1);
    }
		String path = System.getProperty("java.library.path");

		SpringApplication.run(Application.class, args);
	}
}
