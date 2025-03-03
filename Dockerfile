FROM maven:latest as build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:21-slim
RUN apt-get update && apt-get install -y libopencv-dev

WORKDIR /opt/app
# Verifique se o nome do JAR gerado bate com "events-*.jar"
COPY --from=build /app/target/events-*.jar ./events.jar

EXPOSE 8080

# Em sistemas Debian/Ubuntu, as bibliotecas nativas do OpenCV geralmente ficam em /usr/lib/x86_64-linux-gnu
CMD ["java", "-Djava.library.path=/usr/lib/x86_64-linux-gnu", "-jar", "events.jar"]
