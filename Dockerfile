FROM openjdk:17
EXPOSE  8089
ADD target/kaddem-0.0.1.jar kaddem.jar
ENTRYPOINT ["java","-jar","kaddem.jar"]