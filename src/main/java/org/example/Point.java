package org.example;

import java.util.Arrays;
import java.util.Objects;

public class Point {
    private double[] point;

    public Point(double x, double y, double z) {
        this.point = new double[]{x, y, z};
    }

    public double[] getPoint() {
        return point;
    }
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
