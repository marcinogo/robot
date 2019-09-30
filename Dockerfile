FROM openjdk:11-jre-slim
VOLUME /tmp
ARG JAR_FILE=target/robot-0.1.jar

ENV DB_USER "DEFAULT_DB_USER"
ENV DB_PASSWORD "DEFAULT_DB_PASSWORD"
ENV DB_URL "DEFAULT_DB_URL"

COPY ${JAR_FILE} robot-app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/robot-app.jar"]