FROM maven:3.9.2-amazoncorretto-17
WORKDIR /app
COPY pom.xml .
COPY src src


COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
CMD /wait-for-it.sh postgres:5432 -- mvn clean package

RUN mvn clean package

FROM amazoncorretto:17
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]