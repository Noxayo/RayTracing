package org.example;

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
     * Constructeur complet
     */
    public Camera(Point lookFrom, Point lookAt, Vector up, double fov) {
        this.lookFrom = lookFrom;
        this.lookAt = lookAt;
        this.up = up;
        this.fov = fov;
    }

    // ========== GETTERS ==========

    public Point getLookFrom() {
        return lookFrom;
    }

    public Point getLookAt() {
        return lookAt;
    }

    public Vector getUp() {
        return up;
    }

    public double getFov() {
        return fov;
    }

    // ========== SETTERS ==========

    public void setLookFrom(Point lookFrom) {
        this.lookFrom = lookFrom;
    }

    public void setLookAt(Point lookAt) {
        this.lookAt = lookAt;
    }

    public void setUp(Vector up) {
        this.up = up;
    }

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


/*
public class Camera {
    private double x, y, z; // Position de l'œil
    private double u, v, w; // Point visé
    private double m, n, o; // Direction vers le haut
    private double fov; // Angle de vue

    // Constructeur
    public Camera(double x, double y, double z, double u, double v, double w, double m, double n, double o, double fov) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.u = u;
        this.v = v;
        this.w = w;
        this.m = m;
        this.n = n;
        this.o = o;
        this.fov = fov;
    }

    // Getters
    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }
    public double getU() { return u; }
    public double getV() { return v; }
    public double getW() { return w; }
    public double getM() { return m; }
    public double getN() { return n; }
    public double getO() { return o; }
    public double getFov() { return fov; }
}

 */
