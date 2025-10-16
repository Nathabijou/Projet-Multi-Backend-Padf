# Build stage
FROM maven:3.9.3-eclipse-temurin-17 as builder

WORKDIR /build

# First copy only the POM file to leverage Docker cache
COPY pom.xml .

# Download all dependencies
RUN mvn dependency:go-offline

# Copy source code
COPY src/ src/

# Build the application
RUN mvn clean package -DskipTests

# Move the JAR file to a known name
RUN mv $(find /build/target -name '*.jar' -type f | head -n 1) /build/target/app.jar

# Final stage
FROM eclipse-temurin:17-jre-jammy

# Install curl for health checks
RUN apt-get update && \
    apt-get install -y curl iproute2 && \
    rm -rf /var/lib/apt/lists/*

# Create a non-root user
RUN addgroup --system javauser && adduser --system --group javauser

WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=builder /build/target/app.jar app.jar

# Set file permissions
RUN chown -R javauser:javauser /app

# Switch to non-root user
USER javauser

# Expose the port the app runs on
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=30s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:${PORT:-8080}/healthz || exit 1

# The application's entry point
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=${PORT:-8080}", "--spring.profiles.active=prod"]
