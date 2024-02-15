FROM maven:3.9.2-amazoncorretto-17 as build
COPY pom.xml .
COPY src src

RUN mvn clean package -PexcludeIntegTests

FROM openjdk:17
COPY --from=build target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]