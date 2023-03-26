FROM openjdk:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} eventservice.jar
ENTRYPOINT ["java", "-jar", "eventservice.jar"]