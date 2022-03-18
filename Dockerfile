FROM microblink/java:17
COPY "build/libs/Salon-0.0.1-SNAPSHOT.jar" salon.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=gcp -Djava.security.egd=file:/dev/./urandom -jar /salon.jar"]
