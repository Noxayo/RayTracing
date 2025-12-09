package org.example.math;

import java.util.Arrays;
import java.util.Objects;

/**
 * Représente un point 3D (x, y, z).
 */
public class Point {
    private double[] point;

    /**
     * Crée un point 3D.
     * @param x abscisse
     * @param y ordonnée
     * @param z altitude
     */
    public Point(double x, double y, double z) {
        this.point = new double[]{x, y, z};
    }

    /**
     * Retourne les coordonnées du point.
     * @return tableau [x,y,z]
     */
    public double[] getPoint() {
        return point;
    }
    /**
     * Remplace les coordonnées du point.
     * @param point tableau [x,y,z]
     */
    public void setPoint(double[] point) {
        this.point = point;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Point point1 = (Point) o;
        return Objects.deepEquals(point, point1.point);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(point);
    }
}
