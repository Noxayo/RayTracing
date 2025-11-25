package org.example.parsing;

import org.example.*;
import org.example.Camera;
import org.example.Scene;
import org.example.geometry.Plane;
import org.example.geometry.Sphere;
import org.example.geometry.Triangle;
import org.example.raytracer.DirectionalLight;
import org.example.raytracer.PointLight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Parser pour lire les fichiers de description de scène (.scene)
 * Lit le fichier ligne par ligne et construit l'objet Scene
 */
public class SceneFileParser {

    /**
     * Parse un fichier de scène
     * @param configFilePath Chemin vers le fichier .scene
     * @return L'objet Scene construit
     * @throws IOException Si erreur de lecture
     */
    public Scene parse(String configFilePath) throws IOException {
        Scene scene = new Scene();

        // Lire le fichier ligne par ligne
        try (BufferedReader reader = new BufferedReader(new FileReader(configFilePath))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                try {
                    readLine(line, scene);
                } catch (Exception e) {
                    throw new IOException("Erreur ligne " + lineNumber + ": " + e.getMessage(), e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Error while parsing " + configFilePath);
        }

        // Valider la scène après le parsing
        validateScene(scene);

        return scene;
    }

    /**
     * Lit et traite une ligne du fichier
     * @param line La ligne à traiter
     * @param scene L'objet Scene à remplir
     */
    private void readLine(String line, Scene scene) {
        // Nettoyer la ligne (enlever espaces avant/après)
        line = line.trim();

        // Ignorer les lignes vides et les commentaires
        if (line.isEmpty() || line.startsWith("#")) {
            return;
        }

        // Découper la ligne en mots (séparés par des espaces)
        String[] tokens = line.split("\\s+");
        String command = tokens[0];

        // Traiter selon la commande
        if (command.equals("size")) {
            parseSize(tokens, scene);
        }
        else if (command.equals("output")) {
            parseOutput(tokens, scene);
        }
        else if (command.equals("camera")) {
            parseCamera(tokens, scene);
        }
        else if (command.equals("ambient")) {
            parseAmbient(tokens, scene);
        }
        else if (command.equals("diffuse")) {
            parseDiffuse(tokens, scene);
        }
        else if (command.equals("specular")) {
            parseSpecular(tokens, scene);
        }
        else if (command.equals("directional")) {
            parseDirectionalLight(tokens, scene);
        }
        else if (command.equals("point")) {
            parsePointLight(tokens, scene);
        }
        else if (command.equals("sphere")) {
            parseSphere(tokens, scene);
        }
        else if (command.equals("maxverts")) {
            parseMaxVerts(tokens, scene);
        }
        else if (command.equals("vertex")) {
            parseVertex(tokens, scene);
        }
        else if (command.equals("tri")) {
            parseTriangle(tokens, scene);
        }
        else if (command.equals("plane")) {
            parsePlane(tokens, scene);
        }
        else {
            throw new IllegalArgumentException("Commande inconnue: " + command);
        }
    }

    /**
     * Parse: size largeur hauteur
     */
    private void parseSize(String[] tokens, Scene scene) {
        if (tokens.length != 3) {
            throw new IllegalArgumentException("size attend 2 paramètres");
        }
        int width = Integer.parseInt(tokens[1]);
        int height = Integer.parseInt(tokens[2]);
        scene.setWidth(width);
        scene.setHeight(height);
    }

    /**
     * Parse: output nomfichier.png
     */
    private void parseOutput(String[] tokens, Scene scene) {
        if (tokens.length != 2) {
            throw new IllegalArgumentException("output attend 1 paramètre");
        }
        scene.setOutput(tokens[1]);
    }

    /**
     * Parse: camera x y z u v w m n o fov
     */
    private void parseCamera(String[] tokens, Scene scene) {
        if (tokens.length != 11) {
            throw new IllegalArgumentException("camera attend 10 paramètres");
        }

        // Position de l'œil
        Point lookFrom = new Point(
                Double.parseDouble(tokens[1]),
                Double.parseDouble(tokens[2]),
                Double.parseDouble(tokens[3])
        );

        // Point visé
        Point lookAt = new Point(
                Double.parseDouble(tokens[4]),
                Double.parseDouble(tokens[5]),
                Double.parseDouble(tokens[6])
        );

        // Direction "haut"
        Vector up = new Vector(
                Double.parseDouble(tokens[7]),
                Double.parseDouble(tokens[8]),
                Double.parseDouble(tokens[9])
        );

        // Angle de vue
        double fov = Double.parseDouble(tokens[10]);

        Camera camera = new Camera(lookFrom, lookAt, up, fov);
        scene.setCamera(Camera);
    }

    /**
     * Parse: ambient r g b
     */
    private void parseAmbient(String[] tokens, Scene scene) {
        if (tokens.length != 4) {
            throw new IllegalArgumentException("ambient attend 3 paramètres");
        }

        Color ambient = new Color(
                Double.parseDouble(tokens[1]),
                Double.parseDouble(tokens[2]),
                Double.parseDouble(tokens[3])
        );
        scene.setAmbient(ambient);
    }

    /**
     * Parse: diffuse r g b
     * Cette couleur s'appliquera aux prochains objets créés
     */
    private void parseDiffuse(String[] tokens, Scene scene) {
        if (tokens.length != 4) {
            throw new IllegalArgumentException("diffuse attend 3 paramètres");
        }

        Color diffuse = new Color(
                Double.parseDouble(tokens[1]),
                Double.parseDouble(tokens[2]),
                Double.parseDouble(tokens[3])
        );
        scene.setCurrentDiffuse(diffuse);
    }

    /**
     * Parse: specular r g b
     * Cette couleur s'appliquera aux prochains objets créés
     */
    private void parseSpecular(String[] tokens, Scene scene) {
        if (tokens.length != 4) {
            throw new IllegalArgumentException("specular attend 3 paramètres");
        }

        Color specular = new Color(
                Double.parseDouble(tokens[1]),
                Double.parseDouble(tokens[2]),
                Double.parseDouble(tokens[3])
        );
        scene.setCurrentSpecular(specular);
    }

    /**
     * Parse: directional x y z r g b
     */
    private void parseDirectionalLight(String[] tokens, Scene scene) {
        if (tokens.length != 7) {
            throw new IllegalArgumentException("directional attend 6 paramètres");
        }

        // Direction
        Vector direction = new Vector(
                Double.parseDouble(tokens[1]),
                Double.parseDouble(tokens[2]),
                Double.parseDouble(tokens[3])
        );

        // Couleur
        Color color = new Color(
                Double.parseDouble(tokens[4]),
                Double.parseDouble(tokens[5]),
                Double.parseDouble(tokens[6])
        );

        DirectionalLight light = new DirectionalLight(direction, color);
        scene.addLight(light);
    }

    /**
     * Parse: point x y z r g b
     */
    private void parsePointLight(String[] tokens, Scene scene) {
        if (tokens.length != 7) {
            throw new IllegalArgumentException("point attend 6 paramètres");
        }

        // Position
        Point position = new Point(
                Double.parseDouble(tokens[1]),
                Double.parseDouble(tokens[2]),
                Double.parseDouble(tokens[3])
        );

        // Couleur
        Color color = new Color(
                Double.parseDouble(tokens[4]),
                Double.parseDouble(tokens[5]),
                Double.parseDouble(tokens[6])
        );

        PointLight light = new PointLight(position, color);
        scene.addLight(light);
    }

    /**
     * Parse: sphere x y z rayon
     * Utilise les couleurs courantes (diffuse et specular)
     */
    private void parseSphere(String[] tokens, Scene scene) {
        if (tokens.length != 5) {
            throw new IllegalArgumentException("sphere attend 4 paramètres");
        }

        // Centre
        Point center = new Point(
                Double.parseDouble(tokens[1]),
                Double.parseDouble(tokens[2]),
                Double.parseDouble(tokens[3])
        );

        // Rayon
        double radius = Double.parseDouble(tokens[4]);

        // Créer la sphère avec les couleurs courantes
        Sphere sphere = new Sphere(
                center,
                radius,
                scene.getCurrentDiffuse(),
                scene.getCurrentSpecular()
        );
        scene.addShape(sphere);
    }

    /**
     * Parse: maxverts nombre
     */
    private void parseMaxVerts(String[] tokens, Scene scene) {
        if (tokens.length != 2) {
            throw new IllegalArgumentException("maxverts attend 1 paramètre");
        }

        int maxVerts = Integer.parseInt(tokens[1]);
        scene.setMaxVerts(maxVerts);
    }

    /**
     * Parse: vertex x y z
     */
    private void parseVertex(String[] tokens, Scene scene) {
        if (tokens.length != 4) {
            throw new IllegalArgumentException("vertex attend 3 paramètres");
        }

        Point vertex = new Point(
                Double.parseDouble(tokens[1]),
                Double.parseDouble(tokens[2]),
                Double.parseDouble(tokens[3])
        );
        scene.addVertex(vertex);
    }

    /**
     * Parse: tri indice1 indice2 indice3
     */
    private void parseTriangle(String[] tokens, Scene scene) {
        if (tokens.length != 4) {
            throw new IllegalArgumentException("tri attend 3 paramètres");
        }

        int i0 = Integer.parseInt(tokens[1]);
        int i1 = Integer.parseInt(tokens[2]);
        int i2 = Integer.parseInt(tokens[3]);

        // Vérifier que les indices sont valides
        if (i0 >= scene.getMaxVerts() || i1 >= scene.getMaxVerts() || i2 >= scene.getMaxVerts()) {
            throw new IllegalArgumentException(
                    "Les indices doivent être < maxverts (" + scene.getMaxVerts() + ")"
            );
        }

        // Récupérer les sommets
        Point v0 = scene.getVertices().get(i0);
        Point v1 = scene.getVertices().get(i1);
        Point v2 = scene.getVertices().get(i2);

        // Créer le triangle avec les couleurs courantes
        Triangle triangle = new Triangle(
                v0, v1, v2,
                scene.getCurrentDiffuse(),
                scene.getCurrentSpecular()
        );
        scene.addShape(triangle);
    }

    /**
     * Parse: plane x y z nx ny nz
     */
    private void parsePlane(String[] tokens, Scene scene) {
        if (tokens.length != 7) {
            throw new IllegalArgumentException("plane attend 6 paramètres");
        }

        // Point du plan
        Point point = new Point(
                Double.parseDouble(tokens[1]),
                Double.parseDouble(tokens[2]),
                Double.parseDouble(tokens[3])
        );

        // Vecteur normal
        Vector normal = new Vector(
                Double.parseDouble(tokens[4]),
                Double.parseDouble(tokens[5]),
                Double.parseDouble(tokens[6])
        );

        // Créer le plan avec les couleurs courantes
        Plane plane = new Plane(
                point,
                normal,
                scene.getCurrentDiffuse(),
                scene.getCurrentSpecular()
        );
        scene.addShape(plane);
    }

    /**
     * Valide que la scène est complète et correcte
     */
    private void validateScene(Scene scene) {
        // Vérifier que size est défini
        if (scene.getWidth() == 0 || scene.getHeight() == 0) {
            throw new IllegalStateException("La taille (size) doit être définie");
        }

        // Vérifier que camera est défini
        if (scene.getCamera() == null) {
            throw new IllegalStateException("La caméra (camera) doit être définie");
        }

        // Vérifier que la somme des lumières <= 1
        double totalR = 0, totalG = 0, totalB = 0;
        for (var light : scene.getLights()) {
            Color c = light.getColor();
            totalR += c.getR();
            totalG += c.getG();
            totalB += c.getB();
        }

        if (totalR > 1.0 || totalG > 1.0 || totalB > 1.0) {
            throw new IllegalStateException(
                    "La somme des couleurs des lumières dépasse 1"
            );
        }
    }
}