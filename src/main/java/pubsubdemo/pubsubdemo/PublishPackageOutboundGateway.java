package pubsubdemo.pubsubdemo;

import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = "publishPackageChannel")
public interface PublishPackageOutboundGateway {
    void publishToShippingService(String text);
}
