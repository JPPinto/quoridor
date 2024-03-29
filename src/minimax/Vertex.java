package minimax;

import java.util.ArrayList;

/**
 * Created by Jo�o on 29/05/2015.
 */
public class Vertex<T> implements Comparable<Vertex> {
    public T name;
    public ArrayList<Edge> adjacencies=new ArrayList<Edge>();
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
