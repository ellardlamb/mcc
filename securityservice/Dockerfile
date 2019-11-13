FROM openjdk:8-jdk-alpine

RUN apk add gradle

WORKDIR /root/securityservice

COPY . /root/securityservice

EXPOSE 8081

ENTRYPOINT ["gradle","bootRun"]
