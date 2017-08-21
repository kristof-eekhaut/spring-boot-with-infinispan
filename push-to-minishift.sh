docker login -u developer -p $(oc whoami -t) $(minishift openshift registry)
docker tag spring-boot-with-infinispan $(minishift openshift registry)/spring-boot-with-infinispan/spring-boot-with-infinispan
docker push $(minishift openshift registry)/spring-boot-with-infinispan/spring-boot-with-infinispan