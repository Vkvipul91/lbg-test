FROM openjdk:8
ADD target/lloydsatm-mvn-*.jar /lloyd.jar
ENTRYPOINT ["java", "-jar", "/lloyd.jar"]
EXPOSE 8080

