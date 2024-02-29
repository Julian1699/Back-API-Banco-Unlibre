FROM openjdk:17-jdk-alpine
COPY target/bank-0.0.1-SNAPSHOT.jar api-springboot.jar
ENTRYPOINT ["java","-jar","api-springboot.jar"]