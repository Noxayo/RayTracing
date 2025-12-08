package org.example.raytracer;

public class Ray {
    private final double[] origin;
    private final double[] direction; // normalized

    public Ray(double[] origin, double[] direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public double[] getOrigin() { return origin; }
    public double[] getDirection() { return direction; }
}
