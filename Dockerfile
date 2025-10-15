# Etap senp: Konstwi epi kouri aplikasyon an
FROM maven:3.9.3-amazoncorretto-17

# Kreye direktè travay la
WORKDIR /app

# Kopye fichye yo
COPY pom.xml .
COPY src/ src/

# Enstale depandans yo ak konstwi aplikasyon an
RUN mvn clean package -DskipTests

# Kouri aplikasyon an
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/*.jar", "--spring.profiles.active=prod"]
