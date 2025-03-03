FROM openjdk:17-jdk-alpine
EXPOSE 8089
ADD target/Kaddem-0.0.1.jar Kaddem-0.0.1.jar
ENTRYPOINT ["java","-jar","/Kaddem-0.0.1.jar"]