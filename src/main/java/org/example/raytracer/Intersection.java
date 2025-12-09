package org.example.raytracer;

import org.example.Color;
import org.example.geometry.Shape;

public class Intersection {
    private final Shape shape;
    private final double t;
    private final double[] point;
    private final double[] normal; // normalized
    // L'AbstractVec3 pour tous les calculs
    private final org.example.AbstractVec3 V = new org.example.AbstractVec3();

    public Intersection(Shape shape, double t, double[] point, double[] normal) {
        this.shape = shape;
        this.t = t;
        this.point = point;
        this.normal = normal;
    }

    public Shape getShape() { return shape; }
    public double getT() { return t; }
    public double[] getPoint() { return point; }
    public double[] getNormal() { return normal; }

    // Dans Intersection.java (REMPLACEZ l'ancienne méthode computeColor)

    /**
     * Calcule la couleur à l'intersection, y compris la réflexion récursive (Jalon 6).
     * @param scene La scène
     * @param origin L'origine du rayon (œil ou intersection précédente)
     * @param depth La profondeur de récursion actuelle (commence à 1)
     * @return La couleur finale
     */
    public org.example.Color computeColor(org.example.Scene scene, double[] origin, int depth) {

        double[] N = normal;

        // 1. Calcul de D (Direction du rayon INCIDENT: origine -> point)
        double[] D = V.normalization(V.subtraction(point, origin));

        // 2. Calcul de viewDir (Direction de VUE pour Phong: point -> origin)
        // L'ancien 'eye' est maintenant 'origin'.
        double[] viewDir = V.normalization(V.subtraction(origin, point));

        double[] baseArr = shape.getDiffuse().getColor();
        double[] specArr = shape.getSpecular().getColor();
        double shininess = shape.getShininess();

        double[] ambArr = scene.getAmbient().getColor();
        double r = clamp01(ambArr[0] * baseArr[0]);
        double g = clamp01(ambArr[1] * baseArr[1]);
        double b = clamp01(ambArr[2] * baseArr[2]);

        // =========================================================
        //  DÉBUT DU CODE JALON 5 (Lumière Directe)
        // =========================================================
        for (org.example.raytracer.AbstractLight light : scene.getLights()) {
            double[] L;
            double[] lightColor = light.getColor().getColor();
            double maxDist = Double.POSITIVE_INFINITY;

            // Setup lumière (PointLight vs DirectionalLight)
            if (light instanceof org.example.raytracer.DirectionalLight dl) {
                double[] dirL = dl.getDirection().getVector();
                L = V.normalization(dirL);
            } else if (light instanceof org.example.raytracer.PointLight pl) {
                double[] lp = pl.getPosition().getPoint();
                double[] toLight = V.subtraction(lp, point);
                maxDist = V.length(toLight);
                L = V.normalization(toLight);
            } else {
                continue;
            }

            // Shadow ray: offset point slightly along normal to avoid self-intersection
            double[] shadowOrigin = V.addition(point, V.multiplicationByScalar(1e-4, N));
            org.example.raytracer.Ray shadowRay = new org.example.raytracer.Ray(shadowOrigin, L);
            if (isOccluded(scene, shadowRay, maxDist)) {
                continue; // in shadow for this light
            }

            // Diffuse (Lambert)
            double ndotl = Math.max(0.0, V.scalarProduct(N, L));
            r = clamp01(r + baseArr[0] * lightColor[0] * ndotl);
            g = clamp01(g + baseArr[1] * lightColor[1] * ndotl);
            b = clamp01(b + baseArr[2] * lightColor[2] * ndotl);

            // Specular (Phong)
            if (shininess > 0) {
                double[] minusL = V.multiplicationByScalar(-1.0, L);
                //  CORRECTION JALON 6: reflect est maintenant dans V (AbstractVec3)
                double[] R = V.reflect(minusL, N);
                double rv = Math.max(0.0, V.scalarProduct(R, viewDir));
                double spec = Math.pow(rv, shininess);
                r = clamp01(r + specArr[0] * lightColor[0] * spec);
                g = clamp01(g + specArr[1] * lightColor[1] * spec);
                b = clamp01(b + specArr[2] * lightColor[2] * spec);
            }
        }
        // =========================================================
        //  FIN DU CODE JALON 5
        // =========================================================

        // Stocker la couleur directe dans un objet modifiable (nécessaire pour la fonction add/multiply)
        org.example.Color finalColor = new org.example.Color(r, g, b);

        // --- LOGIQUE DE RÉFLEXION (Jalon 6) ---
        int maxDepth = scene.getMaxdepth();
        org.example.Color specular = shape.getSpecular();

        // Condition d'arrêt : profondeur max atteinte OU l'objet n'est pas spéculaire
        if (depth < maxDepth && !specular.isBlack()) {

            // 1. Calculer le vecteur réfléchi (R)
            // D est la direction du rayon incident que nous avons calculé au début.
            double[] reflectedDir = V.reflect(D, N);

            // 2. Créer le nouveau rayon (origine: le point d'intersection 'point')
            org.example.raytracer.Ray reflectedRay = new org.example.raytracer.Ray(point, reflectedDir);

            // 3. Trouver la prochaine intersection
            org.example.raytracer.Intersection hitPrime = findClosestIntersection(scene, reflectedRay);

            if (hitPrime != null) {
                // 4. Appel récursif : calcule la couleur vue par le rayon réfléchi (c')
                org.example.Color reflectedColor = hitPrime.computeColor(scene, point, depth + 1);

                // 5. Ajouter la contribution: c = c + specular * c'
                Color reflectedContribution = reflectedColor.multiply(specular);
                finalColor.add(reflectedContribution);
            }
        }

        // 6. Plafonnement final des couleurs (très important !)
        finalColor.clamp();

        return finalColor;
    }
//
//    // Dans Intersection.java
//// REMPLACEZ l'ancienne méthode computeColor par celle-ci
//
//    /**
//     * Calcule la couleur à l'intersection en incluant la lumière directe (Jalon 5)
//     * et la lumière réfléchie (Jalon 6 Bonus) de manière récursive.
//     * @param scene La scène
//     * @param origin L'origine du rayon (œil ou intersection précédente)
//     * @param depth La profondeur de récursion actuelle (commence à 1)
//     * @return La couleur finale
//     */
//    public org.example.Color computeColor(org.example.Scene scene, double[] origin, int depth) {
//        double[] N = normal;
//
//        // Direction du RAYON INCIDENT (point d'origine -> point d'intersection)
//        // C'est ce vecteur que nous allons réfléchir
//        double[] D = V.normalization(V.subtraction(point, origin));
//
//        // Direction de VUE (pour Phong) : point d'intersection -> œil
//        double[] viewDir = V.normalization(V.subtraction(origin, point));
//
//        double[] baseArr = shape.getDiffuse().getColor();
//        double[] specArr = shape.getSpecular().getColor();
//        double shininess = shape.getShininess();
//
//        double[] ambArr = scene.getAmbient().getColor();
//        double r = clamp01(ambArr[0] * baseArr[0]);
//        double g = clamp01(ambArr[1] * baseArr[1]);
//        double b = clamp01(ambArr[2] * baseArr[2]);
//
//        // --- CALCUL DE LA COULEUR DIRECTE (Diffuse + Specular) ---
//        for (org.example.raytracer.AbstractLight light : scene.getLights()) {
//            // [CODE JALON 5 EXISTANT] :
//            // Détermination de L (direction/position lumière)
//            // Calcul des ombres (isOccluded)
//            // Calcul des contributions Diffuse et Specular (qui ajoutent à r, g, b)
//            // Assurez-vous que votre Jalon 5 fonctionne ici et que r, g, b contiennent
//            // la couleur après la lumière directe.
//
//            // Exemple simplifié (à remplacer par votre code Jalon 5 complet)
//            // double[] L = ...; // Direction de la lumière
//            // if (!isOccluded(scene, new Ray(point, L), maxDist) {
//            //     // Mise à jour de r, g, b pour Diffuse et Specular
//            // }
//        }
//
//        // Stocker la couleur directe dans un objet modifiable
//        org.example.Color finalColor = new org.example.Color(r, g, b);
//
//        // --- LOGIQUE DE RÉFLEXION (Jalon 6) ---
//
//        // Condition d'arrêt : profondeur max atteinte OU l'objet n'est pas spéculaire
//        if (depth < scene.getMaxdepth() && !shape.getSpecular().isBlack()) {
//
//            // 1. Calculer le vecteur réfléchi (R)
//            double[] reflectedDir = V.reflect(D, N);
//
//            // 2. Créer le nouveau rayon (origine: le point d'intersection 'point')
//            org.example.raytracer.Ray reflectedRay = new org.example.raytracer.Ray(point, reflectedDir);
//
//            // 3. Trouver la prochaine intersection
//            org.example.raytracer.Intersection hitPrime = findClosestIntersection(scene, reflectedRay);
//
//            if (hitPrime != null) {
//                // 4. Appel récursif (c')
//                org.example.Color reflectedColor = hitPrime.computeColor(scene, point, depth + 1);
//
//                // 5. Ajouter la contribution: c = c + specular * c'
//                Color reflectedContribution = reflectedColor.multiply(shape.getSpecular());
//                finalColor.add(reflectedContribution);
//            }
//        }
//
//        // 6. Plafonnement final des couleurs (très important !)
//        finalColor.clamp();
//
//        return finalColor;
//    }

// ... (conservez la méthode isOccluded et clamp01 en bas de la classe)
    /*// Compute color at the intersection according to scene lighting
    public org.example.Color computeColor(org.example.Scene scene, double[] eye) {
        org.example.AbstractVec3 V = new org.example.AbstractVec3();
        double[] N = normal;
        double[] viewDir = V.normalization(V.subtraction(eye, point));

        double[] baseArr = shape.getDiffuse().getColor();
        double[] specArr = shape.getSpecular().getColor();
        double shininess = shape.getShininess();

        double[] ambArr = scene.getAmbient().getColor();
        double r = clamp01(ambArr[0] * baseArr[0]);
        double g = clamp01(ambArr[1] * baseArr[1]);
        double b = clamp01(ambArr[2] * baseArr[2]);

        for (org.example.raytracer.AbstractLight light : scene.getLights()) {
            double[] L;
            double[] lightColor = light.getColor().getColor();
            double maxDist = Double.POSITIVE_INFINITY;
            if (light instanceof org.example.raytracer.DirectionalLight dl) {
                double[] dirL = dl.getDirection().getVector();
                L = V.normalization(dirL);
            } else if (light instanceof org.example.raytracer.PointLight pl) {
                double[] lp = pl.getPosition().getPoint();
                double[] toLight = V.subtraction(lp, point);
                maxDist = V.length(toLight);
                L = V.normalization(toLight);
            } else {
                continue;
            }

            // Shadow ray: offset point slightly along normal to avoid self-intersection
            double[] shadowOrigin = V.addition(point, V.multiplicationByScalar(1e-4, N));
            org.example.raytracer.Ray shadowRay = new org.example.raytracer.Ray(shadowOrigin, L);
            if (isOccluded(scene, shadowRay, maxDist)) {
                continue; // in shadow for this light
            }

            double ndotl = Math.max(0.0, V.scalarProduct(N, L));
            r = clamp01(r + baseArr[0] * lightColor[0] * ndotl);
            g = clamp01(g + baseArr[1] * lightColor[1] * ndotl);
            b = clamp01(b + baseArr[2] * lightColor[2] * ndotl);

            // Specular (Phong)
            if (shininess > 0) {
                double[] minusL = V.multiplicationByScalar(-1.0, L);
                double[] R = reflect(minusL, N, V);
                double rv = Math.max(0.0, V.scalarProduct(R, viewDir));
                double spec = Math.pow(rv, shininess);
                r = clamp01(r + specArr[0] * lightColor[0] * spec);
                g = clamp01(g + specArr[1] * lightColor[1] * spec);
                b = clamp01(b + specArr[2] * lightColor[2] * spec);
            }
        }

        return new org.example.Color(r, g, b);
    }*/

    private boolean isOccluded(org.example.Scene scene, org.example.raytracer.Ray ray, double maxDist) {
        org.example.AbstractVec3 V = new org.example.AbstractVec3();
        for (Shape s : scene.getShapes()) {
            Intersection h = s.intersect(ray);
            if (h != null) {
                if (Double.isInfinite(maxDist) || h.t < maxDist - 1e-4) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Trouve l'intersection la plus proche le long d'un rayon donné.
     * @param scene La scène
     * @param ray Le rayon secondaire (réfléchi)
     * @return L'intersection la plus proche (peut être null)
     */
    private org.example.raytracer.Intersection findClosestIntersection(org.example.Scene scene, org.example.raytracer.Ray ray) {
        double closestT = Double.POSITIVE_INFINITY;
        Intersection best = null;
        for (org.example.geometry.Shape s : scene.getShapes()) {
            Intersection h = s.intersect(ray);
            // Utiliser 1e-4 pour ignorer l'intersection avec l'objet lui-même (très important !)
            if (h != null && h.getT() > 1e-4 && h.getT() < closestT) {
                closestT = h.getT();
                best = h;
            }
        }
        return best;
    }
    /*private double[] reflect(double[] I, double[] N, org.example.AbstractVec3 V) {
        double dot = V.scalarProduct(I, N);
        double[] twoN = V.multiplicationByScalar(2.0 * dot, N);
        return V.subtraction(I, twoN);
    }*/ //(Elle est maintenant dans AbstractVec3.java).

    private double clamp01(double v) {
        if (v < 0) return 0;
        if (v > 1) return 1;
        return v;
    }
}
