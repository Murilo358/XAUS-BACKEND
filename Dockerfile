FROM eclipse-temurin:17.0.8.1_1-jdk-jammy
COPY . .

RUN sed -i 's/\r$//' mvnw

RUN /bin/sh mvnw dependency:resolve

RUN /bin/sh mvnw clean install

ENTRYPOINT ["java","-jar","target/XAUS-0.0.1-SNAPSHOT.jar"]