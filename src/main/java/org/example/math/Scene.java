package org.example.math;

import org.example.geometry.Shape;
import org.example.raytracer.AbstractLight;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente une scène 3D complète
 * Contient tous les objets, lumières et paramètres de rendu
 */
public class Scene {

    // ========== ATTRIBUTS OBLIGATOIRES ==========
    private int width;              // Largeur de l'image
    private int height;             // Hauteur de l'image
    private Camera camera;          // La caméra

    // ========== ATTRIBUTS OPTIONNELS ==========
    private String output = "output.png";           // Nom du fichier de sortie
    private Color ambient = new Color();            // Couleur ambiante (noir par défaut)

    // ========== LISTES ==========
    private List<AbstractLight> lights = new ArrayList<>();  // Liste des lumières
    private List<Shape> shapes = new ArrayList<>();          // Liste des formes

    // ========== ATTRIBUTS TEMPORAIRES POUR LE PARSING ==========
    // Ces couleurs sont utilisées pour les prochains objets créés
    private Color currentDiffuse = new Color(1, 1, 1);    // Couleur diffuse courante (par défaut blanc)
    private Color currentSpecular = new Color();   // Couleur spéculaire courante
    private double currentShininess = 0.0;         // Exposant de brillance (Phong)

    // Pour les triangles
    private List<Point> vertices = new ArrayList<>();  // Liste des sommets (vertex)
    private int maxVerts = 0;                         // Nombre max de vertices attendus

    // ATTRIBUT POUR LE JALON 6
    private int maxdepth = 1; // Profondeur max de récursion (1 par défaut = pas de réflexion)

    // ========== CONSTRUCTEUR ==========
    public Scene() {
        // Constructeur vide, tout est initialisé par défaut
    }


    // ========== GETTERS ==========

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Camera getCamera() {
        return camera;
    }

    public String getOutput() {
        return output;
    }

    public Color getAmbient() {
        return ambient;
    }

    public List<AbstractLight> getLights() {
        return lights;
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public Color getCurrentDiffuse() {
        return currentDiffuse;
    }

    public Color getCurrentSpecular() {
        return currentSpecular;
    }

    public double getCurrentShininess() {
        return currentShininess;
    }

    public List<Point> getVertices() {
        return vertices;
    }

    public int getMaxVerts() {
        return maxVerts;
    }

    // Maxdepth pour la scene du jalon 6
    public int getMaxdepth() {
        return maxdepth;
    }
    // ========== SETTERS ==========

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public void setAmbient(Color ambient) {
        this.ambient = ambient;
    }

    public void setCurrentDiffuse(Color currentDiffuse) {
        this.currentDiffuse = currentDiffuse;
    }

    public void setCurrentSpecular(Color currentSpecular) {
        this.currentSpecular = currentSpecular;
    }

    public void setCurrentShininess(double currentShininess) {
        this.currentShininess = currentShininess;
    }

    public void setMaxVerts(int maxVerts) {
        this.maxVerts = maxVerts;
    }

    public void setMaxdepth(int maxdepth) {
        this.maxdepth = maxdepth;
    }
    // ========== MÉTHODES UTILES ==========

    /**
     * Ajoute une lumière à la scène
     */
    public void addLight(AbstractLight light) {
        this.lights.add(light);
    }

    /**
     * Ajoute une forme à la scène
     */
    public void addShape(Shape shape) {
        this.shapes.add(shape);
    }

    /**
     * Ajoute un vertex à la liste
     */
    public void addVertex(Point vertex) {
        this.vertices.add(vertex);
    }


    @Override
    public String toString() {
        return "Scene{" +
                "width=" + width +
                ", height=" + height +
                ", output='" + output + '\'' +
                ", camera=" + camera +
                ", lights=" + lights.size() +
                ", shapes=" + shapes.size() +
                '}';
    }
    /**
     * Calcule la couleur à l'intersection. Mis à jour pour la récursion.
     * @param intersection L'intersection trouvée.
     * @param origin Le point d'où vient le rayon (œil ou intersection précédente).
     * @param depth La profondeur de récursion actuelle.
     * @return La couleur calculée.
     */
    public Color computeColor(org.example.raytracer.Intersection intersection, double[] origin, int depth) {
        // Délègue le calcul à l'Intersection, en passant la profondeur
        return intersection.computeColor(this, origin, depth);
    }
}
