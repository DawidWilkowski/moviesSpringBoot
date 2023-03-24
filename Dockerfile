# Build stage
FROM maven:3.8.1-openjdk-17-slim AS build
COPY src /usr/src/app/src  
COPY pom.xml /usr/src/app  
RUN mvn -f /usr/src/app/pom.xml clean package

# Run stage
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /usr/src/app/target/movies-0.0.1-SNAPSHOT.jar /usr/app/movies-0.0.1-SNAPSHOT.jar  
EXPOSE 8080  
ENTRYPOINT ["java","-jar","/usr/app/movies-0.0.1-SNAPSHOT.jar"]  
