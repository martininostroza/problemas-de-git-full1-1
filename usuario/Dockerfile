# ==========================================
# ETAPA 1: Compilación y empaquetado (Build)
# ==========================================
FROM maven:3.9.6-eclipse-temurin-21-jammy AS builder

WORKDIR /build

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

# ==========================================
# ETAPA 2: Entorno de ejecución (Runtime)
# ==========================================
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Usamos el puerto 8089 para usuario
ENV PORT=8089
ENV SPRING_PROFILES_ACTIVE=prod

# Ajustado especialmente para tu microservicio de Usuario
COPY --from=builder /build/target/usuario-0.0.1-SNAPSHOT.jar app.jar

EXPOSE ${PORT}

ENTRYPOINT ["java", "-server", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]