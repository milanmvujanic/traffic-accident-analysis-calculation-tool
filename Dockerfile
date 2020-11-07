FROM adoptopenjdk:8-jre-hotspot    
WORKDIR /app
COPY target/traffic-accident-analysis-calculation-tool.jar traffic-accident-analysis-calculation-tool.jar