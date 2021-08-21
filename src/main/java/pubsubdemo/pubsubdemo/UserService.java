package pubsubdemo.pubsubdemo;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.AckMode;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserService {
    String message;

    @GetMapping("userNotification")
    public String getMessage() {
        return "hello user service with order ID " + message;
    }

    @Bean
    public PubSubInboundChannelAdapter userAdapter(
            @Qualifier("userInputChannels") MessageChannel inputChannel,
            PubSubTemplate template) {
        PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(template,"NotificationTopicUserSub");
        adapter.setOutputChannel(inputChannel);
        adapter.setAckMode(AckMode.AUTO);
        return adapter;
    }

    @Bean
    MessageChannel userInputChannels() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "userInputChannels")
    public void receiveMessage(String payload) {
        this.message = payload;
    }
}
