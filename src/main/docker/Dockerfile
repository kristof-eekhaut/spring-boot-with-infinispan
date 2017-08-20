FROM frolvlad/alpine-oraclejdk8:slim

ADD maven/spring-boot-with-infinispan.jar spring-boot-with-infinispan.jar
RUN sh -c 'touch /spring-boot-with-infinispan.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/spring-boot-with-infinispan.jar"]
