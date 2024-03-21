FROM openjdk:21

COPY target/*.jar cart-service.jar
ENTRYPOINT ["java", "-jar", "cart-service.jar"]