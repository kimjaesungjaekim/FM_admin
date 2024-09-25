FROM amazoncorretto:17-al2023-headless
WORKDIR /app
COPY build/libs/fillme-backend-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]
