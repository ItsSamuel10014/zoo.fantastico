FROM openjdk:21-slim
WORKDIR /app
COPY target/zoofantastico-0.0.1-SNAPSHOT.jar zoo-fantastic-app.jar
EXPOSE 8080
CMD ["java", "-jar", "zoo-fantastic-app.jar"]