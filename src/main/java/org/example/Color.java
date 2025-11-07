package org.example;

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
}
