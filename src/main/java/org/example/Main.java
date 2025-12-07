package org.example;

import org.example.parsing.SceneFileParser;

import java.util.Arrays;

//public class Main {
//    static void main() {
//        Vector v1 = new Vector(1,1,1);
//        Vector v2 = new Vector(1,1,1);
//
//        AbstractVec3 Calc = new AbstractVec3();
//
//        double[] v3 = Calc.addition(v1.getVector(),v2.getVector());
//
//        v3 = Calc.multiplicationByScalar(4, v3);
//
//        System.out.println(Arrays.toString(v3));
//    } */

    /**
     * Classe principale pour tester le parsing du fichier de scène
     */
    public class Main {
        public static void main(String[] args) {
            System.out.println("========================================");
            System.out.println("    TEST DU PARSER - JALON 2");
            System.out.println("========================================\n");

            // Créer le parser
            SceneFileParser parser = new SceneFileParser();

            try {
                // chemin de fichier .scene (j'ai toujours le message que le chemin d'accès marche pas)
                String sceneFile = "src/main/java/Scenes/test1.scene";

                System.out.println(" Lecture du fichier: " + sceneFile);
                System.out.println("...\n");

                // Parser le fichier
                Scene scene = parser.parse(sceneFile);

                // Afficher les résultats
                System.out.println(" PARSING RÉUSSI !\n");

                System.out.println("========================================");
                System.out.println("    INFORMATIONS DE LA SCÈNE");
                System.out.println("========================================");

                System.out.println("\n DIMENSIONS:");
                System.out.println("   Largeur: " + scene.getWidth() + " px");
                System.out.println("   Hauteur: " + scene.getHeight() + " px");

                System.out.println("\n SORTIE:");
                System.out.println("   Fichier: " + scene.getOutput());

                System.out.println("\n CAMÉRA:");
                System.out.println("   " + scene.getCamera());

                System.out.println("\n COULEUR AMBIANTE:");
                System.out.println("   " + scene.getAmbient());

                System.out.println("\n LUMIÈRES: " + scene.getLights().size());
                int lightNum = 1;
                for (var light : scene.getLights()) {
                    System.out.println("   " + lightNum + ". " + light);
                    lightNum++;
                }

                System.out.println("\n FORMES: " + scene.getShapes().size());
                int shapeNum = 1;
                for (var shape : scene.getShapes()) {
                    System.out.println("   " + shapeNum + ". " + shape);
                    shapeNum++;
                }

                if (scene.getVertices().size() > 0) {
                    System.out.println("\n VERTICES: " + scene.getVertices().size());
                }

                System.out.println("\n========================================");
                System.out.println(" JALON 2 TERMINÉ AVEC SUCCÈS !");
                System.out.println("========================================\n");

            } catch (Exception e) {
                System.err.println("\n ERREUR LORS DU PARSING:");
                System.err.println("   " + e.getMessage());
                System.err.println();
                e.printStackTrace();
            }
        }
    }