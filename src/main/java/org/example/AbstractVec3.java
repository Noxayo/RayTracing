package org.example;

public class AbstractVec3 {

    public double[] addition(double[] a, double[] b){
        //Vérification de la longueure des arrays
        if (a.length != 3 || b.length != 3) {
            throw new IllegalArgumentException("Input arrays must have a length of 3.");
        }

        double[] result = new double[3];
        result[0] = a[0] + b[0];
        result[1] = a[1] + b[1];
        result[2] = a[2] + b[2];

        return result;
    }

    public double[] subtraction(double[] a, double[] b){
        if (a.length != 3 || b.length != 3) {
            throw new IllegalArgumentException("Input arrays must have a length of 3.");
        }

        double[] result = new double[3];
        result[0] = a[0] - b[0];
        result[1] = a[1] - b[1];
        result[2] = a[2] - b[2];

        return result;
    }

}
