import java.util.Comparator;

class NameComparator implements Comparator<Graph.Node> {
    @Override
    public int compare(Graph.Node a, Graph.Node b) {
        return a.name.compareToIgnoreCase(b.name);
    }
}