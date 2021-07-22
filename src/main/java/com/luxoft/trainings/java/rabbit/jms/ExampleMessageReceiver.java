package com.luxoft.trainings.java.rabbit.jms;

import static com.luxoft.trainings.java.rabbit.jms.JmsProvider.QUEUE_NAME;
import static com.luxoft.trainings.java.rabbit.jms.JmsProvider.getConnectionFactory;

import io.vavr.CheckedFunction0;
import io.vavr.CheckedFunction2;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import lombok.SneakyThrows;
import lombok.experimental.ExtensionMethod;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtensionMethod(ScopedFunctions.class)
public class ExampleMessageReceiver implements MessageListener, AutoCloseable {

  Connection connection = CheckedFunction0.of(getConnectionFactory()::createConnection)
                              .unchecked()
                              .apply()
                              .applyChecked(Connection::start);

  {
    CheckedFunction2.of(connection::createSession).unchecked()
        .apply(false, Session.AUTO_ACKNOWLEDGE)
        .zip(session -> session.createQueue(QUEUE_NAME))
        .applyChecked(Session::createConsumer)
        .with(messageConsumer -> messageConsumer.setMessageListener(this));
  }

  @Override
  @SneakyThrows
  public void onMessage(Message message) {
    if (message instanceof TextMessage tm)
      log.info("Message received: {}, Thread: {}", tm.getText(), Thread.currentThread().getName());
  }

  @Override
  public void close() throws JMSException {
    connection.close();
  }
}
