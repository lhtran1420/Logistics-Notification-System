package pubsubdemo.pubsubdemo;

import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = "pubSubNotificationChannel")
public interface PublishNotificationOutboundGateway {
    void publishToUserService(String text);
}
