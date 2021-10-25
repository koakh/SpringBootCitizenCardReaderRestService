package com.solidarychain.citizencardreaderapi.controllers;

import com.solidarychain.citizencardreaderapi.models.Citizen;
import com.solidarychain.citizencardreaderapi.services.CardService;
import com.solidarychain.citizencardreaderapi.config.ConfigProperties;
import com.solidarychain.utils.HttpUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import pt.gov.cartaodecidadao.PTEID_EIDCard;
import pt.gov.cartaodecidadao.PTEID_EId;
import pt.gov.cartaodecidadao.PTEID_Exception;

@RestController
public class CardController {

  private CardService cardService;
  private ConfigProperties config;

  @Autowired
  public CardController(
      final ConfigProperties config,
      final CardService cardService
  ) {
    this.config = config;
    this.cardService = cardService;
  }

  @GetMapping("/")
  public String index() {
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
    return "card readed from endpoint.";
  }

  @GetMapping("/sign-in")
  public String testSignIn() {
    HttpUtils.graphql(this.config.getGraphqlFqdn(), this.config.getGraphqlUri());
    return "no repply";
  }
}