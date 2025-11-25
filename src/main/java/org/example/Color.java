package org.example;

import java.util.Arrays;
import java.util.Objects;

/**
 * Représente une couleur RGB avec des valeurs entre 0 et 1
 * Les valeurs qui dépassent 1 sont automatiquement plafonnées à 1
 */
public class Color {
    private double[] color;

    /**
     * Constructeur avec 3 composantes
     * @param r Rouge (entre 0 et 1)
     * @param g Vert (entre 0 et 1)
     * @param b Bleu (entre 0 et 1)
     */
    public Color(double r, double g, double b) {
        // Plafonner les valeurs à 1 si elles dépassent
        this.color = new double[]{
                Math.min(r, 1.0),
                Math.min(g, 1.0),
                Math.min(b, 1.0)
        };
    }

    /**
     * Constructeur par défaut : crée une couleur noire (0, 0, 0)
     */
    public Color() {
        this(0, 0, 0);
    }

    // ========== GETTERS ==========

    /**
     * Retourne le tableau complet [r, g, b]
     */
    public double[] getColor() {
        return color;
    }

    /**
     * Retourne la composante rouge
     * @return valeur entre 0 et 1
     */
    public double getR() {
        return color[0];
    }

    /**
     * Retourne la composante verte
     * @return valeur entre 0 et 1
     */
    public double getG() {
        return color[1];
    }

    /**
     * Retourne la composante bleue
     * @return valeur entre 0 et 1
     */
    public double getB() {
        return color[2];
    }

    // ========== SETTERS ==========

    public void setColor(double[] color) {
        this.color = color;
    }

    /**
     * Modifie la composante rouge
     */
    public void setR(double r) {
        this.color[0] = Math.min(r, 1.0);
    }

    /**
     * Modifie la composante verte
     */
    public void setG(double g) {
        this.color[1] = Math.min(g, 1.0);
    }

    /**
     * Modifie la composante bleue
     */
    public void setB(double b) {
        this.color[2] = Math.min(b, 1.0);
    }

    // ========== MÉTHODES ==========

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Color color1 = (Color) o;
        return Objects.deepEquals(color, color1.color);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(color);
    }

    @Override
    public String toString() {
        return String.format("Color(r=%.2f, g=%.2f, b=%.2f)",
                color[0], color[1], color[2]);
    }

    /**
     * Convertit la couleur en format RGB entier pour l'écriture d'images
     * Convertit les valeurs 0-1 en 0-255
     * @return Un entier RGB
     */
    public int toRGB() {
        int red = (int) Math.round(this.color[0] * 255);
        int green = (int) Math.round(this.color[1] * 255);
        int blue = (int) Math.round(this.color[2] * 255);

        return ((red & 0xff) << 16)
                + ((green & 0xff) << 8)
                + (blue & 0xff);
    }
}


/*package org.example;

import java.util.Arrays;
import java.util.Objects;

public class Color {
    private double[] color;

    public Color(double r, double g, double b) {
        this.color = new double[]{r, g, b};
    }

    public double[] getColor() {
        return color;
    }
    public void setColor(double[] color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Color color1 = (Color) o;
        return Objects.deepEquals(color, color1.color);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(color);
    }

    public int toRGB() {
        int red = (int) Math.round(this.color[0] * 255);
        int green = (int) Math.round(this.color[1] * 255);
        int blue = (int) Math.round(this.color[2] * 255);

        return ((red & 0xff) << 16)
                + ((green & 0xff) << 8)
                + (blue & 0xff);
    }
}
*/

