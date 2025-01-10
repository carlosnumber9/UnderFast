FROM maven:3.8.7-openjdk-17 AS backend
RUN apt-get update && apt-get install -y curl

FROM node:18 AS frontend
RUN apt-get update && apt-get install -y git

FROM ubuntu:20.04
COPY --from=backend /usr/share/maven /usr/share/maven
COPY --from=frontend /usr/local /usr/local

# Configuración adicional
RUN apt-get update && apt-get install -y \
    openjdk-17-jdk \
    build-essential