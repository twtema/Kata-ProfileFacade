FROM docker.io/eclipse-temurin:17.0.7_7-jre

WORKDIR /app
COPY target/ProfileFacade.jar /app/ProfileFacade.jar
EXPOSE 8087
CMD ["java", "-jar", "ProfileFacade.jar"]