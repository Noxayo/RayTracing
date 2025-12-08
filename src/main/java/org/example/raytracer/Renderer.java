package org.example.raytracer;

import org.example.*;
import org.example.geometry.Shape;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Renderer {
    private final AbstractVec3 V = new AbstractVec3();

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

    private void ensureParentDirs(File f) {
        File parent = f.getParentFile();
        if (parent != null && !parent.exists()) parent.mkdirs();
    }

    private String getExt(String filename) {
        int i = filename.lastIndexOf('.');
        return (i >= 0) ? filename.substring(i + 1) : "png";
    }

    private Color trace(Scene scene, double[] origin, double[] dir) {
        Intersection hit = nearestHit(scene, origin, dir);
        if (hit == null) {
            return new Color(0, 0, 0);
        }
        return scene.computeColor(hit, origin);
    }

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

    // clamp moved to Intersection
}
