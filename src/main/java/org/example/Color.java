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

    public int toRGB() {
        int red = (int) Math.round(this.color[0] * 255);
        int green = (int) Math.round(this.color[1] * 255);
        int blue = (int) Math.round(this.color[2] * 255);

        return ((red & 0xff) << 16)
                + ((green & 0xff) << 8)
                + (blue & 0xff);
    }
}
