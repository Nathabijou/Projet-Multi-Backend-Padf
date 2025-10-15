# Etap 1: Konstwi aplikasyon an
FROM maven:3.9.3-amazoncorretto-17 AS build
WORKDIR /app

# Kopye premye fichye POM la pou itilize kach
COPY pom.xml .

# Enstale tout depandans yo
RUN mvn dependency:go-offline -B

# Kopye tout kòd sous la
COPY src/ /app/src/

# Konstwi aplikasyon an
RUN mvn clean package -DskipTests

# Etap 2: Kreye imaj final la
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Kreye yon itilizatè ki pa root
RUN useradd -m myuser \
    && chown -R myuser:myuser /app
USER myuser

# Copy the JAR file from the build stage
COPY --from=build --chown=myuser:myuser /app/target/*.jar app.jar

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=prod

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
