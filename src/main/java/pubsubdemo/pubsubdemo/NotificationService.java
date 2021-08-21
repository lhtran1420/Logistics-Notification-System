package pubsubdemo.pubsubdemo;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.AckMode;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationService {
    @Autowired
    private PublishNotificationOutboundGateway messageGateway;
    String message;

    @GetMapping("notification")
    public String getMessage() {
        messageGateway.publishToUserService(message);
        return "hello notification service with order ID" + message;
    }

    @Bean
    @ServiceActivator(inputChannel = "pubSubNotificationChannel")
    public MessageHandler messageNotificationSender(PubSubTemplate pubsubTemplate) {
        return new PubSubMessageHandler(pubsubTemplate, "NotificationTopic");
    }

    @Bean
    public PubSubInboundChannelAdapter notificationAdapter(
            @Qualifier("notificationInputChannel") MessageChannel inputChannel,
            PubSubTemplate template) {
        PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(template,"OrderTopicNotificationSub");
        adapter.setOutputChannel(inputChannel);
        adapter.setAckMode(AckMode.AUTO);
        return adapter;
    }

    @Bean
    MessageChannel notificationInputChannel() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "notificationInputChannel")
    public void receiveMessage(String payload) {
        this.message = payload;
    }
}
