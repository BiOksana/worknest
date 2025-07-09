FROM maven:3.9.9 AS build
WORKDIR /app
COPY . .
RUN mvn package

FROM openjdk:23-jdk-slim
ENV SPRING_PROFILES_ACTIVE=dev
COPY target/workNest-0.0.1-SNAPSHOT.jar workNest.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/workNest.jar"]

ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

