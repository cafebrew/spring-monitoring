# spring-monitoring
Spring boot application monitoing with prometheus and grafana

[Getting Started](https://github.com/cafebrew/spring-with-docker/blob/master/HELP.md)

## Script

### Build  

```bash
$ ./gradlew clean build
```

### Bake 

```bash
$ ./gradlew jib -Djib.to.auth.username=${DOCKER_USER} -Djib.to.auth.password=${DOCKER_PASS}
```

### SonarCloud

```bash
$ ./gradlew sonarqube \
    -Dsonar.projectKey=cafebrew_spring-monitoring \
    -Dsonar.organization=cafebrew-github \
    -Dsonar.host.url=https://sonarcloud.io \
    -Dsonar.login=8282a6bf7a6045d54db14f24f21a63ccedd8c297
```