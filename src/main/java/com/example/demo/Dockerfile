FROM amazoncorretto:19.0.2-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} api/app.jar
ENTRYPOINT ["java","-jar","api/app.jar"]
