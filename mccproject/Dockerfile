FROM openjdk:8-jdk-alpine

VOLUME /tmp

#RUN apk update
RUN apk add nodejs npm

ARG JAR_FILE=build/libs/mccproject-1.0.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
#ENTRYPOINT ["npm","start","/root/prj-cust-client-crud"]
