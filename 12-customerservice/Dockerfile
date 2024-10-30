FROM openjdk:11-jre-slim

ENV SPRING_PROFILES_ACTIVE=prod

ADD target/customerservice-1.0-SNAPSHOT.jar /app/customerservice.jar

EXPOSE 8083

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "/app.jar"]