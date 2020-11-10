FROM adoptopenjdk:8-jre-hotspot  
WORKDIR /app
COPY /target/gonzo.jar gonzo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","gonzo.jar"]