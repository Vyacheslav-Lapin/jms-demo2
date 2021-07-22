package com.luxoft.trainings.java.rabbit.jms;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;

public class Main {

  @SneakyThrows
  public static void main(String... __) {

    @Cleanup val sender = new ExampleMessageSender();

    //noinspection unused
    @Cleanup val receiver = new ExampleMessageReceiver();

    for (int i = 1; i <= 5; i++) {
      sender.sendMessage("Hello world! %d".formatted(i));
      Thread.sleep(300);
    }
  }
}
