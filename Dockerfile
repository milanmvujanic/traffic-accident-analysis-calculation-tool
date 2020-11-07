FROM maven:3.6.3-adoptopenjdk-8 AS build
COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean install

FROM adoptopenjdk:8-jre-hotspot  
COPY --from=build /app/target/traffic-accident-analysis-calculation-tool.jar /app/traffic-accident-analysis-calculation-tool.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/traffic-accident-analysis-calculation-tool.jar"]