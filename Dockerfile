FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests
EXPOSE 3000
ENTRYPOINT ["java", "-jar", "target/frontend.jar"]
