package pubsubdemo.pubsubdemo;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderService {
    @Autowired
    private PublishOrderOutboundGateway orderOutboundGateway;

    @Bean
    @ServiceActivator(inputChannel = "pubSubOrderChannel")
    public MessageHandler messageOrderSender(PubSubTemplate pubsubTemplate) {
        return new PubSubMessageHandler(pubsubTemplate, "OrderTopic");
    }

    @PostMapping("/orderNotification")
    public String publishToPackageService(@RequestBody String message) {
        orderOutboundGateway.publishToPackageServiceAndNotiService(message);
        return message;
    }
}
