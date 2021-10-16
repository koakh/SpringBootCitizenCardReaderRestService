package com.solidarychain.citizencardreaderapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

import com.solidarychain.citizencardreaderapi.Citizen;

import pt.gov.cartaodecidadao.*;

@RestController
public class HelloController {

  @GetMapping("/")
  public String index() {
    try {
      PTEID_ReaderSet.initSDK();

      PTEID_EIDCard card;
      PTEID_ReaderContext context;
      PTEID_ReaderSet readerSet;

      readerSet = PTEID_ReaderSet.instance();

      System.out.println(String.format("readerCount %s", readerSet.readerCount()));

      for (int i = 0; i < readerSet.readerCount(); i++) {
        context = readerSet.getReaderByNum(i);
        if (context.isCardPresent()) {
          card = context.getEIDCard();
          System.out.println(card.isActive());

          if (card.isActive()) {
            PTEID_EId eid = card.getID();
            Citizen citizen = new Citizen(eid);
            System.out.println(citizen);
          } else {
            System.err.println("no card found");
          }
        }
      }
      // A finalização do SDK (é obrigatória) deve ser efectuada através da invocação
      // do método PTEID_releaseSDK(), a invocação deste método garante que todos os
      // processos em segundo plano são terminados e que a memória alocada é
      // libertada.
      PTEID_ReaderSet.releaseSDK();
    } catch (Exception e) {
      System.out.println(e);
    }

    return "Greetings from Spring Boot!";
  }
}