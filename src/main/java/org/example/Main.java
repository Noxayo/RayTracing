package org.example;

import org.example.math.Scene;
import org.example.parsing.SceneFileParser;

/**
 * Point d'entrée minimal : lit un fichier .scene et génère l'image.
 * Utilisation: java -cp target/classes org.example.Main <chemin/vers/fichier.scene>
 */
public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java -cp target/classes org.example.Main <fichier.scene>");
            System.exit(1);
        }

        String sceneFile = args[0];
        SceneFileParser parser = new SceneFileParser();
        try {
            Scene scene = parser.parse(sceneFile);
            org.example.raytracer.Renderer renderer = new org.example.raytracer.Renderer();
            renderer.render(scene);
            System.out.println("Image écrite dans: " + scene.getOutput());
        } catch (Exception e) {
            System.err.println("Erreur: " + e.getMessage());
            e.printStackTrace();
            System.exit(2);
        }
    }
}