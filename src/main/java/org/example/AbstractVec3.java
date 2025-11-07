package org.example;

public class AbstractVec3 {

    public double[] addition(double[] a, double[] b){
        //Vérification de la longueur des arrays
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
        //Vérification de la longueur des arrays
        if (a.length != 3 || b.length != 3) {
            throw new IllegalArgumentException("Input arrays must have a length of 3.");
        }

        double[] result = new double[3];
        result[0] = a[0] - b[0];
        result[1] = a[1] - b[1];
        result[2] = a[2] - b[2];

        return result;
    }

    public double[] multiplicationByScalar(double x, double[] a){
        //Vérification de la longueur des arrays
        if (a.length != 3){
            throw new IllegalArgumentException("Input arrays must have a length of 3.");
        }
        double[] result = new double[3];
        result[0] = x * a[0];
        result[1] = x * a[1];
        result[2] = x * a[2];

        return result;
    }

    public double scalarProduct(double[] a, double[] b){
        //Vérification de la longueur des arrays
        if (a.length != 3 || b.length != 3) {
            throw new IllegalArgumentException("Input arrays must have a length of 3.");
        }
        double result = a[0] * b[0] + a[1] * b[1] + a[2] * b[2];

        return result;
    }

    public double[] vectorialProduct(double[] a, double[] b){
        //Vérification de la longueur des arrays
        if (a.length != 3 || b.length != 3) {
            throw new IllegalArgumentException("Input arrays must have a length of 3.");
        }
        double[] result = new double[3];
        result[0] = a[1] * b[2] - a[2] * b[1];
        result[1] = a[2] * b[0] - a[0] * b[2];
        result[2] = a[0] * b[1] - a[1] * b[0];

        return result;
    }

    public double[] schurProduct(double[] a, double[] b){
        //Vérification de la longueur des arrays
        if (a.length != 3 || b.length != 3) {
            throw new IllegalArgumentException("Input arrays must have a length of 3.");
        }
        double[] result = new double[3];
        result[0] = a[0] * b[0];
        result[1] = a[1] * b[1];
        result[2] = a[2] * b[2];

        return result;
    }

    public double length(double[] a){
        //Vérification de la longueur des arrays
        if (a.length != 3){
            throw new IllegalArgumentException("Input arrays must have a length of 3.");
        }
        double result = Math.sqrt(a[0] * a[0] + a[1] * a[1] + a[2] * a[2]);

        return result;
    }

    public double[] normalization(double[] a){
        if (a.length != 3){
            throw new IllegalArgumentException("Input arrays must have a length of 3.");
        }
        double[] result = multiplicationByScalar(1/length(a), a);

        return result;
    }

}
