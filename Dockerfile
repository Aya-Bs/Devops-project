# Multi-stage build
FROM openjdk:17-jdk-alpine as builder
WORKDIR /app
COPY target/kaddem-0.0.1.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

# Final image
FROM openjdk:17-jdk-alpine
WORKDIR /app

# Create user and directories
RUN addgroup -S spring && adduser -S spring -G spring && \
    mkdir -p /app/logs && \
    chown -R spring:spring /app

# Copy layers
COPY --from=builder --chown=spring:spring /app/dependencies/ ./
COPY --from=builder --chown=spring:spring /app/spring-boot-loader/ ./
COPY --from=builder --chown=spring:spring /app/snapshot-dependencies/ ./
COPY --from=builder --chown=spring:spring /app/application/ ./

# Health check (will be overridden by compose)
HEALTHCHECK --interval=30s --timeout=10s --retries=3 \
    CMD curl -f http://localhost:8089/actuator/health || exit 1

USER spring:spring

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-Djava.security.egd=file:/dev/./urandom", "org.springframework.boot.loader.JarLauncher"]