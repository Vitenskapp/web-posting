# Etapa 1: Build da aplicação
FROM ubuntu:22.04 AS build

RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

# Etapa 2: Criar a imagem final com apenas o JAR
FROM openjdk:17-jdk-slim

WORKDIR /app
EXPOSE 8080

# Copia o JAR gerado na fase de build
COPY --from=build /app/target/web-posting-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
