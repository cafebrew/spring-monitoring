[![Build Status](https://travis-ci.com/cafebrew/spring-monitoring.svg?branch=master)](https://travis-ci.com/cafebrew/spring-monitoring)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=cafebrew_spring-monitoring&metric=alert_status)](https://sonarcloud.io/dashboard?id=cafebrew_spring-monitoring)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=cafebrew_spring-monitoring&metric=bugs)](https://sonarcloud.io/dashboard?id=cafebrew_spring-monitoring)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=cafebrew_spring-monitoring&metric=code_smells)](https://sonarcloud.io/dashboard?id=cafebrew_spring-monitoring)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=cafebrew_spring-monitoring&metric=coverage)](https://sonarcloud.io/dashboard?id=cafebrew_spring-monitoring)

# spring-monitoring
Spring boot application monitoing with prometheus and grafana

[Getting Started](https://github.com/cafebrew/spring-with-docker/blob/master/HELP.md)
[BLOG Article] (https://coldbrew.dev/26)

## Script

### Build  

```bash
$ ./gradlew clean build -x test
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
