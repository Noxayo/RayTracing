package org.example.raytracer;

import org.example.Color;

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

    public Color getColor() {
        return color;
    }

    // ========== SETTERS ==========

    public void setColor(Color color) {
        this.color = color;
    }
}