package pubsubdemo.pubsubdemo;

import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = "pubSubOrderChannel")
public interface PublishOrderOutboundGateway {
    void publishToPackageServiceAndNotiService(String text);
}
