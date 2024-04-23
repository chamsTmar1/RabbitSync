package org.insat.gl3;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Send {
    private static final String QUEUE_NAME = "migration_queue";

    public static void sendMigrationScript(String migrationScript) throws IOException, TimeoutException {
        // Create a connection to the RabbitMQ server
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); // RabbitMQ server address
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // Convert the migration script to bytes
            byte[] messageBytes = migrationScript.getBytes(StandardCharsets.UTF_8);

            // Publish the migration script to the queue
            channel.basicPublish("", QUEUE_NAME, null, messageBytes);
            System.out.println(" [x] Sent migration script to the queue '" + QUEUE_NAME + "'");
        }
    }
}
