# Etap 1: Enstale depandans yo
FROM maven:3.9.3-amazoncorretto-17 AS build
WORKDIR /app

# Kopye fich pom.xml an premye
COPY pom.xml .

# Telechaje tout depandans yo nan yon kach
RUN mvn dependency:go-offline -B

# Kopye kòd sous la
COPY src/ src/

# Konstwi aplikasyon an
RUN mvn clean package -DskipTests

# Etap 2: Kreye imaj final la
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Kopye fich JAR la
COPY --from=build /app/target/*.jar app.jar

# Kouri aplikasyon an
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
