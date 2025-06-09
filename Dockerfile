# ---------- Build Stage ----------
FROM openjdk:17-jdk-slim AS build
WORKDIR /app

COPY . .
RUN chmod +x ./gradlew && ./gradlew build

# ---------- Runtime Stage ----------
FROM openjdk:17-jdk-slim AS runtime
WORKDIR /app

COPY --from=build /app/build/libs/*.jar /app/iltuo.jar

EXPOSE 4000
CMD ["java", "-jar", "/app/iltuo.jar"]
