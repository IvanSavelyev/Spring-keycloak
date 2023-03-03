FROM maven:3.8.4-openjdk-17 AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
COPY src /workspace/src
RUN mvn -f pom.xml clean package -DskipTests

FROM openjdk:17-alpine
COPY --from=build /workspace/target/*.jar app.jar

ENV APP_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
EXPOSE 9000

CMD "$JAVA_HOME/bin/java" $APP_OPTS \
-cp app.jar \
org.springframework.boot.loader.JarLauncher
