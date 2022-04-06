FROM maven:3.8-openjdk-8 AS build
ADD . /src
RUN mvn -f /src/pom.xml clean package

FROM openjdk:8

COPY --from=build /src/target/pet_feeder-0.0.1-SNAPSHOT.jar /app/
COPY --from=build /src/tables.sql /app/

CMD  ["java", "-jar","/app/pet_feeder-0.0.1-SNAPSHOT.jar"]