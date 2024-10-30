FROM openjdk:11-jre-slim

ENV SPRING_PROFILES_ACTIVE=prod

ADD target/registryserver-1.0-SNAPSHOT.jar /app/registryserver.jar

EXPOSE 8761

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "/app.jar"]