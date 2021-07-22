package com.luxoft.trainings.java.rabbit.jms;

import java.util.Collections;
import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsProvider {

  public static final String QUEUE_NAME = "example.queue";

  /**
   * The VM transport allows clients to connect to each other inside
   *                  the VM without the overhead of the network communication.
   */
  public static ConnectionFactory getConnectionFactory () {
    final var connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
    connectionFactory.setTrustedPackages(Collections.singletonList("com.luxoft.trainings.java.rabbit.jms"));
    return connectionFactory;
  }
}
