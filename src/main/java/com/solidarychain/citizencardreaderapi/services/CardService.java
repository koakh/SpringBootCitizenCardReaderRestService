package com.solidarychain.citizencardreaderapi.services;

import javax.annotation.PreDestroy;

import com.solidarychain.citizencardreaderapi.CardEventsCallback;
import com.solidarychain.citizencardreaderapi.config.WebSocketConfiguration;
import com.solidarychain.citizencardreaderapi.models.Citizen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import pt.gov.cartaodecidadao.PTEID_EIDCard;
import pt.gov.cartaodecidadao.PTEID_EId;
import pt.gov.cartaodecidadao.PTEID_Exception;
import pt.gov.cartaodecidadao.PTEID_ReaderContext;
import pt.gov.cartaodecidadao.PTEID_ReaderSet;

@Service
public class CardService {
  
  private PTEID_EIDCard card;
  private final SimpMessagingTemplate websocket;

  private void cardInsertedEvent() {
    System.out.println("Card inserted...fire websocket event here");
    this.websocket.convertAndSend(WebSocketConfiguration.TOPIC_PREFIX + "/test", "{ message: 'Card inserted' }");
  }

  private void cardRemovedEvent() {
    System.out.println("Card removed...fire websocket event here");
    this.websocket.convertAndSend(WebSocketConfiguration.TOPIC_PREFIX + "/test", "{ message: 'Card removed' }");
  }

  @Autowired
  public CardService(final SimpMessagingTemplate websocket) {
    this.websocket = websocket;
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
          System.out.println(String.format("card isActive %b", card.isActive()));
          // events
          context.SetEventCallback(
              new CardEventsCallback(() -> this.cardInsertedEvent(), () -> this.cardRemovedEvent()), null);
          if (card.isActive()) {
            PTEID_EId eid = card.getID();
            Citizen citizen = new Citizen(eid);
            System.out.println(citizen);
            // assign to singleton
            this.card = card;
          } else {
            System.err.println("no card found");
          }
        }
      }
      // A finalização do SDK (é obrigatória) deve ser efectuada através da invocação
      // do método PTEID_releaseSDK(), a invocação deste método garante que todos os
      // processos em segundo plano são terminados e que a memória alocada é
      // libertada.
      // TODO: put this in application destroy
      // PTEID_ReaderSet.releaseSDK();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  // TODO: works if we close with ctrl+c
  // https://www.baeldung.com/spring-shutdown-callbacks
  // During the bean initialization, Spring will register all the bean methods
  // that are annotated with
  // @PreDestroy and invokes them when the application shuts down.
  @PreDestroy
  public void destroy() {
    System.out.println("Callback triggered - @PreDestroy.");
    // A finalização do SDK (é obrigatória) deve ser efectuada através da invocação
    // do método PTEID_releaseSDK(), a invocação deste método garante que todos os
    // processos em segundo plano são terminados e que a memória alocada é
    // libertada.
    try {
      PTEID_ReaderSet.releaseSDK();
    } catch (PTEID_Exception e) {
      e.printStackTrace();
    }
  }

  public PTEID_EIDCard getCard() {
    return card;
  }
}
