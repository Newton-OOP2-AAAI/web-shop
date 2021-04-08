FROM amazoncorretto:11-alpine-jdk
COPY /target/web-shop-0.0.1-SNAPSHOT.jar webshop.jar
ENTRYPOINT ["java", "-jar", "/webshop.jar"]