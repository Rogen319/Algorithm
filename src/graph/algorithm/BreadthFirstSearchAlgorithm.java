package graph.algorithm;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import graph.util.LinkedList;
import graph.util.List;
import graph.util.Position;
import graph.core.AbstractGraphAlgorithm;
import graph.core.Edge;
import graph.core.Graph;
import graph.core.GraphAlgorithm;
import graph.core.Vertex;
import graph.gui.GraphOverlay;

public class BreadthFirstSearchAlgorithm<V,E> extends AbstractGraphAlgorithm<V, E> {
	private class BreadthFirstOverlay implements GraphOverlay {
		Map<Integer, Color> colorMap = new HashMap<Integer, Color>();
		
		{
			colorMap.put(GraphAlgorithm.UNEXPLORED, Color.BLACK);
			colorMap.put(GraphAlgorithm.DISCOVERY, Color.RED);
			colorMap.put(GraphAlgorithm.VISITED, Color.RED);
			colorMap.put(GraphAlgorithm.CROSS, Color.GREEN);
		}
		@Override		
		@SuppressWarnings("rawtypes")
		public Color edgeColor(Edge edge) {
			return colorMap.get(edgeLabels.get(edge));
		}

		@SuppressWarnings("rawtypes")
		@Override
		public Color vertexColor(Vertex vertex) {
			return colorMap.get(vertexLabels.get(vertex));
		}
	}

	private Graph<V,E> G;
	private Map<Vertex<V>, Integer> vertexLabels;
	private Map<Edge<E>, Integer> edgeLabels;
	
	public BreadthFirstSearchAlgorithm() {
		super();
		vertexLabels = new HashMap<Vertex<V>, Integer>();
		edgeLabels = new HashMap<Edge<E>, Integer>();
	}
	
	public BreadthFirstSearchAlgorithm(Graph<V,E> graph) {
		this();
		G = graph;
	}
	
	public void setGraph(Graph<V,E> graph) {
		G = graph;
	}

	@Override
	public void search(Map<String, Vertex<V>> parameters) {
		for(Vertex<V> vertex: G.vertices())
			vertexLabels.put(vertex, UNEXPLORED);
		for(Edge<E> edge: G.edges())
			edgeLabels.put(edge, UNEXPLORED);
		for(Vertex<V> vertex: G.vertices())
			if (vertexLabels.get(vertex) == UNEXPLORED)
				search(vertex);		
	}
	
	public void search(Vertex<V> s){
		List<Vertex<V>> q = new LinkedList<Vertex<V>>();
		q.insertLast(s);
		vertexLabels.put(s, VISITED);
		while (!q.isEmpty()){
			int size = q.size();
			for (int i = 0; i < size; i++){
				Position<Vertex<V>> p = q.first();
				q.remove(p);
				Vertex<V> v = p.element();
				for(Edge<E> edge: G.incidentEdges(v)){
					if (edgeLabels.get(edge) == UNEXPLORED){
						Vertex<V> w = G.opposite(v, edge);
						if (vertexLabels.get(w) == UNEXPLORED){
							q.insertLast(w);
							vertexLabels.put(w, VISITED);
							edgeLabels.put(edge, DISCOVERY);
						}else{
							edgeLabels.put(edge, CROSS);
						}							
					}
				}
			}
		}
	}
	
	@Override
	public GraphOverlay getOverlay() {
		return new BreadthFirstOverlay();
	}
}
