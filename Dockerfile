FROM alpine:3.19
LABEL maintainer="https://github.com/6SOATGP54"

WORKDIR /home

# updates source list
RUN apk update

# install required tools
RUN apk add --no-cache git bash openjdk17-jdk maven

# Project's setup
COPY . /home/tech-challenge
WORKDIR /home/tech-challenge

# Start Project
RUN mvn -DskipTests install
EXPOSE 8091

CMD ["java", "-jar", "\home\tech-challenge\target\soat-food-0.0.1-SNAPSHOT.jar","&"]