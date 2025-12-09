package org.example.math;

/**
 * Représente la caméra dans la scène 3D
 * Stocke la position, la direction et l'angle de vue
 */
public class Camera {
    private Point lookFrom;  // Position de l'œil (où est la caméra)
    private Point lookAt;    // Point visé par la caméra
    private Vector up;       // Direction "vers le haut"
    private double fov;      // Angle de vue en degrés (field of view)

    /**
     * Constructeur complet.
     * @param lookFrom position de l'œil
     * @param lookAt point visé
     * @param up vecteur «haut» de référence
     * @param fov angle de vue en degrés
     */
    public Camera(Point lookFrom, Point lookAt, Vector up, double fov) {
        this.lookFrom = lookFrom;
        this.lookAt = lookAt;
        this.up = up;
        this.fov = fov;
    }

    // ========== GETTERS ==========

    /**
     * Retourne la position de la caméra.
     */
    public Point getLookFrom() {
        return lookFrom;
    }

    /**
     * Retourne le point regardé par la caméra.
     */
    public Point getLookAt() {
        return lookAt;
    }

    /**
     * Retourne le vecteur «haut».
     */
    public Vector getUp() {
        return up;
    }

    /**
     * Retourne l'angle de vue (degrés).
     */
    public double getFov() {
        return fov;
    }

    // ========== SETTERS ==========

    /**
     * Définit la position de la caméra.
     */
    public void setLookFrom(Point lookFrom) {
        this.lookFrom = lookFrom;
    }

    /**
     * Définit le point visé.
     */
    public void setLookAt(Point lookAt) {
        this.lookAt = lookAt;
    }

    /**
     * Définit le vecteur «haut».
     */
    public void setUp(Vector up) {
        this.up = up;
    }

    /**
     * Définit l'angle de vue (degrés).
     */
    public void setFov(double fov) {
        this.fov = fov;
    }

    @Override
    public String toString() {
        return "Camera{lookFrom=" + lookFrom +
                ", lookAt=" + lookAt +
                ", up=" + up +
                ", fov=" + fov + "°}";
    }
}
