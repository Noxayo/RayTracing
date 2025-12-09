package org.example.raytracer;

import org.example.math.Color;
import org.example.geometry.Shape;

/**
 * Représente une intersection rayon-objet (Shape, t, point, normal) et centralise
 * les utilitaires d'intersection spécifiques à la géométrie (Möller–Trumbore, Sphère/Plan)
 * ainsi que l'algorithme d'éclairage récursif de la scène (Jalon 5 et Jalon 6).
 */
public class Intersection {
    private final Shape shape;
    private final double t;
    private final double[] point;
    private final double[] normal; // normalisée
    // L'AbstractVec3 pour tous les calculs
    private final org.example.math.AbstractVec3 V = new org.example.math.AbstractVec3();

    /**
     * Constructeur d'une intersection.
     * @param shape forme touchée
     * @param t paramètre le long du rayon
     * @param point point d'impact
     * @param normal normale au point d'impact (normalisée)
     */
    public Intersection(Shape shape, double t, double[] point, double[] normal) {
        this.shape = shape;
        this.t = t;
        this.point = point;
        this.normal = normal;
    }

    /** Retourne la forme intersectée. */
    public Shape getShape() { return shape; }
    /** Retourne la distance paramétrique le long du rayon. */
    public double getT() { return t; }
    /** Retourne le point d'impact. */
    public double[] getPoint() { return point; }
    /** Retourne la normale (normalisée). */
    public double[] getNormal() { return normal; }

    // --- Aides d'intersection spécifiques à la géométrie (centralisées) ---
    /**
     * Test d'intersection rayon-triangle (Möller–Trumbore).
     * @param tri triangle
     * @param ray rayon
     * @return intersection ou null
     */
    public static org.example.raytracer.Intersection intersectTriangle(
            org.example.geometry.Triangle tri,
            org.example.raytracer.Ray ray) {
        org.example.math.AbstractVec3 V = new org.example.math.AbstractVec3();
        double[] O = ray.getOrigin();
        double[] D = ray.getDirection();
        double[] A = tri.getV0().getPoint();
        double[] B = tri.getV1().getPoint();
        double[] C = tri.getV2().getPoint();

        double[] e1 = V.subtraction(B, A);
        double[] e2 = V.subtraction(C, A);
        double[] pvec = V.vectorialProduct(D, e2);
        double det = V.scalarProduct(e1, pvec);
        if (Math.abs(det) < 1e-8) return null;
        double invDet = 1.0 / det;

        double[] tvec = V.subtraction(O, A);
        double u = V.scalarProduct(tvec, pvec) * invDet;
        if (u < 0 || u > 1) return null;

        double[] qvec = V.vectorialProduct(tvec, e1);
        double v = V.scalarProduct(D, qvec) * invDet;
        if (v < 0 || u + v > 1) return null;

        double t = V.scalarProduct(e2, qvec) * invDet;
        if (t <= 1e-6) return null;

        double[] P = V.addition(O, V.multiplicationByScalar(t, D));
        double[] N = V.normalization(V.vectorialProduct(e1, e2));
        return new org.example.raytracer.Intersection(tri, t, P, N);
    }

    /**
     * Teste l'intersection d'un rayon avec une sphère.
     * * L'algorithme résout l'équation quadratique du second degré en t : a*t^2 + b*t + c = 0
     * où a = D⋅D, b = 2 * (O-C)⋅D, et c = (O-C)⋅(O-C) - r^2
     * * 1. Calcul du discriminant (Δ = b^2 - 4ac) pour déterminer le nombre de solutions
     * 2. Si Δ > 0, deux intersections existent (t1 et t2)
     * 3. La méthode sélectionne le t positif le plus petit (> 1e-6)
     * 4. La normale (N) est calculée comme la direction (p - Centre) normalisée.
     *
     * @param sph La sphère à tester.
     * @param ray Le rayon émis (origine O et direction D).
     * @return L'objet Intersection pour l'impact valide le plus proche, ou null si aucune intersection n'est trouvée devant le rayon.
     */
    public static org.example.raytracer.Intersection intersectSphere(
            org.example.geometry.Sphere sph,
            org.example.raytracer.Ray ray) {
        org.example.math.AbstractVec3 V = new org.example.math.AbstractVec3();
        double[] origin = ray.getOrigin();
        double[] dir = ray.getDirection();
        double[] C = sph.getCenter().getPoint();
        double radius = sph.getRadius();
        double[] oc = V.subtraction(origin, C);
        double a = V.scalarProduct(dir, dir);
        double b = 2.0 * V.scalarProduct(oc, dir);
        double c = V.scalarProduct(oc, oc) - radius * radius;
        double disc = b * b - 4 * a * c;
        if (disc < 0) return null;
        double sqrtD = Math.sqrt(disc);
        double t1 = (-b - sqrtD) / (2 * a);
        double t2 = (-b + sqrtD) / (2 * a);
        double t = Double.POSITIVE_INFINITY;
        if (t1 > 1e-6) t = Math.min(t, t1);
        if (t2 > 1e-6) t = Math.min(t, t2);
        if (t == Double.POSITIVE_INFINITY) return null;
        double[] point = V.addition(origin, V.multiplicationByScalar(t, dir));
        double[] normal = V.normalization(V.subtraction(point, C));
        return new org.example.raytracer.Intersection(sph, t, point, normal);
    }

    /**
     * Test d'intersection rayon-plan.
     * @param pl plan
     * @param ray rayon
     * @return intersection ou null
     */
    public static org.example.raytracer.Intersection intersectPlane(
            org.example.geometry.Plane pl,
            org.example.raytracer.Ray ray) {
        org.example.math.AbstractVec3 V = new org.example.math.AbstractVec3();
        double[] origin = ray.getOrigin();
        double[] dir = ray.getDirection();
        double[] P0 = pl.getPoint().getPoint();
        double[] N = pl.getNormal().getVector();

        double denom = V.scalarProduct(N, dir);
        if (Math.abs(denom) < 1e-8) return null;
        double t = V.scalarProduct(V.subtraction(P0, origin), N) / denom;
        if (t <= 1e-6) return null;
        double[] hitPoint = V.addition(origin, V.multiplicationByScalar(t, dir));
        double[] n = V.normalization(N);
        return new org.example.raytracer.Intersection(pl, t, hitPoint, n);
    }

    /**
     * Calcule la couleur finale au point d'intersection.
     * Implémente la lumière directe (Lambert/Phong/Ombres) et la contribution de la réflexion récursive.
     * * @param scene La scène (pour accès aux lumières et maxdepth).
     * @param origin L'origine du rayon (œil ou intersection précédente).
     * @param depth La profondeur de récursion actuelle (commence à 1 pour le rayon primaire).
     * @return La couleur finale, plafonnée à [1, 1, 1].
     */
    public org.example.math.Color computeColor(org.example.math.Scene scene, double[] origin, int depth) {

        double[] N = normal;

        // 1. Calcul des vecteurs nécessaires
        // D : Direction du rayon INCIDENT (utilisée pour la réflexion)
        double[] D = V.normalization(V.subtraction(point, origin));

        // 2. Calcul de viewDir (Direction de VUE pour Phong: point -> origin)
        // L'ancien 'eye' est maintenant 'origin'.
        double[] viewDir = V.normalization(V.subtraction(origin, point));

        double[] baseArr = shape.getDiffuse().getColor();
        double[] specArr = shape.getSpecular().getColor();
        double shininess = shape.getShininess();
        // Initialisation de r, g, b avec la lumière ambiante
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

            // Rayon d'ombre : décaler légèrement le point le long de la normale pour éviter l'auto-intersection
            double[] shadowOrigin = V.addition(point, V.multiplicationByScalar(1e-4, N));
            org.example.raytracer.Ray shadowRay = new org.example.raytracer.Ray(shadowOrigin, L);
            if (isOccluded(scene, shadowRay, maxDist)) {
                continue; // dans l'ombre pour cette lumière
            }

            // Diffus (Lambert)
            double ndotl = Math.max(0.0, V.scalarProduct(N, L));
            r = clamp01(r + baseArr[0] * lightColor[0] * ndotl);
            g = clamp01(g + baseArr[1] * lightColor[1] * ndotl);
            b = clamp01(b + baseArr[2] * lightColor[2] * ndotl);

            // Spéculaire (Phong) : Brillance, dépend de l'angle de vue
            if (shininess > 0) {
                double[] minusL = V.multiplicationByScalar(-1.0, L);
                //  CORRECTION JALON 6: reflect est maintenant dans V (AbstractVec3) (On a essayé de bien affecter les fontions au bons endroits)
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
        org.example.math.Color finalColor = new org.example.math.Color(r, g, b);

        // --- LOGIQUE DE RÉFLEXION (Jalon 6) ---
        int maxDepth = scene.getMaxdepth();
        org.example.math.Color specular = shape.getSpecular();

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
                org.example.math.Color reflectedColor = hitPrime.computeColor(scene, point, depth + 1);

                // 5. Ajouter la contribution: c = c + specular * c'
                Color reflectedContribution = reflectedColor.multiply(specular);
                finalColor.add(reflectedContribution);
            }
        }

        // 6. Plafonnement final des couleurs (très important !)
        finalColor.clamp();

        return finalColor;
    }

    /**
     * Teste l'occlusion d'un point par un objet entre le point et la lumière.
     * @param scene scène
     * @param ray rayon d'ombre
     * @param maxDist distance maximale à considérer (point lights)
     * @return true si occlus
     */
    private boolean isOccluded(org.example.math.Scene scene, org.example.raytracer.Ray ray, double maxDist) {
        org.example.math.AbstractVec3 V = new org.example.math.AbstractVec3();
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
    /**
     * Trouve l'intersection valide la plus proche pour un rayon donné.
     */
    private org.example.raytracer.Intersection findClosestIntersection(org.example.math.Scene scene, org.example.raytracer.Ray ray) {
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

    private double clamp01(double v) {
        if (v < 0) return 0;
        if (v > 1) return 1;
        return v;
    }
}
