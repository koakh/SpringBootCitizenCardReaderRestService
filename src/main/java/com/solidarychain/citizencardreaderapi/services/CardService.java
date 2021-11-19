package com.solidarychain.citizencardreaderapi.services;

import java.util.concurrent.TimeUnit;

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

  private final SimpMessagingTemplate websocket;

  private PTEID_ReaderSet readerSet;
  private PTEID_ReaderContext context;
  private PTEID_EIDCard card;

  private void cardInsertedEvent() {
    System.out.println("Card inserted...");
    this.websocket.convertAndSend(WebSocketConfiguration.TOPIC_PREFIX + "/test", "{ message: 'Card inserted' }");
    this.getCard();
  }

  private void cardRemovedEvent() {
    System.out.println("Card removed...");
    this.websocket.convertAndSend(WebSocketConfiguration.TOPIC_PREFIX + "/test", "{ message: 'Card removed' }");
  }

  @Autowired
  public CardService(final SimpMessagingTemplate websocket) {
    this.websocket = websocket;
    try {
      PTEID_ReaderSet.initSDK();
      this.readerSet = PTEID_ReaderSet.instance();
      System.out.println(String.format("readerCount %s", readerSet.readerCount()));
      // TODO:
      PTEID_ReaderContext context = readerSet.getReader();
      context.SetEventCallback(new CardEventsCallback(() -> this.cardInsertedEvent(), () -> this.cardRemovedEvent()),
          null);
      // TODO:
      // this.getCard();
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
    try {
      this.websocket.convertAndSend(WebSocketConfiguration.TOPIC_PREFIX + "/test", "{ message: 'detecting card...' }");
      for (int i = 0; i < this.readerSet.readerCount(); i++) {
        this.context = this.readerSet.getReaderByNum(i);
        if (this.context.isCardPresent()) {
          this.card = this.context.getEIDCard();
          System.out.println(String.format("card isActive %b", this.card.isActive()));
          // this.context.SetEventCallback(
          // new CardEventsCallback(() -> this.cardInsertedEvent(), () ->
          // this.cardRemovedEvent()), null);
          // events
          if (this.card.isActive()) {
            PTEID_EId eid = this.card.getID();
            Citizen citizen = new Citizen(eid);
            System.out.println(citizen);
            // TODO
            // try {
            // TimeUnit.SECONDS.sleep(3);
            // } catch (InterruptedException e) {
            // e.printStackTrace();
            // }
            // this.context.SetEventCallback(
            // new CardEventsCallback(() -> this.cardInsertedEvent(), () ->
            // this.cardRemovedEvent()), null);
            // // assign to singleton
            // this.card = card;
            // TODO: remove callback
            // context.StopEventCallback(callbackId);
          } else {
            System.err.println("no card found");
            this.card = null;
          }
        }
      }
    } catch (PTEID_Exception e) {
      e.printStackTrace();
    }

    return this.card;
  }

  // @EventListener
  // public void doSomethingAfterApplicationReady(ApplicationReadyEvent event) {
  // System.out.println("ApplicationReady...");
  // try {
  // TimeUnit.SECONDS.sleep(5);
  // } catch (InterruptedException e) {
  // // TODO Auto-generated catch block
  // e.printStackTrace();
  // }
  // try {
  // this.context.SetEventCallback(
  // new CardEventsCallback(() -> this.cardInsertedEvent(), () ->
  // this.cardRemovedEvent()), null);
  // } catch (PTEID_Exception e) {
  // // TODO Auto-generated catch block
  // e.printStackTrace();
  // }
  // }
}
