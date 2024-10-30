FROM openjdk:11-jre-slim

ENV SPRING_PROFILES_ACTIVE=prod

ADD target/productservice-1.0-SNAPSHOT.jar /app/productservice.jar

EXPOSE 8082

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "/app.jar"]