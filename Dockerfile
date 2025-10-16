# Etap 1: Konstriksyon
FROM maven:3.9.3-eclipse-temurin-17 AS builder

# Mete kèk enfòmasyon sou konstriksyon an
LABEL maintainer="Dev Team"

# Kreye direktè travay la
WORKDIR /app

# Kopye tout dosye yo
COPY . .

# Enstale tout dependans yo
RUN mvn clean package -DskipTests

# ---
# Etap 2: Ekzekisyon
FROM eclipse-temurin:17-jre-jammy

# Kreye yon itilizateur ki pa root
RUN addgroup --system javauser && adduser --system --group javauser

# Kreye yon direktè pou aplikasyon an
WORKDIR /app

# Kopye fichye JAR la soti nan etap konstriksyon an
COPY --from=builder --chown=javauser:javauser /app/target/*.jar app.jar

# Fè itilizatè a posede dosye yo
RUN chown -R javauser:javauser /app

# Chanje pou itilizatè ki pa root
USER javauser

# Ekspoze pò 8080 (default)
EXPOSE 8080

# Health check ak curl
HEALTHCHECK --interval=30s --timeout=30s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Kòmand pou kòmanse aplikasyon an
ENTRYPOINT ["sh", "-c", "java -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -jar /app/app.jar --server.port=${PORT:-8080} --spring.profiles.active=prod"]
