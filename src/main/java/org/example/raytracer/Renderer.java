package org.example.raytracer;

import org.example.geometry.Shape;
import org.example.math.AbstractVec3;
import org.example.math.Camera;
import org.example.math.Color;
import org.example.math.Scene;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Moteur de rendu de base (ray tracing) pour la scène.
 */
public class Renderer {
    private final AbstractVec3 V = new AbstractVec3();

    /**
     * Rendre l'image de la scène et l'écrire dans le fichier de sortie.
     * @param scene scène à rendre
     * @throws IOException en cas d'erreur d'écriture du fichier
     */
    public void render(Scene scene) throws IOException {
        int width = scene.getWidth();
        int height = scene.getHeight();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Camera cam = scene.getCamera();
        double[] eye = cam.getLookFrom().getPoint();
        double[] lookAt = cam.getLookAt().getPoint();
        double[] up = cam.getUp().getVector();

        double[] forward = V.normalization(V.subtraction(lookAt, eye));
        double[] right = V.normalization(V.vectorialProduct(forward, up));
        double[] trueUp = V.vectorialProduct(right, forward);

        double fovRad = Math.toRadians(cam.getFov());
        double aspect = (double) width / (double) height;
        double viewportHeight = 2.0 * Math.tan(fovRad / 2.0);
        double viewportWidth = viewportHeight * aspect;

        for (int y = 0; y < height; y++) {
            double v = (1 - ((y + 0.5) / height)) * viewportHeight - viewportHeight / 2.0;
            for (int x = 0; x < width; x++) {
                double u = ((x + 0.5) / width) * viewportWidth - viewportWidth / 2.0;

                double[] dir = V.normalization(
                        V.addition(
                                V.addition(forward, V.multiplicationByScalar(u, right)),
                                V.multiplicationByScalar(v, trueUp)
                        )
                );

                Color pixelColor = trace(scene, eye, dir);
                image.setRGB(x, y, pixelColor.toRGB());
            }
        }

        File out = new File(scene.getOutput());
        ensureParentDirs(out);
        ImageIO.write(image, getExt(scene.getOutput()), out);
    }

    /**
     * Crée les dossiers parents du fichier de sortie si nécessaire.
     */
    private void ensureParentDirs(File f) {
        File parent = f.getParentFile();
        if (parent != null && !parent.exists()) parent.mkdirs();
    }

    /**
     * Extrait l'extension d'un nom de fichier.
     * @param filename nom de fichier
     * @return extension sans le point (par défaut "png")
     */
    private String getExt(String filename) {
        int i = filename.lastIndexOf('.');
        return (i >= 0) ? filename.substring(i + 1) : "png";
    }

    /**
     * Lance un rayon principal et calcule la couleur vue.
     * @param scene scène courante
     * @param origin origine du rayon
     * @param dir direction du rayon (normalisée)
     * @return couleur du pixel
     */
    private Color trace(Scene scene, double[] origin, double[] dir) {
        Intersection hit = nearestHit(scene, origin, dir);
        if (hit == null) {
            return new Color(0, 0, 0);
        }
        // Appel initial avec profondeur 1
        return scene.computeColor(hit, origin, 1); // ANCIEN: return scene.computeColor(hit, origin);
    }
    /* Ancienne méthode pour jalon 5 private Color trace(Scene scene, double[] origin, double[] dir) {

        Intersection hit = nearestHit(scene, origin, dir);
        if (hit == null) {
            return new Color(0, 0, 0);
        }
        return scene.computeColor(hit, origin);
    }*/

    /**
     * Recherche l'intersection la plus proche dans la scène.
     * @param scene scène courante
     * @param origin origine du rayon
     * @param dir direction du rayon
     * @return intersection la plus proche ou null
     */
    private Intersection nearestHit(Scene scene, double[] origin, double[] dir) {
        double closestT = Double.POSITIVE_INFINITY;
        Intersection best = null;
        Ray ray = new Ray(origin, dir);
        for (Shape s : scene.getShapes()) {
            Intersection h = s.intersect(ray);
            if (h != null && h.getT() < closestT) {
                closestT = h.getT();
                best = h;
            }
        }
        return best;
    }

    // clamp déplacé dans Intersection
}
