package org.example.math;

/**
 * Utilitaires d'algèbre linéaire pour vecteurs 3D (double[3]).
 * Toutes les méthodes supposent des tableaux de longueur 3 et lèvent une exception sinon.
 */
public class AbstractVec3 {

    /**
     * Addition composante par composante: a + b.
     * @param a vecteur 3D
     * @param b vecteur 3D
     * @return nouveau vecteur 3D résultant
     * @throws IllegalArgumentException si les longueurs ne valent pas 3
     */
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

    /**
     * Soustraction composante par composante: a - b.
     * @param a vecteur 3D
     * @param b vecteur 3D
     * @return nouveau vecteur 3D résultant
     * @throws IllegalArgumentException si les longueurs ne valent pas 3
     */
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

    /**
     * Multiplication scalaire: x · a.
     * @param x scalaire
     * @param a vecteur 3D
     * @return nouveau vecteur 3D résultant
     * @throws IllegalArgumentException si la longueur ne vaut pas 3
     */
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

    /**
     * Produit scalaire: a · b.
     * @param a vecteur 3D
     * @param b vecteur 3D
     * @return scalaire (double)
     * @throws IllegalArgumentException si les longueurs ne valent pas 3
     */
    public double scalarProduct(double[] a, double[] b){
        //Vérification de la longueur des arrays
        if (a.length != 3 || b.length != 3) {
            throw new IllegalArgumentException("Input arrays must have a length of 3.");
        }
        double result = a[0] * b[0] + a[1] * b[1] + a[2] * b[2];

        return result;
    }

    /**
     * Produit vectoriel: a × b.
     * @param a vecteur 3D
     * @param b vecteur 3D
     * @return nouveau vecteur 3D orthogonal à a et b
     * @throws IllegalArgumentException si les longueurs ne valent pas 3
     */
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

    /**
     * Produit de Schur (Hadamard): a ⊙ b (composante par composante).
     * @param a vecteur 3D
     * @param b vecteur 3D
     * @return nouveau vecteur 3D résultant
     * @throws IllegalArgumentException si les longueurs ne valent pas 3
     */
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

    /**
     * Norme euclidienne ||a||.
     * @param a vecteur 3D
     * @return longueur (double)
     * @throws IllegalArgumentException si la longueur ne vaut pas 3
     */
    public double length(double[] a){
        //Vérification de la longueur des arrays
        if (a.length != 3){
            throw new IllegalArgumentException("Input arrays must have a length of 3.");
        }
        double result = Math.sqrt(a[0] * a[0] + a[1] * a[1] + a[2] * a[2]);

        return result;
    }

    /**
     * Normalise un vecteur: a / ||a||.
     * @param a vecteur 3D
     * @return nouveau vecteur 3D normalisé (peut contenir NaN si a est nul)
     * @throws IllegalArgumentException si la longueur ne vaut pas 3
     */
    public double[] normalization(double[] a){
        if (a.length != 3){
            throw new IllegalArgumentException("Input arrays must have a length of 3.");
        }
        double[] result = multiplicationByScalar(1/length(a), a);

        return result;
    }
    /** JALON 6 BONUS
     * Calcule le vecteur réfléchi R pour un vecteur incident D et une normale N.
     * Formule: R = D - 2 * (D . N) * N
     * D est la direction du rayon incident (ray.getDirection()).
     * @param incident Direction du rayon (D)
     * @param normal La normale à la surface (N)
     * @return Le vecteur réfléchi R
     */
    /**
     * JALON 6 BONUS: calcule le vecteur réfléchi R pour un vecteur incident D et une normale N.
     * Formule: R = D - 2 · (D · N) · N.
     * @param incident direction incidente D
     * @param normal normale de surface N (normalisée de préférence)
     * @return vecteur réfléchi R
     */
    public double[] reflect(double[] incident, double[] normal) {
        // 1. Calculer le produit scalaire (D . N)
        double dotProduct = scalarProduct(incident, normal);

        // 2. Calculer 2 * (D . N) * N
        double factor = 2.0 * dotProduct;
        double[] scaledNormal = multiplicationByScalar(factor, normal);

        // 3. Calculer R = D - (2 * (D . N) * N)
        return subtraction(incident, scaledNormal);
    }
}
