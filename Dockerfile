FROM openjdk:11
ADD target/pubsubdemo-0.0.1-SNAPSHOT.jar pubsubdemo-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar", "pubsubdemo-0.0.1-SNAPSHOT.jar"]
