
In this project, I've structured the system around three main topics: the order topic, package topic, and notification topic. Here's a breakdown of how they interact:

Order Topic: This is where messages regarding orders are initiated. When a user places an order, the order service publishes messages to both the notification and package services.
Package Topic: This topic handles messages related to packaging and shipping. When the package service receives messages from the order service, it relays these messages to the shipping service. This allows shippers to know which orders they need to fulfill.
Notification Topic: The notification service is responsible for informing users about the status of their orders. It subscribes to messages from the order service, and upon receiving them, it publishes messages to the user service, notifying users that their order has been successfully placed.
By setting up these subscriptions and message flows, the system ensures efficient communication between different components, facilitating the order process seamlessly from initiation to delivery.
