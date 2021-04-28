FROM maven:3.8.1-jdk-11 AS MAVEN_BUILD
COPY pom.xml .
COPY src src

RUN mvn package -DskipTests

FROM openjdk:11
COPY --from=MAVEN_BUILD /target/web-shop-0.0.1-SNAPSHOT.jar webshop.jar
ENTRYPOINT ["java", "-jar", "/webshop.jar"]