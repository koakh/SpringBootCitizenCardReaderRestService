package com.solidarychain.citizencardreaderapi;

import pt.gov.cartaodecidadao.Callback;

// https://amagovpt.github.io/docs.autenticacao.gov/sdk/java/overview-summary.html

public class CardEventsCallback implements Callback {
   // https://stackoverflow.com/questions/25186216/java-8-pass-method-as-parameter/25199262
   private Runnable cardInserted;
   private Runnable cardremoved;

   public CardEventsCallback(Runnable cardInserted, Runnable cardremoved) {
      this.cardInserted = cardInserted;
      this.cardremoved = cardremoved;
   }

   @Override
   public void getEvent(long lRet, long ulState, Object callbackData) {
      int cardState = (int) ulState & 0x0000FFFF;
      // int eventCounter = ((int) ulState) >> 16;
      // System.err.println("Card Event:" + " cardState: " + cardState + " Event Counter: " + eventCounter);
      if ((cardState & 0x0100) != 0) {
         // call external runnable
         this.cardInserted.run();
      } else {
         // call external runnable
         this.cardremoved.run();
      }
   }
}