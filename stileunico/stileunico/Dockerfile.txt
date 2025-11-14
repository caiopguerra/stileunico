# Use uma imagem oficial do Java
FROM openjdk:17-jdk-slim

# Diretório da aplicação dentro do container
WORKDIR /app

# Copiar todos os arquivos do projeto
COPY stileunico/stileunico/ .

# Dar permissão para o Maven Wrapper
RUN chmod +x mvnw

# Build da aplicação (gera o JAR)
RUN ./mvnw clean package -DskipTests

# Expor a porta que o Spring Boot vai rodar
EXPOSE 8080

# Comando para iniciar a aplicação
CMD ["java", "-jar", "target/stileunico-0.0.1-SNAPSHOT.jar"]
