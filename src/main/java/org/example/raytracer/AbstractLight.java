package org.example.raytracer;

import org.example.math.Color;

/**
 * Classe abstraite représentant une source de lumière
 * Sera héritée par DirectionalLight et PointLight
 */
public abstract class AbstractLight {
    protected Color color;  // Couleur de la lumière (r, g, b)

    /**
     * Constructeur
     * @param color La couleur de la lumière
     */
    public AbstractLight(Color color) {
        this.color = color;
    }

    // ========== GETTERS ==========

    /**
     * Retourne la couleur de la lumière.
     */
    public Color getColor() {
        return color;
    }

    // ========== SETTERS ==========

    /**
     * Définit la couleur de la lumière.
     */
    public void setColor(Color color) {
        this.color = color;
    }
}