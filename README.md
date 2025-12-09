# RayTracing

Un petit moteur de lancer de rayons (ray tracing) en Java, avec parsing de scènes, éclairage direct (Lambert + Phong), ombres et réflexion récursive.

## Fonctionnalités
- Géométrie: sphères, plans, triangles (Möller–Trumbore pour les triangles)
- Matériaux: `diffuse`, `specular`, `shininess` (Phong)
- Lumières: directionnelle (soleil) et ponctuelle (ampoule), ombres
- Rendu: Lambert + Phong, réflexion récursive (profondeur configurée via `maxdepth`)
- Parsing de scènes (.scene): `size`, `output`, `camera`, `ambient`, `diffuse`, `specular`, `shininess`, `directional`, `point`, `sphere`, `plane`, `maxverts`, `vertex`, `tri`, `maxdepth`

## Structure du projet
```
src/
  main/java/org/example/
    math/          # Math & modèles: AbstractVec3, Camera, Color, Point, Vector, Scene
    geometry/      # Formes: Sphere, Triangle, Plane, Shape
    raytracer/     # Coeur rendu: Ray, Intersection, Renderer, lumières
    parsing/       # Parser de fichiers .scene
  main/java/Scenes/  # Exemples de scènes
```

## Prérequis
- Java 17+ (ou version compatible avec votre `pom.xml`)
- Maven 3.8+

## Compilation et exécution (Windows PowerShell)
L’application attend un chemin de fichier `.scene` en argument.

Exécution directe après compilation:
```powershell
mvn -q -DskipTests compile
java -cp "target/classes" org.example.Main "src/main/java/Scenes/final.scene"
```

Vous pouvez remplacer `src/main/java/Scenes/final.scene` par n’importe quel fichier de scène.

Par défaut, `Main` charge le fichier de scène défini dans `src/main/java/Scenes/final_avec_bonus.scene`.
- Pour changer la scène, modifiez la variable `sceneFile` dans `src/main/java/org/example/Main.java`.
- Le rendu s’écrit dans le fichier indiqué par la directive `output` du fichier `.scene`.

## Lancement des tests
```powershell
mvn -q test
```

## Génération de la Javadoc
Des commentaires Javadoc (en français) sont présents dans le code.
Pour générer un site Javadoc (si le plugin est configuré dans Maven):
```powershell
mvn -q javadoc:javadoc
```
La documentation sera disponible sous `target/site/apidocs/`.

## Notes
- Les classes math (Camera, Color, Point, Scene, Vector, AbstractVec3) ont été déplacées dans le package `org.example.math`.
- Tous les commentaires et la Javadoc ont été francisés pour cohérence.
- Les scènes d’exemple se trouvent dans `src/main/java/Scenes/`.

## Création et exécution du JAR

Construction du JAR (packaging standard Maven):
```powershell
mvn -q -DskipTests package
```
Le JAR sera généré sous `target/` (par ex. `target/RayTracing-1.0-SNAPSHOT.jar`).

Exécution du JAR en fournissant le fichier `.scene`:
```powershell
java -cp "target/RayTracing-1.0-SNAPSHOT.jar" org.example.Main "src/main/java/Scenes/final.scene"
```
Astuce: si vous souhaitez un JAR exécutable via `java -jar`, il faut définir le `Main-Class` dans le manifeste (plugin Maven `maven-jar-plugin`). Je peux l’ajouter si vous le souhaitez.
