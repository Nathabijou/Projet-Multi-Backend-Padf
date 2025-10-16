# Stage 1: Build
FROM maven:3.9.3-eclipse-temurin-17 AS builder

# Set working directory
WORKDIR /app

# Copy Maven settings and config
COPY mvn-settings.xml /root/.m2/settings.xml

# Create .mvn directory and copy jvm.config if it exists
RUN mkdir -p /app/.mvn && \
    if [ -f .mvn/jvm.config ]; then \
        cp .mvn/jvm.config /app/.mvn/; \
    else \
        echo "-Djdk.module.illegalAccess=permit" > /app/.mvn/jvm.config; \
        echo "-Djdk.reflect.useDirectMethodHandle=false" >> /app/.mvn/jvm.config; \
        echo "-Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn" >> /app/.mvn/jvm.config; \
    fi

# Copy only the POM file first to leverage Docker cache
COPY pom.xml .

# Install Lombok and other annotation processors first
RUN mvn -B dependency:go-offline \
    -Dmaven.repo.local=/root/.m2/repository \
    -Dmaven.artifact.threads=20 \
    -Dmaven.test.skip=true \
    -Dmaven.compile.fork=true \
    -T 1C \
    process-test-classes

# Download all dependencies
RUN mvn -B dependency:go-offline \
    -Dmaven.repo.local=/root/.m2/repository \
    -Dmaven.artifact.threads=20 \
    -Dmaven.test.skip=true \
    -Dmaven.compile.fork=true \
    -T 1C

# Copy source code
COPY src/ src/

# Build the application with optimized Maven settings
RUN mvn -B clean package \
    -Dmaven.repo.local=/root/.m2/repository \
    -DskipTests \
    -T 1C \
    -Dmaven.test.skip=true \
    -Dmaven.compile.fork=true \
    -Dmaven.javadoc.skip=true \
    -Dmaven.source.skip=true \
    -Dmaven.artifact.threads=20 \
    -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn \
    -Dorg.gradle.daemon=false \
    -Dorg.gradle.parallel=true \
    -Dorg.gradle.caching=true \
    -Dorg.gradle.configureondemand=true

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-jammy

# Set working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=30s --start-period=60s --retries=3 \
  CMD wget --spider http://localhost:8080/actuator/health || exit 1

# Run the application with optimized JVM settings
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-XX:+UseG1GC", "-XX:+OptimizeStringConcat", "-XX:+UseStringDeduplication", "-Djava.security.egd=file:/dev/./urandom", "-Dserver.port=8080", "-jar", "app.jar", "--spring.profiles.active=prod"]
