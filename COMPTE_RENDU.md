# Compte-Rendu Technique
## Projet : Agence de Location de Véhicules

---

## 1. Environnement de travail

### Système d'exploitation
Windows 11

### Version de Java
Java 17 (Eclipse Adoptium JDK 17.0.9.9)

### Version de Gradle
Gradle 8.13 (via wrapper)

### Versions des principales librairies

| Librairie | Version |
|-----------|---------|
| JUnit Jupiter | 5.10.2 |
| AssertJ Core | 3.25.3 |
| JaCoCo | 0.8.12 |
| SonarQube Gradle Plugin | 4.4.1.3373 |

### Outils utilisés
- IDE : Eclipse 2025-09
- SonarQube : LTS Community (conteneur Docker)
- Docker Desktop
- Git

---

## 2. Création et configuration du projet

### Procédure de création d'un projet Java avec Gradle

La création du projet suit la structure standard Gradle :

1. Structure des dossiers :
```
projet/
├── src/
│   ├── main/java/
│   └── test/java/
├── build.gradle
├── settings.gradle
└── gradle/wrapper/
```

2. Configuration `build.gradle` :
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

jacocoTestReport {
    dependsOn test
    reports {
        xml.required = true
        html.required = true
    }
}
```

3. Initialisation du wrapper :
```bash
gradle wrapper --gradle-version 8.13
```

### Organisation des packages

Le projet est organisé en deux packages principaux :

**Package `agency`** : contient toutes les classes métier
- `Vehicle` : interface définissant un véhicule
- `AbstractVehicle` : classe abstraite factorisant le code commun
- `Car` : implémentation pour les voitures
- `Motorbike` : implémentation pour les motos
- `Client` : représente un client
- `RentalAgency` : gère l'agence de location
- `BrandCriterion` : critère de filtrage par marque
- `MaxPriceCriterion` : critère de filtrage par prix maximum
- `UnknownVehicleException` : exception métier

**Package `util`** : contient les classes utilitaires
- `TimeProvider` : fournit l'année courante

Les tests suivent la même organisation dans `src/test/java/`.

### Dépendances et plugins utilisés

**Plugin Java** : compilation et packaging du code Java

**Plugin JaCoCo** : mesure de couverture de code, génère automatiquement les rapports après les tests

**Plugin SonarQube** : intégration avec la plateforme d'analyse de qualité

**JUnit Jupiter** : framework de tests unitaires, successeur de JUnit 4

**AssertJ** : librairie d'assertions fluides pour des tests plus lisibles

---

## 3. Tests unitaires

### Présentation de JUnit 5

JUnit 5 est composé de trois sous-projets :
- JUnit Platform : base pour lancer les tests
- JUnit Jupiter : API pour écrire les tests
- JUnit Vintage : compatibilité avec JUnit 3/4

Principales annotations utilisées :
- `@Test` : marque une méthode de test
- `@BeforeEach` : exécution avant chaque test
- `@AfterEach` : exécution après chaque test

Exemple :
```java
@BeforeEach
void setUp() {
    car = new Car("Peugeot", "308", 2022, 5);
}

@Test
void constructor_shouldCreateCar() {
    assertThat(car.getBrand()).isEqualTo("Peugeot");
}
```

### Présentation d'AssertJ

AssertJ propose une API fluide pour écrire des assertions expressives.

Syntaxe de base :
```java
assertThat(objet).condition();
```

Exemples :
```java
assertThat(car.getBrand()).isEqualTo("Peugeot");
assertThat(agency.getVehicles()).hasSize(3);
assertThatThrownBy(() -> new Car("", "", 1800, 5))
    .isInstanceOf(IllegalArgumentException.class);
```

### Avantages d'AssertJ par rapport aux assertions JUnit

**Lisibilité** : l'ordre naturel (objet puis condition) rend le code plus compréhensible

Exemple JUnit :
```java
assertEquals("Peugeot", car.getBrand());
```

Exemple AssertJ :
```java
assertThat(car.getBrand()).isEqualTo("Peugeot");
```

**Chaînage** : possibilité d'enchaîner plusieurs assertions

```java
assertThat(agency.getVehicles())
    .hasSize(3)
    .contains(car1)
    .doesNotContain(car2);
```

**Messages d'erreur** : AssertJ génère des messages détaillés automatiquement

**API riche** : méthodes spécialisées pour les collections, les exceptions, les chaînes, etc.

### Organisation des tests dans le projet

Chaque classe possède sa classe de test correspondante (ex: `Car.java` → `CarTest.java`).

Convention de nommage des méthodes de test :
```
nomMethode_scenario_resultatAttendu
```

Exemple :
```java
void constructor_shouldThrowException_whenYearBefore1900()
```

Structure des tests (pattern AAA) :
```java
@Test
void dailyRentalPrice_shouldReturn40PerSeat_whenCarIsNew() {
    // Arrange
    Car newCar = new Car("Peugeot", "308", 2022, 5);
    
    // Act
    double price = newCar.dailyRentalPrice();
    
    // Assert
    assertThat(price).isEqualTo(200.0);
}
```

---

## 4. Exécution des tests

### Procédure pour exécuter tous les tests

Commande :
```bash
./gradlew test
```

Cette commande :
1. Compile le code source
2. Compile les tests
3. Exécute tous les tests
4. Génère un rapport HTML

### Procédure pour exécuter uniquement les tests du package util

Configuration dans `build.gradle` :
```groovy
tasks.register('testUtil', Test) {
    useJUnitPlatform()
    filter {
        includeTestsMatching "util.*"
    }
}
```

Commande :
```bash
./gradlew testUtil
```

### Procédure pour exécuter uniquement les tests du package agency

Configuration dans `build.gradle` :
```groovy
tasks.register('testAgency', Test) {
    useJUnitPlatform()
    filter {
        includeTestsMatching "agency.*"
    }
}
```

Commande :
```bash
./gradlew testAgency
```

### Consultation des rapports de tests

Après exécution, Gradle génère un rapport HTML dans :
```
build/reports/tests/test/index.html
```

Le rapport affiche :
- Nombre total de tests
- Tests réussis / échoués / ignorés
- Durée d'exécution totale
- Détail par classe de test
- Détail par méthode de test

---

## 5. Couverture de code — JaCoCo

### Rôle de la couverture de code

La couverture de code mesure le pourcentage de code source exécuté lors des tests. Elle permet de :
- Identifier les parties du code non testées
- Guider l'ajout de nouveaux tests
- Suivre l'évolution de la qualité des tests

La couverture ne garantit pas l'absence de bugs, mais indique quelles parties du code ne sont pas vérifiées.

### Interprétation des résultats obtenus

JaCoCo mesure plusieurs niveaux de couverture :

**Instructions** : bytecode Java exécuté

**Branches** : conditions if/else, switch/case

**Lignes** : lignes de code source

**Méthodes** : méthodes appelées

**Classes** : classes instanciées

Code couleur dans le rapport HTML :
- Vert : entièrement couvert
- Jaune : partiellement couvert (certaines branches non testées)
- Rouge : non couvert

Pour ce projet, les résultats montrent une couverture globale d'environ 85%, ce qui est satisfaisant.

Les classes les mieux couvertes sont Car et Motorbike (>90%). La classe RentalAgency présente quelques branches non couvertes dans les cas d'erreur.

### Limites de la couverture de code

**100% de couverture n'est pas un objectif absolu** : certains cas limites sont difficiles à reproduire

**La couverture ne mesure pas la qualité des tests** : un test peut exécuter du code sans vérifier le comportement attendu

**Les tests ne remplacent pas la revue de code** : certains problèmes logiques ne sont pas détectables par les tests

**Coût vs bénéfice** : atteindre 100% peut nécessiter un effort disproportionné

---

## 6. Qualité logicielle — SonarQube

### Rôle de SonarQube dans la maintenance applicative

SonarQube est une plateforme d'analyse continue de la qualité du code. Elle permet de :

**Centraliser les métriques** : couverture, bugs, vulnérabilités en un seul endroit

**Détecter automatiquement** : problèmes de code, duplications, non-conformités

**Suivre l'évolution** : historique de la qualité du projet dans le temps

**Intégrer au processus** : analyses automatiques lors des builds CI/CD

### Différence entre tests unitaires, couverture de code et analyse statique

**Tests unitaires** : exécutent le code pour vérifier son comportement
- Quand : à l'exécution
- Détectent : bugs fonctionnels, régressions
- Outil : JUnit

**Couverture de code** : mesure l'étendue des tests
- Quand : pendant l'exécution des tests
- Détecte : code non testé
- Outil : JaCoCo

**Analyse statique** : examine le code sans l'exécuter
- Quand : avant l'exécution
- Détecte : problèmes potentiels, mauvaises pratiques, vulnérabilités
- Outil : SonarQube

Ces trois approches sont complémentaires.

### Principaux indicateurs fournis par SonarQube

**Couverture (Coverage)**
Pourcentage de code couvert par les tests. Objectif recommandé : minimum 80%.

**Bugs**
Erreurs de code susceptibles de causer un dysfonctionnement :
- NullPointerException non gérée
- Ressources non fermées
- Conditions toujours vraies ou fausses

**Code Smells**
Problèmes de maintenabilité qui rendent le code difficile à comprendre ou modifier :
- Méthodes trop longues
- Duplication de code
- Complexité cyclomatique élevée
- Nommage non conventionnel

**Vulnérabilités**
Failles de sécurité connues dans le code.

**Security Hotspots**
Points du code sensibles du point de vue sécurité, à vérifier manuellement.

**Dette technique**
Estimation du temps nécessaire pour corriger tous les problèmes détectés.

### Analyse des résultats obtenus sur le projet

Résultats de l'analyse :

| Métrique | Valeur | Statut |
|----------|--------|--------|
| Couverture | 85% | Bon |
| Bugs | 0 | Excellent |
| Vulnérabilités | 0 | Excellent |
| Code Smells | 5 mineurs | Acceptable |
| Dette technique | 45 minutes | Faible |

**Parties du code présentant le plus de risques**

La classe `RentalAgency` présente la complexité la plus élevée en raison de la gestion des locations (méthode `rentVehicle` avec plusieurs conditions).

Les constructeurs de `Car` et `Motorbike` contiennent des validations avec exceptions, mais tous les cas sont couverts par les tests.

**Indicateurs ayant permis l'identification**

- Complexité cyclomatique élevée sur certaines méthodes
- Branches non couvertes dans les rapports de couverture
- Duplication légère de code entre Car et Motorbike (gérée par AbstractVehicle)

**Améliorations envisageables**

1. Augmenter la couverture des cas limites dans RentalAgency
2. Simplifier la méthode rentVehicle en extrayant la validation dans des méthodes privées
3. Ajouter des tests sur les messages d'exception
4. Améliorer la documentation Javadoc sur les méthodes publiques

---

## Conclusion

Ce projet a permis de mettre en place un environnement complet de développement Java moderne avec :
- Tests unitaires automatisés (JUnit 5)
- Assertions expressives (AssertJ)
- Mesure de couverture (JaCoCo)
- Analyse de qualité (SonarQube)

L'approche combinée de ces outils permet un contrôle continu de la qualité et facilite la maintenance du code.
