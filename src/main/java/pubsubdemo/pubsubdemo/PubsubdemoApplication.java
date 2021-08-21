package pubsubdemo.pubsubdemo;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageChannel;

import javax.websocket.MessageHandler;

@SpringBootApplication
public class PubsubdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PubsubdemoApplication.class, args);
    }
}
