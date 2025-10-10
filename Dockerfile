# Stage 1: Build the application
FROM maven:3.9.3-amazoncorretto-17 AS build
WORKDIR /app

# First copy only the POM file to leverage Docker cache
COPY pom.xml .
# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src/ /app/src/

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Create a non-root user
RUN useradd -m myuser \
    && chown -R myuser:myuser /app

# Switch to non-root user
USER myuser

# Copy the JAR file from the build stage
COPY --from=build --chown=myuser:myuser /app/target/*.jar app.jar

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=prod

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
