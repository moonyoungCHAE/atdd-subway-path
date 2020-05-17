package wooteco.subway.admin.domain.path;

import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import wooteco.subway.admin.domain.LineStation;

import java.util.List;

public class ShortestPath {
    private DijkstraShortestPath<Long, WeightedEdge> path;

    private ShortestPath(DijkstraShortestPath<Long, WeightedEdge> path) {
        this.path = path;
    }

    public static ShortestPath of(List<LineStation> lineStations, PathType pathType) {
        WeightedGraph<Long, WeightedEdge> graph
                = new DirectedWeightedMultigraph<>(WeightedEdge.class);

        for (LineStation station : lineStations) {
            graph.addVertex(station.getStationId());
            if (station.isFirst()) {
                continue;
            }
            WeightedEdge weightedEdge
                    = graph.addEdge(station.getPreStationId(), station.getStationId());
            weightedEdge.setSubWeight(pathType.findSubWeight(station));
            graph.setEdgeWeight(weightedEdge, pathType.findWeight(station));
        }

        DijkstraShortestPath<Long, WeightedEdge> shortestPath = new DijkstraShortestPath<>(graph);
        return new ShortestPath(shortestPath);
    }

    public DijkstraShortestPath<Long, WeightedEdge> getPath() {
        return path;

    }

    public List<Long> getVertexList(Long source, Long target) {
        try {
            return path.getPath(source, target).getVertexList();
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("존재하지 않는 경로입니다.");
        }
    }

    public int getWeight(Long source, Long target) {
        return (int) path.getPath(source, target).getWeight();
    }

    public int getSubWeight(Long source, Long target) {
        return path.getPath(source, target)
                .getEdgeList()
                .stream()
                .mapToInt(WeightedEdge::getSubWeight)
                .sum();
    }
}
