import java.util.Comparator;

public class EdgeComparator implements Comparator<Edge> {

    @Override
    public int compare(Edge edge, Edge edge1) {
        if (edge.cost > edge1.cost)
            return 1;
        else if (edge.cost < edge1.cost)
            return -1;
        return 0;
    }

}

