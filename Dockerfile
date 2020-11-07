FROM maven:3.6.3-adoptopenjdk-8 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean install

FROM adoptopenjdk:8-jre-hotspot  
COPY --from=build /home/app/target/traffic-accident-analysis-calculation-tool.jar /usr/local/lib/traffic-accident-analysis-calculation-tool.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/traffic-accident-analysis-calculation-tool.jar"]