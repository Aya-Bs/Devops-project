# Multi-stage build for smaller final image
FROM openjdk:17-jdk-alpine as builder
WORKDIR /app
COPY target/kaddem-0.0.1.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

# Final image
FROM openjdk:17-jdk-alpine
WORKDIR /app

# Create non-root user
RUN addgroup -S spring && adduser -S spring -G spring

# Copy application layers from builder
COPY --from=builder /app/dependencies/ ./
COPY --from=builder /app/spring-boot-loader/ ./
COPY --from=builder /app/snapshot-dependencies/ ./
COPY --from=builder /app/application/ ./

# Create logs directory with proper permissions
RUN mkdir -p /app/logs && \
    chown -R spring:spring /app && \
    chmod -R 755 /app/logs

# Health check
HEALTHCHECK --interval=30s --timeout=5s --retries=3 \
    CMD curl -f http://localhost:8089/kaddem/actuator/health || exit 1

USER spring:spring

# Optimized JVM launch
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-Djava.security.egd=file:/dev/./urandom", "org.springframework.boot.loader.JarLauncher"]