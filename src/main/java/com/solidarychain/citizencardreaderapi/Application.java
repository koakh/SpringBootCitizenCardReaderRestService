package com.solidarychain.citizencardreaderapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
  private static final Logger logger = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) {
    logger.debug(String.format("java version %s", System.getProperty("java.version")));
    logger.debug(String.format("java library path %s", System.getProperty("java.library.path")));
    try {
      System.loadLibrary("pteidlibj");
      logger.debug("pteidlibj loaded");
    } catch (UnsatisfiedLinkError e) {
      logger.error("Native code library failed to load. \n" + e);
      System.exit(1);
    }
    System.setProperty("javax.net.ssl.trustStore", "trust-store.jks");
    System.setProperty("javax.net.ssl.trustStorePassword", "TrustStore");
    SpringApplication.run(Application.class, args);
  }
}
