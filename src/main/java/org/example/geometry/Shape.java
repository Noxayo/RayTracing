package org.example.geometry;

import org.example.Color;

/**
 * Classe abstraite représentant une forme géométrique
 * Sera héritée par Sphere, Triangle et Plane
 *
 * IMPORTANT: Chaque forme stocke ses couleurs diffuse et specular
 * Ces couleurs sont définies AVANT la création de la forme dans le fichier
 */
public abstract class Shape {
    protected Color diffuse;   // Couleur de l'objet
    protected Color specular;  // Couleur de la réflexion (effet miroir)

    /**
     * Constructeur
     * @param diffuse La couleur diffuse
     * @param specular La couleur spéculaire
     */
    public Shape(Color diffuse, Color specular) {
        this.diffuse = diffuse;
        this.specular = specular;
    }

    // ========== GETTERS ==========

    public Color getDiffuse() {
        return diffuse;
    }

    public Color getSpecular() {
        return specular;
    }

    // ========== SETTERS ==========

    public void setDiffuse(Color diffuse) {
        this.diffuse = diffuse;
    }

    public void setSpecular(Color specular) {
        this.specular = specular;
    }
}