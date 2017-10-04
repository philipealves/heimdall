FROM openjdk:8-jdk

ADD heimdall-demo.jar heimdall-demo.jar
RUN sh -c 'touch /heimdall-demo.jar'  
ENTRYPOINT ["java", "-Dspring.data.mongodb.uri=mongodb://heimdall-demo/users","-Djava.security.egd=file:/dev/./urandom","-jar","/heimdall-demo.jar"]  