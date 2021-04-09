FROM openjdk:11

EXPOSE 8080

WORKDIR /urlshortenerauthapp

COPY ./auth-service-executable.jar .

ENTRYPOINT [ "java", "-jar", "auth-service-executable.jar" ]