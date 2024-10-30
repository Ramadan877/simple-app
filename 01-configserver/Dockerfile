FROM openjdk:11-jre-slim

ENV SPRING_PROFILES_ACTIVE=prod

ADD target/configserver-1.0-SNAPSHOT.jar /app/configserver.jar



RUN mkdir -p /app
CMD java -Dspring.profiles.active=prod2 -Dserver.port=$PORT $JAVA_OPTS -jar /app/registryserver.jar