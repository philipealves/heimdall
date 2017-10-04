FROM openjdk:8-jdk

ADD build/libs/heimdall-demo.jar heimdall-demo.jar
RUN sh -c 'touch /heimdall-demo.jar'  
ENTRYPOINT ["java","-jar","/heimdall-demo.jar"]  