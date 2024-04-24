package org.insat.gl3;

import com.rabbitmq.client.*;

import java.io.IOException;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.insat.gl3.Synchronization.executeMigrationScript;

public class Receive {

    private static final String QUEUE_NAME = "migration_script_queue";
    private String receivedMessage;

    public Receive() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            // Set up a consumer to receive messages
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                try {
                    receivedMessage = new String(delivery.getBody(), "UTF-8");
                    System.out.println(" [x] Received '" + receivedMessage + "'");

                    // Log received migration script
                    logReceivedMigrationScript(receivedMessage);

                    // Execute migration script only if it's not empty or null
                    if (receivedMessage != null && !receivedMessage.isEmpty()) {
                        executeMigrationScript(receivedMessage);

                        // Acknowledge the message if the migration script was executed successfully
                        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                    } else {
                        // Handle case where received message is empty or null
                        System.out.println("Received migration script is empty or null.");
                    }
                } catch (Exception e) {
                    // Log and handle any exceptions
                    e.printStackTrace();
                    // Reject the message and request requeue (optional)
                    channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, true);
                }
            };



            // Start consuming messages from the queue
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
            });
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public String getReceivedMessage() {
        return receivedMessage;
    }

    private void sendAcknowledgment(Channel channel, long deliveryTag) throws IOException {
        // Send acknowledgment message to the sender
        String acknowledgmentMessage = "Received and processed message with delivery tag: " + deliveryTag;
        channel.basicPublish("", "acknowledgment_queue", null, acknowledgmentMessage.getBytes("UTF-8"));
        System.out.println(" [x] Sent acknowledgment: '" + acknowledgmentMessage + "'");
    }

    private void logReceivedMigrationScript(String migrationScript) {
        // Log received migration script
        System.out.println("Received migration script: " + migrationScript);
    }
}
