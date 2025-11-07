package org.example;

import java.util.Objects;

public class Color {
    private double r, g, b;
    public Color(double r, double g, double b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
    public double getR() {
        return r;
    }
    public void setR(double r) {
        this.r = r;
    }
    public double getG() {
        return g;
    }
    public void setG(double g) {
        this.g = g;
    }
    public double getB() {
        return b;
    }
    public void setB(double b) {
        this.b = b;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Color color = (Color) o;
        return Double.compare(r, color.r) == 0 && Double.compare(g, color.g) == 0 && Double.compare(b, color.b) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, g, b);
    }
}
