# Etapa 1: Construir a aplicação usando Maven 4.0.0 (caso tenha uma imagem específica)
FROM maven:3.9.4-eclipse-temurin-21 AS build
LABEL authors="Kevin-Borba"
LABEL description = "This is the Dockerfile for the projeto-tarefa service"
# Definir o diretório de trabalho
WORKDIR /app

# Copiar o pom.xml e baixar as dependências (para aproveitar o cache do Docker)
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./

RUN chmod +x mvnw
RUN mvn dependency:go-offline -B

# Copiar o código fonte do projeto
COPY src src

# Construir o aplicativo
RUN mvn clean package -DskipTests

# Etapa 2: Criar a imagem para rodar a aplicação com Java
FROM eclipse-temurin:21-jre 

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o arquivo JAR da etapa de construção
#COPY --from=build /app/target/projeto-tarefa-0.0.1-SNAPSHOT.jar /app/projeto-tarefa.jar
# Copy the built jar from the build stage R
COPY --from=build /app/target/projeto-tarefa-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080
EXPOSE 8080

# Comando para rodar a aplicação
#ENTRYPOINT ["java", "-jar", "/app/projeto-tarefa.jar"]

# Define the entrypoint
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
