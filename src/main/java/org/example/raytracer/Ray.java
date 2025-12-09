package org.example.raytracer;

/**
 * Représente un rayon en 3D, défini par une origine et une direction normalisée.
 */
public class Ray {
    private final double[] origin;
    private final double[] direction; // normalisée

    /**
     * Crée un nouveau rayon.
     * @param origin point d'origine du rayon (tableau [x,y,z])
     * @param direction direction du rayon, idéalement normalisée (tableau [x,y,z])
     */
    public Ray(double[] origin, double[] direction) {
        this.origin = origin;
        this.direction = direction;
    }

    /**
     * Retourne l'origine du rayon.
     * @return tableau [x,y,z]
     */
    public double[] getOrigin() { return origin; }

    /**
     * Retourne la direction du rayon.
     * @return tableau [x,y,z] (normalisé de préférence)
     */
    public double[] getDirection() { return direction; }
}
