FROM adoptopenjdk/openjdk11:latest
RUN mkdir -p /app
ADD target/registryserver-1.0-SNAPSHOT.jar /app/registryserver.jar
CMD java -Dspring.profiles.active=prod2 -Dserver.port=$PORT $JAVA_OPTS -jar /app/registryserver.jar