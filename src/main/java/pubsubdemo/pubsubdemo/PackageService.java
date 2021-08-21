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
public class PackageService {
    @Autowired
    private PublishPackageOutboundGateway messagingGateway;
    String message;

    @GetMapping("packageNotification")
    public String getMessage() {
        messagingGateway.publishToShippingService(message);
        return "hello package service with order ID " + message;
    }

    @Bean
    @ServiceActivator(inputChannel = "publishPackageChannel")
    public MessageHandler messagePackageSender(PubSubTemplate pubsubTemplate) {
        return new PubSubMessageHandler(pubsubTemplate, "PackageTopic");
    }

    @Bean
    public PubSubInboundChannelAdapter packageAdapter(
            @Qualifier("packageInputChannel") MessageChannel inputChannel,
            PubSubTemplate template) {
        PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(template,"OrderTopicPackageSub");
        adapter.setOutputChannel(inputChannel);
        adapter.setAckMode(AckMode.AUTO);
        return adapter;
    }

    @Bean
    MessageChannel packageInputChannel() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "packageInputChannel")
    public void receiveMessage(String payload) {
        this.message = payload;
    }
}
