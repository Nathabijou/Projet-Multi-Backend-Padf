FROM maven:3.9.3-amazoncorretto-17 AS builder

# Set working directory
WORKDIR /app

# Copy Maven settings
COPY mvn-settings.xml /root/.m2/settings.xml

# Copy only the POM file first to leverage Docker cache
COPY pom.xml .

# Download dependencies with parallel downloads and optimized settings
RUN --mount=type=cache,id=railway-m2-cache,target=/root/.m2 \
    mvn -B dependency:go-offline \
    -Dmaven.artifact.threads=20 \
    -Dmaven.test.skip=true \
    -Dmaven.compile.fork=true \
    -T 1C

# Copy source code
COPY src/ src/

# Build the application with optimized Maven settings
RUN --mount=type=cache,id=railway-m2-cache,target=/root/.m2 \
    mvn -B clean package \
    -DskipTests \
    -T 1C \
    -Dmaven.test.skip=true \
    -Dmaven.compile.fork=true \
    -Dmaven.javadoc.skip=true \
    -Dmaven.source.skip=true \
    -Dmaven.artifact.threads=20 \
    -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-jammy

# Set working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the application with optimized JVM settings
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-XX:+UseG1GC", "-XX:+OptimizeStringConcat", "-XX:+UseStringDeduplication", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar", "--spring.profiles.active=prod"]
