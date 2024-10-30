FROM openjdk:11-jre-slim

ENV SPRING_PROFILES_ACTIVE=prod

ADD target/orderservice-1.0-SNAPSHOT.jar /app/orderservice.jar

EXPOSE 8081

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "/app.jar"]