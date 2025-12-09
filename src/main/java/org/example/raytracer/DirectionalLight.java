package org.example.raytracer;

import org.example.math.AbstractVec3;
import org.example.math.Color;
import org.example.math.Vector;

/**
 * Lumière directionnelle (comme le soleil)
 * La lumière vient d'une direction, pas d'un point précis
 * Format dans le fichier: directional x y z r g b
 */
public class DirectionalLight extends AbstractLight {
    private Vector direction;  // Direction de la lumière

    /**
     * Constructeur
     * @param direction La direction de la lumière (vecteur)
     * @param color La couleur de la lumière
     */
    public DirectionalLight(Vector direction, Color color) {
        super(color);
        // INVERSER la direction
        AbstractVec3 calc = new AbstractVec3();
        double[] dirArray = direction.getVector();
        double[] normalized = calc.normalization(dirArray);
        double[] inverted = calc.multiplicationByScalar(-1.0, normalized);
        this.direction = new Vector(inverted[0], inverted[1], inverted[2]);
    }

    // ========== GETTERS ==========

    /**
     * Retourne la direction (normalisée et inversée lors de la construction).
     */
    public Vector getDirection() {
        return direction;
    }

    // ========== SETTERS ==========

    /**
     * Définit la direction de la lumière.
     */
    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "DirectionalLight{direction=" + direction +
                ", color=" + color + "}";
    }
}