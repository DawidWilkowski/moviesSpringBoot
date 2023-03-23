FROM maven:3.6.0-jdk-17-slim AS build
COPY src 
COPY pom.xml 
RUN mvn -f pom.xml clean package

#
# Package stage
#

FROM openjdk:17-alpine
COPY --from=build /target/movies-0.0.1-SNAPSHOT.jar /usr/local/lib/demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]