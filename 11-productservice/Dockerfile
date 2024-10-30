FROM adoptopenjdk/openjdk11:latest
RUN mkdir -p /app
ADD target/productservice-1.0-SNAPSHOT.jar /app/productservice.jar
CMD java -Dspring.profiles.active=solo -Dserver.port=$PORT $JAVA_OPTS -jar /app/productservice.jar