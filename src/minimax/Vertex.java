package minimax;

/**
 * Created by João on 29/05/2015.
 */
public class Vertex<T> implements Comparable<Vertex> {
    public T name;
    public Edge[] adjacencies;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;

    public Vertex(T name) {
        this.name = name;
    }

    public T getName() {
        return name;
    }

    public int compareTo(Vertex other) {
        return Double.compare(minDistance, other.minDistance);
    }

}
