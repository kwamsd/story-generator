*Projet de développement d’application Android (Java & Kotlin)*

UE : **Développement d’applications Android** — Semestre 4

---

## 1. Pitch rapide

📂 **Répertoire GitHub** : https://github.com/kwamsd/story-generator

Story Generator est une application Android destinée aux enfants : en sélectionnant quelques mots‑clés, l’enfant obtient automatiquement une histoire illustrée par un mini‑quiz pour valider sa compréhension. 

---

## 2. Objectifs du projet

1. **Mettre en pratique** les bases du SDK Android (Activities, layouts XML, RecyclerView).
2. **Manipuler** JSON et la persistance locale (assets).
3. **Maitriser** Java projet approfondi.

---

## 3. Fonctionnalités clés

- **Sélection de mots‑clés** : grille de cases à cocher (RecyclerView + GridLayoutManager).
- **Génération d’histoire** : filtration d’un fichier JSON (`assets/histoires.json`) selon les mots choisis.
- **Lecture plein écran** avec titre, histoire et visuel d’arrière‑plan.
- **Quiz 2 questions** à réponses multiples ; résultat instantané via `AlertDialog`.
- **Thème clair** personnalisé (couleurs pastel, effet *glassmorphism*).

---

## 4. Parcours utilisateur & Pages

| Écran | Classe | Rôle principal |
| --- | --- | --- |
| **Accueil** | `AccueilActivity.java` | ‑ Saisie du prénom (optionnel) |
| ‑ Choix des mots‑clés |  |  |
| ‑ Bouton « Générer » |  |  |
| **Histoire** | `HistoireActivity.java` | ‑ Affichage du titre et du contenu |
| ‑ Quiz interactif |  |  |
| ‑ Affichage du score |  |  |

> Navigation : un Intent transporte la liste ArrayList<String> des mots‑clés ainsi que le prénom vers HistoireActivity.
> 

---

## 5. Architecture technique

### 5.1 Pile technologique

- **Java 17** ▸ Activities, Adapter.
- **Kotlin 1.9** ▸ Thème (`ui/theme/*`).
- **Android API 26+** (minSdk 26, compileSdk 34).
- **AndroidX** (`appcompat`, `cardview`, `constraintlayout`, `recyclerview`).

### 5.2 Packages & fichiers majeurs

```
com.example.storygenerator
├─ AccueilActivity.java      // page d’accueil
├─ HistoireActivity.java     // lecture + quiz
├─ MotsClesAdapter.java      // recyclerview adapter
└─ ui.theme (Kotlin)         // Color.kt, Theme.kt, Type.kt

```

### 5.3 Ressources

```
res/layout/activity_accueil.xml     // UI de l’accueil
res/layout/activity_histoire.xml    // UI de l’histoire
assets/histoires.json               // banque d’histoires & quiz
res/drawable/bg_gradient.xml        // fond dégradé

```

### 5.4 Gestion des dépendances

Tout est géré par **Gradle** (`build.gradle` de l’app). Aucun framework externe ou accès réseau.

---

## 6. Arborescence (vue simplifiée)

```
StoryGenerator/
 ├─ app/
 │  ├─ src/
 │  │  ├─ main/
 │  │  │  ├─ java/com/example/storygenerator/  (code Java)
 │  │  │  ├─ kotlin/... (thème)
 │  │  │  ├─ res/ (layouts, drawables, values…)
 │  │  │  └─ assets/histoires.json
 │  ├─ build.gradle
 ├─ gradle/
 ├─ build.gradle (project‑level)
 └─ settings.gradle

```

---

## 7. Lancer le projet sous **Android Studio**

> Clonage Git : git clone https://github.com/kwamsd/story-generator.git puis ouvrez le projet dans Android Studio.
> 
1. **Pré‑requis** :
    - Android Studio Flamingo ou plus récent (Arctic Fox ≥ 2023.1),
    - JDK 17 installé,
    - SDK Android 34 téléchargé.
2. **Clonage** : dans un terminal, exécuter `git clone https://github.com/kwamsd/story-generator.git` puis `File › Open…` sur le dossier cloné.
3. **Sync Gradle** : Android Studio détecte automatiquement le wrapper ; valider l’update éventuelle du *Gradle Plugin*.
4. **Exécution** :
    - Choisir un AVD API 26+ **ou** brancher un appareil physique,
    - Cliquer ► *Run ‘app’*.
5. **Test fonctionnel** :
    - Choisir 3‑4 mots‑clés et appuyer sur *Générer*,
    - Lire l’histoire, répondre au quiz, vérifier le score.

---

## 8. Points pédagogiques

- Lire un **JSON** depuis les assets et le convertir en `JSONObject`.
- Utiliser un **RecyclerView** avec `GridLayoutManager` et adapter simple (`CheckBox`).
- Passer des données d’une Activity à l’autre avec `Intent` + `putStringArrayListExtra`.
- Évaluer l’état de sélection grâce à `RadioGroup` / `RadioButton`.

---

## 9. Pistes d’amélioration (optionnelles)

- Ajouter un **Text‑to‑Speech** pour lire l’histoire à l’enfant.
- Génération algorithmique : intégrer GPT‑4o via une API web.
- Système de **score cumulé** et badges.
- Mode sombre / Accessibilité (police Dyslexie‑friendly).
- Sauvegarde des histoires dans une base locale (*Room*).

---

## 10. Auteurs

### **Kwameh DHEGBO**
- Création du fichier **JSON** contenant les données des histoires.
- Développement des **classes Java** pour la **page d’accueil**.

### **Bilal DEMBÉLÉ**
- Développement des autres **classes Java** nécessaires au fonctionnement de l'application.
- Implémentation de la logique Java liée à l’interface d’accueil.

### **Ilian ARICHI** & **Clément DE ABREU**
- Réalisation des interfaces graphiques dans le dossier `res/`.