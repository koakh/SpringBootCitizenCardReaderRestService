package com.solidarychain.citizencardreaderapi.controllers;

import com.solidarychain.citizencardreaderapi.models.Citizen;
import com.solidarychain.citizencardreaderapi.services.CardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import pt.gov.cartaodecidadao.PTEID_EIDCard;
import pt.gov.cartaodecidadao.PTEID_EId;
import pt.gov.cartaodecidadao.PTEID_Exception;

@RestController
public class CardController {

  @Autowired
  private CardService cardService;

  @GetMapping("/")
  public String index() {

    // try {
    // PTEID_ReaderSet.initSDK();

    // PTEID_EIDCard card;
    // PTEID_ReaderContext context;
    // PTEID_ReaderSet readerSet;

    // readerSet = PTEID_ReaderSet.instance();

    // System.out.println(String.format("readerCount %s", readerSet.readerCount()));

    // for (int i = 0; i < readerSet.readerCount(); i++) {
    // context = readerSet.getReaderByNum(i);
    // if (context.isCardPresent()) {
    // card = context.getEIDCard();
    // System.out.println(card.isActive());

    // if (card.isActive()) {
    // PTEID_EId eid = card.getID();
    // Citizen citizen = new Citizen(eid);
    // System.out.println(citizen);
    // } else {
    // System.err.println("no card found");
    // }
    // }
    // }
    // // A finalização do SDK (é obrigatória) deve ser efectuada através da
    // invocação
    // // do método PTEID_releaseSDK(), a invocação deste método garante que todos
    // os
    // // processos em segundo plano são terminados e que a memória alocada é
    // // libertada.
    // PTEID_ReaderSet.releaseSDK();
    // } catch (Exception e) {
    // System.out.println(e);
    // }
    try {
      PTEID_EIDCard card = this.cardService.getCard();
      if (card.isActive()) {
        PTEID_EId eid = card.getID();
        Citizen citizen = new Citizen(eid);
        System.out.println(citizen);
      } else {
        System.err.println("no card found");
      }
    } catch (PTEID_Exception e) {
      e.printStackTrace();
    }

    return "Greetings from Spring Boot!";
  }
}