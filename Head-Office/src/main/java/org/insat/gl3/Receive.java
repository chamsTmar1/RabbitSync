package org.insat.gl3;

import com.rabbitmq.client.*;

import java.io.IOException;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

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
                receivedMessage = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + receivedMessage + "'");
                // Send acknowledgment to the sender
                sendAcknowledgment(channel, delivery.getEnvelope().getDeliveryTag());
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
}
