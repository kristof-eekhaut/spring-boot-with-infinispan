FROM frolvlad/alpine-oraclejdk8:slim

ADD maven/spring-boot-with-infinispan.jar spring-boot-with-infinispan.jar
RUN sh -c 'touch /spring-boot-with-infinispan.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Djava.net.preferIPv4Stack=true", "-agentlib:jdwp=transport=dt_socket,address=9009,server=y,suspend=n", "-jar","/spring-boot-with-infinispan.jar"]
