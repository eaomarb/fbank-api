FROM eclipse-temurin:21
ARG JAR_FILE=target/*.jar
COPY ./target/fbank-api-1.0.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
