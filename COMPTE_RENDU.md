# Compte-Rendu - TP Agence de Location
## Maintenance Applicative - Tests Unitaires avec AssertJ

---

## 1. Environnement de travail

### Système d'exploitation
- **OS** : Windows 11

### Version de Java
- **Version** : Java 17 (Eclipse Adoptium JDK 17.0.9)
- **Chemin** : `C:\Users\...\Eclipse Adoptium\jdk-17.0.9.9-hotspot`

### Version de Gradle
- **Version** : Gradle 8.13
- **Distribution** : Wrapper Gradle inclus dans le projet

### Versions des principales librairies
| Librairie | Version | Rôle |
|-----------|---------|------|
| JUnit Jupiter | 5.10.2 | Framework de tests unitaires |
| AssertJ Core | 3.25.3 | Librairie d'assertions fluides |
| JaCoCo | 0.8.12 | Couverture de code |
| SonarQube Plugin | 4.4.1.3373 | Analyse de qualité |

### Outils utilisés
- **IDE** : Eclipse 2025-09
- **SonarQube** : Version LTS Community (Docker)
- **Docker Desktop** : Pour l'exécution de SonarQube
- **Git** : Gestion de versions

---

## 2. Création et configuration du projet

### Procédure de création d'un projet Java avec Gradle

1. Créer la structure de dossiers :
```
projet/
├── src/
│   ├── main/java/       # Code source
│   └── test/java/       # Tests unitaires
├── build.gradle         # Configuration Gradle
├── settings.gradle      # Paramètres du projet
└── gradle/wrapper/      # Wrapper Gradle
```

2. Configurer le fichier `build.gradle` :
```groovy
plugins {
    id 'java'
    id 'jacoco'
    id 'org.sonarqube' version '4.4.1.3373'
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation 'org.junit.jupiter:junit-jupiter:5.10.2'
    testImplementation 'org.assertj:assertj-core:3.25.3'
}

test {
    useJUnitPlatform()
    finalizedBy jacocoTestReport
}
```

### Organisation des packages

```
src/main/java/
├── agency/                    # Package métier
│   ├── Vehicle.java          # Interface véhicule
│   ├── AbstractVehicle.java  # Classe abstraite commune
│   ├── Car.java              # Voiture
│   ├── Motorbike.java        # Moto
│   ├── Client.java           # Client
│   ├── RentalAgency.java     # Agence de location
│   ├── BrandCriterion.java   # Critère par marque
│   ├── MaxPriceCriterion.java # Critère par prix
│   └── UnknownVehicleException.java
└── util/                      # Package utilitaire
    └── TimeProvider.java      # Fournisseur de date

src/test/java/
├── agency/                    # Tests du package agency
│   ├── CarTest.java
│   ├── MotorbikeTest.java
│   ├── ClientTest.java
│   ├── RentalAgencyTest.java
│   ├── BrandCriterionTest.java
│   ├── MaxPriceCriterionTest.java
│   └── UnknownVehicleExceptionTest.java
└── util/                      # Tests du package util
    └── TimeProviderTest.java
```

### Dépendances et plugins utilisés

| Plugin/Dépendance | Rôle |
|-------------------|------|
| `java` | Compilation Java |
| `jacoco` | Couverture de code |
| `org.sonarqube` | Analyse SonarQube |
| `junit-jupiter` | Tests unitaires |
| `assertj-core` | Assertions fluides |

---

## 3. Tests unitaires

### Présentation de JUnit 5

JUnit 5 est la dernière version majeure du framework de tests unitaires Java. Il se compose de trois modules :

- **JUnit Platform** : Fondation pour lancer les frameworks de test
- **JUnit Jupiter** : Nouveau modèle de programmation et d'extension
- **JUnit Vintage** : Compatibilité avec JUnit 3 et 4

**Annotations principales :**
- `@Test` : Marque une méthode comme test
- `@BeforeEach` : Exécuté avant chaque test
- `@AfterEach` : Exécuté après chaque test
- `@BeforeAll` : Exécuté une fois avant tous les tests
- `@DisplayName` : Nom personnalisé pour le test

### Présentation d'AssertJ

AssertJ est une librairie d'assertions fluides pour Java. Elle permet d'écrire des assertions lisibles et expressives.

**Exemple :**
```java
// Assertion JUnit standard
assertEquals("Peugeot", car.getBrand());

// Assertion AssertJ
assertThat(car.getBrand()).isEqualTo("Peugeot");
```

### Avantages d'AssertJ par rapport aux assertions JUnit

| Aspect | JUnit | AssertJ |
|--------|-------|---------|
| Lisibilité | `assertEquals(expected, actual)` | `assertThat(actual).isEqualTo(expected)` |
| Chaînage | Non | Oui : `assertThat(list).hasSize(3).contains("a")` |
| Messages d'erreur | Basiques | Détaillés et explicites |
| Collections | Limité | Riche : `containsExactly`, `extracting`, etc. |
| Exceptions | `assertThrows` | `assertThatThrownBy().isInstanceOf().hasMessage()` |

**Exemple de chaînage AssertJ :**
```java
assertThat(agency.getVehicles())
    .hasSize(3)
    .contains(car1, moto1)
    .doesNotContain(car2);
```

### Organisation des tests dans le projet

Les tests suivent la convention de nommage `ClasseTest.java` et sont organisés en miroir du code source :

- Chaque classe a sa classe de test correspondante
- Les tests utilisent le pattern AAA (Arrange-Act-Assert)
- Les noms de méthodes suivent le format : `methode_scenario_resultatAttendu`

---

## 4. Exécution des tests

### Exécuter tous les tests

```bash
# Avec le wrapper Gradle
./gradlew test

# Sous Windows
gradlew.bat test
```

### Exécuter uniquement les tests du package util

```bash
./gradlew testUtil
```

### Exécuter uniquement les tests du package agency

```bash
./gradlew testAgency
```

### Consultation des rapports de tests

Après exécution, les rapports sont disponibles dans :

- **Rapport HTML** : `build/reports/tests/test/index.html`
- **Rapport XML** : `build/test-results/test/`

Le rapport HTML affiche :
- Nombre de tests exécutés
- Tests réussis / échoués
- Temps d'exécution
- Détail par classe de test

---

## 5. Couverture de code — JaCoCo

### Rôle de la couverture de code

La couverture de code mesure le pourcentage de code exécuté lors des tests. Elle permet de :

- Identifier les parties du code non testées
- Évaluer la qualité des tests
- Guider l'écriture de nouveaux tests

### Génération des rapports JaCoCo

```bash
./gradlew jacocoTestReport
```

**Rapports générés :**
- HTML : `build/reports/jacoco/test/html/index.html`
- XML : `build/reports/jacoco/test/jacocoTestReport.xml`

### Interprétation des résultats

JaCoCo mesure plusieurs métriques :

| Métrique | Description |
|----------|-------------|
| Instructions | Nombre d'instructions bytecode couvertes |
| Branches | Couverture des branches (if/else, switch) |
| Lines | Lignes de code couvertes |
| Methods | Méthodes appelées |
| Classes | Classes instanciées |

**Code couleur :**
- **Vert** : Entièrement couvert
- **Jaune** : Partiellement couvert (branches)
- **Rouge** : Non couvert

### Limites de la couverture de code

1. **100% de couverture ne garantit pas l'absence de bugs**
   - Le code peut être exécuté sans que les assertions vérifient le bon comportement

2. **Ne mesure pas la qualité des tests**
   - Un test sans assertion compte comme couverture

3. **Branches complexes**
   - Certaines combinaisons de conditions peuvent être difficiles à tester

4. **Code défensif**
   - Le code de gestion d'erreurs peut être difficile à couvrir

---

## 6. Qualité logicielle — SonarQube

### Rôle de SonarQube dans la maintenance applicative

SonarQube est une plateforme d'analyse de qualité de code qui permet :

- **Détection automatique** des problèmes de code
- **Suivi de la dette technique** dans le temps
- **Centralisation** des métriques de qualité
- **Intégration CI/CD** pour un contrôle continu

### Installation et configuration

1. **Lancer SonarQube avec Docker :**
```bash
docker run -d --name sonarqube -p 9000:9000 sonarqube:lts-community
```

2. **Accès :** http://localhost:9000
   - Login : `admin`
   - Password : `password`

3. **Exécuter l'analyse :**
```bash
./gradlew test jacocoTestReport sonar -Dsonar.login=admin -Dsonar.password=password
```

### Différence entre tests unitaires, couverture et analyse statique

| Aspect | Tests unitaires | Couverture | Analyse statique |
|--------|-----------------|------------|------------------|
| Quand | Exécution | Exécution | Sans exécution |
| Objectif | Vérifier le comportement | Mesurer l'étendue des tests | Détecter les problèmes potentiels |
| Détecte | Bugs fonctionnels | Code non testé | Code smells, vulnérabilités |
| Outil | JUnit | JaCoCo | SonarQube |

### Principaux indicateurs SonarQube

#### Couverture (Coverage)
Pourcentage de code couvert par les tests. Objectif recommandé : > 80%

#### Bugs
Problèmes qui peuvent causer un comportement incorrect :
- NullPointerException potentiel
- Ressources non fermées
- Conditions toujours vraies/fausses

#### Code Smells
Problèmes de maintenabilité :
- Méthodes trop longues
- Duplication de code
- Complexité cyclomatique élevée
- Nommage non conventionnel

#### Vulnérabilités et Security Hotspots
- **Vulnérabilités** : Failles de sécurité confirmées
- **Security Hotspots** : Code sensible à vérifier manuellement

#### Dette technique
Temps estimé pour corriger tous les problèmes détectés. Exprimée en jours/heures de travail.

### Analyse des résultats du projet

#### Résultats obtenus

| Indicateur | Valeur |
|------------|--------|
| Couverture | ~85% |
| Bugs | 0 |
| Vulnérabilités | 0 |
| Code Smells | Quelques mineurs |
| Dette technique | < 1 heure |

#### Zones à risque identifiées

1. **Classe RentalAgency**
   - Complexité la plus élevée (plusieurs méthodes)
   - Gestion des exceptions à surveiller

2. **Méthode toString() dans AbstractVehicle**
   - Formatage de chaînes à vérifier

#### Améliorations envisageables

1. **Augmenter la couverture des branches**
   - Tester tous les cas limites des validations

2. **Réduire la duplication**
   - Factoriser les messages d'exception

3. **Documentation**
   - Compléter la Javadoc sur les méthodes publiques

---

## 7. Conclusion

Ce projet a permis de mettre en place un environnement complet de développement Java avec :

- **Tests unitaires** avec JUnit 5 et AssertJ pour des assertions expressives
- **Couverture de code** avec JaCoCo pour mesurer l'étendue des tests
- **Analyse de qualité** avec SonarQube pour maintenir un code propre

L'utilisation combinée de ces outils permet :
- Une **détection précoce** des problèmes
- Une **documentation vivante** via les tests
- Un **suivi continu** de la qualité du code

---

## Annexes

### Commandes utiles

```bash
# Compiler le projet
./gradlew build

# Exécuter tous les tests
./gradlew test

# Générer le rapport de couverture
./gradlew jacocoTestReport

# Analyser avec SonarQube
./gradlew sonar -Dsonar.login=admin -Dsonar.password=password

# Nettoyer le projet
./gradlew clean
```

### Structure finale du projet

```
agence-location/
├── src/
│   ├── main/java/
│   │   ├── agency/
│   │   └── util/
│   └── test/java/
│       ├── agency/
│       └── util/
├── build.gradle
├── settings.gradle
├── gradlew.bat
├── gradle/wrapper/
└── README.md
```
