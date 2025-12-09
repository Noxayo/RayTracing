package org.example.math;

import java.util.Arrays;
import java.util.Objects;

/**
 * Représente un vecteur 3D (x, y, z).
 */
public class Vector {

    private double[] vector;

    /**
     * Crée un vecteur 3D.
     * @param x composante x
     * @param y composante y
     * @param z composante z
     */
    public Vector(double x, double y, double z) {
        this.vector = new double[]{x, y, z};
    }

    /**
     * Retourne les composantes du vecteur.
     * @return tableau [x,y,z]
     */
    public double[] getVector() {
        return vector;
    }
    /**
     * Met à jour les composantes du vecteur.
     * @param vector tableau [x,y,z]
     */
    public void setVector(double[] vector) {
        this.vector = vector;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector1 = (Vector) o;
        return Objects.deepEquals(vector, vector1.vector);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(vector);
    }
}
 
