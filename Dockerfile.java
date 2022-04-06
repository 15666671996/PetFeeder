FROM node:14 AS webapp-build
ADD . /src
RUN npm --prefix /src/webapp/ install
RUN npm --prefix /src/webapp/ run build

FROM maven:3.8-openjdk-8 AS build
ADD . /src
COPY --from=webapp-build /src/webapp/dist/ /src/src/main/resources/static/
RUN mvn -f /src/pom.xml clean package

FROM openjdk:8

COPY --from=build /src/target/pet_feeder-0.0.1-SNAPSHOT.jar /app/
COPY --from=build /src/tables.sql /app/

CMD  ["java", "-jar","/app/pet_feeder-0.0.1-SNAPSHOT.jar"]