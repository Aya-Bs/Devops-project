#FROM openjdk:17-jdk-alpine
#EXPOSE 8089
#ADD target/Kaddem-0.0.1.jar kaddem.jar
#ENTRYPOINT ["java","-jar","/kaddem.jar"]

FROM openjdk:17-jdk-alpine
EXPOSE 8089
ADD target/*.jar kaddem.jar
ENTRYPOINT ["java", "-jar", "/kaddem.jar"]
