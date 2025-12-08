package org.example.geometry;

import org.example.Color;
import org.example.Point;

/**
 * Représente un triangle défini par 3 sommets
 * Format dans le fichier: tri indice1 indice2 indice3
 */
public class Triangle extends Shape {
    private Point v0;  // Premier sommet
    private Point v1;  // Deuxième sommet
    private Point v2;  // Troisième sommet

    /**
     * Constructeur
     * @param v0 Premier sommet
     * @param v1 Deuxième sommet
     * @param v2 Troisième sommet
     * @param diffuse La couleur diffuse
     * @param specular La couleur spéculaire
     */
    public Triangle(Point v0, Point v1, Point v2, Color diffuse, Color specular) {
        super(diffuse, specular);
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
    }

    // ========== GETTERS ==========

    public Point getV0() {
        return v0;
    }

    public Point getV1() {
        return v1;
    }

    public Point getV2() {
        return v2;
    }

    // ========== SETTERS ==========

    public void setV0(Point v0) {
        this.v0 = v0;
    }

    public void setV1(Point v1) {
        this.v1 = v1;
    }

    public void setV2(Point v2) {
        this.v2 = v2;
    }

    @Override
    public String toString() {
        return "Triangle{v0=" + v0 +
                ", v1=" + v1 +
                ", v2=" + v2 +
                ", diffuse=" + diffuse +
                ", specular=" + specular + "}";
    }

    @Override
    public org.example.raytracer.Intersection intersect(org.example.raytracer.Ray ray) {
        // Möller–Trumbore algorithm
        org.example.AbstractVec3 V = new org.example.AbstractVec3();
        double[] O = ray.getOrigin();
        double[] D = ray.getDirection();
        double[] A = v0.getPoint();
        double[] B = v1.getPoint();
        double[] C = v2.getPoint();

        double[] e1 = V.subtraction(B, A);
        double[] e2 = V.subtraction(C, A);
        double[] pvec = V.vectorialProduct(D, e2);
        double det = V.scalarProduct(e1, pvec);
        if (Math.abs(det) < 1e-8) return null;
        double invDet = 1.0 / det;

        double[] tvec = V.subtraction(O, A);
        double u = V.scalarProduct(tvec, pvec) * invDet;
        if (u < 0 || u > 1) return null;

        double[] qvec = V.vectorialProduct(tvec, e1);
        double v = V.scalarProduct(D, qvec) * invDet;
        if (v < 0 || u + v > 1) return null;

        double t = V.scalarProduct(e2, qvec) * invDet;
        if (t <= 1e-6) return null;

        double[] P = V.addition(O, V.multiplicationByScalar(t, D));
        double[] N = V.normalization(V.vectorialProduct(e1, e2));
        return new org.example.raytracer.Intersection(this, t, P, N);
    }
}
