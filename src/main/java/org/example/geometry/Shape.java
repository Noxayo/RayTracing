package org.example.geometry;

import org.example.math.Color;

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
    protected double shininess; // Exposant de brillance (Phong)

    /**
     * Constructeur
     * @param diffuse La couleur diffuse
     * @param specular La couleur spéculaire
     */
    public Shape(Color diffuse, Color specular) {
        this.diffuse = diffuse;
        this.specular = specular;
        this.shininess = 0.0;
    }

    // ========== GETTERS ==========

    /**
     * Couleur diffuse de la forme.
     */
    public Color getDiffuse() {
        return diffuse;
    }

    /**
     * Couleur spéculaire (utilisée pour la réflexion/Phong).
     */
    public Color getSpecular() {
        return specular;
    }

    /**
     * Exposant de brillance de Phong.
     */
    public double getShininess() { return shininess; }

    // ========== SETTERS ==========

    /**
     * Définit la couleur diffuse.
     */
    public void setDiffuse(Color diffuse) {
        this.diffuse = diffuse;
    }

    /**
     * Définit la couleur spéculaire.
     */
    public void setSpecular(Color specular) {
        this.specular = specular;
    }

    /**
     * Définit l'exposant de brillance.
     */
    public void setShininess(double shininess) { this.shininess = shininess; }

    /**
     * Calcule l'intersection entre cette forme et un rayon.
     * @param ray le rayon à tester
     * @return l'intersection trouvée ou null si aucune
     */
    public abstract org.example.raytracer.Intersection intersect(org.example.raytracer.Ray ray);
}