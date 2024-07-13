# Start with a base image containing Java 8
FROM openjdk:8-jdk-alpine

# Add Maintainer Info
LABEL maintainer="sw5614@paran.com"

# Set environment variables
ENV APP_HOME /app
ENV APP_NAME board
ENV JAVA_OPTS=""

# Create application directory
RUN mkdir -p $APP_HOME

# Set the working directory
WORKDIR $APP_HOME

# The application's jar file
ARG JAR_FILE=build/libs/board-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
COPY ${JAR_FILE} ${APP_HOME}/board.jar

# Expose the port
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "board.jar"]
