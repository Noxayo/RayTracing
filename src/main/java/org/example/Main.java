package org.example;

import java.util.Arrays;

public class Main {
    static void main() {
        Vector v1 = new Vector(1,1,1);
        Vector v2 = new Vector(1,1,1);

        AbstractVec3 Calc = new AbstractVec3();

        double[] v3 = Calc.addition(v1.getVector(),v2.getVector());

        System.out.println(Arrays.toString(v3));
    }
}
