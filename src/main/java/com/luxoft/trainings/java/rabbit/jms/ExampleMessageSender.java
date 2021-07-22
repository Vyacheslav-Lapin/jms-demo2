package com.luxoft.trainings.java.rabbit.jms;

import static com.luxoft.trainings.java.rabbit.jms.JmsProvider.QUEUE_NAME;
import static com.luxoft.trainings.java.rabbit.jms.JmsProvider.getConnectionFactory;

import io.vavr.CheckedFunction0;
import io.vavr.CheckedFunction1;
import io.vavr.CheckedFunction2;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import lombok.experimental.ExtensionMethod;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtensionMethod(ScopedFunctions.class)
public class ExampleMessageSender implements AutoCloseable {

  Connection connection = CheckedFunction0.of(getConnectionFactory()::createConnection).unchecked()
                              .apply()
                              .applyChecked(Connection::start);

  Session session = CheckedFunction2.of(connection::createSession).unchecked().apply(false, Session.AUTO_ACKNOWLEDGE);

  MessageProducer producer = CheckedFunction1.of(session::createProducer).unchecked().apply(
      CheckedFunction1.of(session::createQueue).unchecked()
          .apply(QUEUE_NAME));

  public void sendMessage(ExampleMessageSender this, String message) throws JMSException {
    log.info("Sending message: {}, Thread: {}", message, Thread.currentThread().getName());
    producer.send(session.createTextMessage(message));
  }

  @Override
  public void close() throws JMSException {
    connection.close();
  }
}
