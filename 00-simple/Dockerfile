FROM adoptopenjdk/openjdk11:latest
RUN mkdir -p /app
ADD target/simple-1.0-SNAPSHOT.jar /app/simple.jar
CMD java -Dserver.port=$PORT $JAVA_OPTS -jar /app/simple.jar