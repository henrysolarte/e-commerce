# ETAPA 1: Compilacion y empaquetado del artefacto
FROM eclipse-temurin:25-jdk AS builder
WORKDIR /app

# Copiamos archivos del wrapper y definicion del proyecto
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x ./mvnw

# Descarga de dependencias en cache
RUN ./mvnw dependency:go-offline -B

# Copiamos el codigo fuente y empaquetamos el JAR excluyendo tests pesados de BD
COPY src/ src/
RUN ./mvnw clean package -DskipTests

# ETAPA 2: Imagen final ligera para ejecucion en Render
FROM eclipse-temurin:25-jre
WORKDIR /app

# Copiamos el binario compilado desde la etapa de builder
COPY --from=builder /app/target/*.jar app.jar

# Copiamos el script de conversion de DATABASE_URL
COPY convert-database-url.sh convert-database-url.sh
RUN chmod +x convert-database-url.sh

# Render asigna dinamicamente el puerto en la variable de entorno PORT
EXPOSE 8081

# Ejecutamos el script de conversion y luego la aplicacion con optimizacion de memoria
# Use '.' instead of 'source' for POSIX sh compatibility
ENTRYPOINT ["sh", "-c", ". ./convert-database-url.sh && java -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -jar app.jar"]
