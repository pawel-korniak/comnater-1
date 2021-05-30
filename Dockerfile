# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY . /
RUN mvn -f /pom.xml clean package -Dmaven.test.skip

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build /target/comnater-0.0.1.jar /target/comnater-0.0.1.jar
ENTRYPOINT ["java","-jar","/target/comnater-0.0.1.jar"]

