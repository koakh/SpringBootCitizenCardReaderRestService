package com.solidarychain.citizencardreaderapi.controllers;

import com.solidarychain.citizencardreaderapi.models.Citizen;
import com.solidarychain.citizencardreaderapi.services.CardService;
import com.solidarychain.citizencardreaderapi.config.ConfigProperties;
import com.solidarychain.citizencardreaderapi.config.WebSocketConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pt.gov.cartaodecidadao.PTEID_EIDCard;
import pt.gov.cartaodecidadao.PTEID_EId;
import pt.gov.cartaodecidadao.PTEID_Exception;

@RestController()
@RequestMapping("card")
public class CardController {

  private final SimpMessagingTemplate websocket;
  private final CardService cardService;
  private final ConfigProperties config;

  @Autowired
  public CardController(final SimpMessagingTemplate websocket, final ConfigProperties config,
      final CardService cardService) {
    this.websocket = websocket;
    this.config = config;
    this.cardService = cardService;
  }

  @GetMapping("/read")
  public String index() {
    try {
      // TODO: move all this stuff to service readCard
      PTEID_EIDCard card = this.cardService.getCard();
      if (card != null && card.isActive()) {
        PTEID_EId eid = card.getID();
        Citizen citizen = new Citizen(eid);
        System.out.println(citizen);
        // transmit a message over the WebSocket. This is a pub-sub approach so that one message is relayed to every attached consumer.
        // The route of each message is different, allowing multiple messages to be sent to distinct receivers on the client while needing only one open WebSocket — a resource-efficient approach.
        this.websocket.convertAndSend(WebSocketConfiguration.TOPIC_PREFIX + "/test", citizen.toString());
        return "card readed from endpoint.";
      } else {
        System.err.println("no card found");
        this.websocket.convertAndSend(WebSocketConfiguration.TOPIC_PREFIX + "/test", " { message: 'no card found' }");
        return "no card found";
      }
    } catch (PTEID_Exception e) {
      e.printStackTrace();
      return e.getCause().getMessage();
    }    
  }
}