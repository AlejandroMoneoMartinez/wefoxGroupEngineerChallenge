FROM amazoncorretto:11-alpine-jdk
MAINTAINER wefox.com
COPY paymentProcessor-0.0.1-SNAPSHOT.jar paymentProcessor-0.0.1-SNAPSHOT.jar
#COPY target/paymentProcessor-0.0.1-SNAPSHOT.jar paymentProcessor-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","paymentProcessor-0.0.1-SNAPSHOT.jar"]