#FROM maven:3.8.4-openjdk-17 AS build
#RUN mkdir -p /workspace
#WORKDIR /workspace
#COPY pom.xml /workspace
#COPY src /workspace/src
#RUN mvn -f pom.xml clean package -DskipTests
#
#FROM openjdk:17-alpine
#COPY --from=build /workspace/target/*.jar app.jar
#EXPOSE 9000
#ENTRYPOINT ["java","-jar","app.jar"]

FROM openjdk:17-alpine

VOLUME /tmp

WORKDIR /application
COPY /target/Spring-keycloak-0.0.1-SNAPSHOT.jar /application/build/libs/

ENV APP_OPTS="\
    -Xms512M -server -Xmx1024M -XX:+UseParallelGC -Djava.awt.headless=true \
    -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"

CMD "$JAVA_HOME/bin/java" $APP_OPTS \
-cp ./build/libs/Spring-keycloak-0.0.1-SNAPSHOT.jar \
org.springframework.boot.loader.JarLauncher
