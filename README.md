# Agence de Location - Projet Java avec Tests et SonarQube

## Prerequis

- Java 17 ou superieur
- Gradle (le wrapper est inclus)
- Docker (pour SonarQube)

## Structure du projet

```
src/main/java/
  agency/       Classes metier (Vehicle, Car, Motorbike, Client, RentalAgency, etc.)
  util/         Classes utilitaires (TimeProvider)
src/test/java/
  agency/       Tests des classes metier
  util/         Tests des classes utilitaires
```

## Commandes Gradle

### Execution des tests

```bash
# Tous les tests
./gradlew test

# Tests du package util uniquement
./gradlew testUtil

# Tests du package agency uniquement
./gradlew testAgency
```

### Rapports JaCoCo (couverture de code)

```bash
./gradlew jacocoTestReport
```

Les rapports sont generes dans :
- HTML : `build/reports/jacoco/test/html/index.html`
- XML : `build/reports/jacoco/test/jacocoTestReport.xml`

## Installation et configuration de SonarQube

### 1. Lancer SonarQube avec Docker

```bash
docker run -d --name sonarqube -p 9000:9000 sonarqube:lts-community
```

Attendre environ 1-2 minutes que SonarQube demarre completement.

### 2. Acces a SonarQube

- URL : http://localhost:9000
- Login par defaut : admin
- Mot de passe par defaut : admin (a changer a la premiere connexion)

### 3. Generer un token d'authentification

1. Se connecter a SonarQube
2. Aller dans : Mon compte > Securite > Generer un token
3. Nom du token : `gradle-token`
4. Type : Global Analysis Token
5. Copier le token genere

### 4. Executer l'analyse SonarQube

```bash
# Avec le token en parametre
./gradlew test jacocoTestReport sonar -Dsonar.token=VOTRE_TOKEN

# Ou en definissant le token dans gradle.properties
# sonar.token=VOTRE_TOKEN
# puis executer :
./gradlew test jacocoTestReport sonar
```

### 5. Consulter les resultats

Apres l'analyse, acceder a http://localhost:9000 pour voir :
- La couverture de code
- Les bugs detectes
- Les code smells
- Les vulnerabilites
- La dette technique

## Indicateurs SonarQube

| Indicateur | Description |
|------------|-------------|
| Coverage | Pourcentage de code couvert par les tests |
| Bugs | Problemes qui peuvent causer des erreurs |
| Code Smells | Problemes de maintenabilite du code |
| Vulnerabilities | Failles de securite potentielles |
| Security Hotspots | Points a verifier manuellement |
| Technical Debt | Temps estime pour corriger tous les problemes |

## Arreter SonarQube

```bash
docker stop sonarqube
docker rm sonarqube
```
