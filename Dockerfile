FROM openjdk:17-jdk-alpine

COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew clean build

ENTRYPOINT [ "java", "-jar", "-Dspring.profiles.active=local", "/build/libs/app.jar" ]