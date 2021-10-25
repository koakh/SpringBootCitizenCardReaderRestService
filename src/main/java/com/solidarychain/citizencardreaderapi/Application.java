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
    // TODO: output
		// String path = System.getProperty("java.library.path");

    System.setProperty("javax.net.ssl.trustStore", "trust-store.jks");
    System.setProperty("javax.net.ssl.trustStorePassword", "TrustStore");
    // download cerificate with firefox
    // https://stackoverflow.com/questions/6908948/java-sun-security-provider-certpath-suncertpathbuilderexception-unable-to-find
    // scp /home/mario/Downloads/localhost* 192.168.122.160:/tmp
    // keytool -import -alias ca -keystore trust-store.jks -storepass TrustStore -trustcacerts -file /tmp/localhost-chain.pem
    // Owner: CN=localhost
    // Issuer: CN=localhost
    // Serial number: 7b295e10bd81d13012d6bb9c708f5df39254de7b
    // Valid from: Tue Oct 12 20:59:38 UTC 2021 until: Thu Sep 18 20:59:38 UTC 2121
    // +
    // https://www.ti-enxame.com/pt/java/como-corrigir-o-erro-java.security.cert.certificateexception-no-subject-alternative-names-present/1043257709/
    // disable https verification on host

    
		SpringApplication.run(Application.class, args);
	}
}
