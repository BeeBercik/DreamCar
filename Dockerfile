FROM openjdk:22

WORKDIR app

COPY . .

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/DreamCar-0.0.1-SNAPSHOT.jar"]