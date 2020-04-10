FROM openjdk:11-jdk-slim as build
MAINTAINER emrecaglan
RUN mkdir -p /bwt-test/
ADD ./build/libs/task-0.0.1-SNAPSHOT.jar ./bwt-test/task-0.0.1.jar
ENTRYPOINT ["java", "-jar", "/bwt-test/task-0.0.1.jar"]
HEALTHCHECK --start-period=3m CMD curl --silent --write-out "HTTPSTATUS:%{http_code}" \
http://localhost:8081/actuator/health | sed -e 's/HTTPSTATUS\:.*//g' | grep '{"status":"UP"}' || exit 1