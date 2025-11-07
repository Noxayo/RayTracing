package org.example;

import java.util.Arrays;
import java.util.Objects;

public class Vector {

    private double[] vector;

    public Vector(double x, double y, double z) {
        this.vector = new double[]{x, y, z};
    }

    public double[] getVector() {
        return vector;
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
