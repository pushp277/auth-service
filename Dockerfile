# ==========================================
# 1. BUILDER STAGE
# ==========================================
FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /build

# Copy Maven descriptor to cache dependencies
COPY pom.xml .

# Download dependencies in a separate layer
RUN mvn dependency:go-offline -B

# Copy source code and build the executable JAR
COPY src ./src
RUN mvn clean package -DskipTests

# ==========================================
# 2. RUNNER STAGE
# ==========================================
FROM eclipse-temurin:21-jre-alpine AS runner

WORKDIR /app

# Run as a non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy the built JAR from the builder stage
COPY --from=builder /build/target/*.jar app.jar

EXPOSE 8003

# Flexible JVM settings
ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]