# Agence de Location - Projet Java

Projet de gestion d'une agence de location de vehicules (voitures et motos) avec tests unitaires.

## Prerequis

- Java 17 ou superieur
- Docker (pour SonarQube)

## Structure du projet

```
src/main/java/
  agency/       Classes metier
  util/         Classes utilitaires

src/test/java/
  agency/       Tests
  util/         Tests
```

## Commandes

### Tests

```bash
# Tous les tests
./gradlew test

# Tests package util
./gradlew testUtil

# Tests package agency
./gradlew testAgency
```

### Couverture de code

```bash
./gradlew jacocoTestReport
```

Rapport HTML : `build/reports/jacoco/test/html/index.html`

Rapport XML : `build/reports/jacoco/test/jacocoTestReport.xml`

## SonarQube

### Lancer SonarQube

```bash
docker run -d --name sonarqube -p 9000:9000 sonarqube:lts-community
```

Attendre 1-2 minutes puis acceder a http://localhost:9000

Identifiants : admin / password

### Analyser le projet

```bash
./gradlew test jacocoTestReport sonar -Dsonar.login=admin -Dsonar.password=password
```

### Arreter SonarQube

```bash
docker stop sonarqube
docker rm sonarqube
```
