FROM openjdk:17-jdk-alpine

COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew clean build

ARG SERVER_PATH
ARG SERVER_PORT
ARG SERVER_PROFILE

ENTRYPOINT exec java -jar -Dspring.profiles.active=${SERVER_PROFILE} /${SERVER_PATH}/build/libs/app.jar
EXPOSE $SERVER_PORT