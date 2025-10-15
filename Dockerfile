# Etap senp: Konstwi epi kouri aplikasyon an
FROM maven:3.9.3-amazoncorretto-17

# Kreye yon itilizatè ki pa root
RUN useradd -m myuser
WORKDIR /home/myuser/app

# Kopye fichye yo
COPY --chown=myuser:myuser pom.xml .
COPY --chown=myuser:myuser src/ src/

# Enstale depandans yo
RUN mvn dependency:go-offline -B

# Konstwi aplikasyon an
RUN mvn clean package -DskipTests

# Kouri aplikasyon an
USER myuser
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/*.jar", "--spring.profiles.active=prod"]
