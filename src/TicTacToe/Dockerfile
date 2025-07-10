FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY build/libs/*.jar tictactoe.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "tictactoe.jar"]