FROM eclipse-temurin:17.0.8.1_1-jdk-jammy

WORKDIR /app

COPY mvnw pom.xml ./
COPY .mvn .mvn

RUN sed -i 's/\r$//' mvnw

RUN ./mvnw dependency:resolve

COPY src ./src

RUN ./mvnw clean package -DskipTests

ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -jar target/XAUS-0.0.1-SNAPSHOT.jar --server.port=$PORT"]
