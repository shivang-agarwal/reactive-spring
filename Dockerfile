FROM openjdk:8
ADD target/reactive-spring-1.0.jar reactive-spring-1.0.jar
EXPOSE 9988
ENTRYPOINT ["java","-jar","reactive-spring-1.0.jar"]

