# Etap 1: Enstale depandans yo
FROM maven:3.9.3-amazoncorretto-17 AS deps
WORKDIR /app
COPY pom.xml .
# Telechaje tout depandans yo nan yon kach
RUN mvn dependency:go-offline -B

# Etap 2: Konstwi aplikasyon an
FROM maven:3.9.3-amazoncorretto-17 AS build
WORKDIR /app
# Kopye fich pom.xml ak depandans yo
COPY --from=deps /root/.m2 /root/.m2
COPY pom.xml .
# Kopye kòd sous la
COPY src/ src/
# Enstale depandans yo
RUN mvn dependency:resolve
# Konpile kòd la
RUN mvn compile
# Konstwi aplikasyon an
RUN mvn package -DskipTests

# Etap 3: Kreye imaj final la
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
# Kopye fich JAR la
COPY --from=build /app/target/*.jar app.jar

# Kouri aplikasyon an
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
