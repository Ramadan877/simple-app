FROM openjdk:11-jre-slim

ENV SPRING_PROFILES_ACTIVE=prod

ADD target/configserver-1.0-SNAPSHOT.jar /app/configserver.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "/app.jar"]