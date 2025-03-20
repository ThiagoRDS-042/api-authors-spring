FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN apt-get install maven -y
RUN mvn clean install

FROM openjdk:17-jdk-slim
EXPOSE 8080

ARG DB_URL
ARG DB_USER
ARG DB_PASS

ENV DATABASE_URL=${DB_URL}
ENV DATABASE_USERNAME=${DB_USER}
ENV DATABASE_PASSWORD=${DB_PASS}

COPY --from=build /target/api_authors-0.0.1-SNAPSHOT.jar app.jar

CMD  java -jar -D spring.datasource.url=${DATABASE_URL} -D spring.datasource.username=${DATABASE_USERNAME}-D spring.datasource.password=${DATABASE_PASSWORD} app.jar