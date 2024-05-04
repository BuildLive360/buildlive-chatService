FROM openjdk:17
EXPOSE 3000
ADD target/buildlive-chatservice.jar buildlive-chatservice.jar
ENTRYPOINT ["java","-jar","/buildlive-chatservice.jar"]