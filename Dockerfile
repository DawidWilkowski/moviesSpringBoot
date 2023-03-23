# Build stage
FROM maven:3.9.0-eclipse-temurin-17 AS build
COPY pom.xml /app/
COPY src /app/src
RUN mvn -f /app/pom.xml clean package

# Run stage
FROM openjdk:17
COPY --from=build /usr/src/app/target/movies-0.0.1-SNAPSHOT.jar /usr/app/movies-0.0.1-SNAPSHOT.jar  
EXPOSE 8080  
ENTRYPOINT ["java","-jar","/usr/app/movies-0.0.1-SNAPSHOT.jar"]  