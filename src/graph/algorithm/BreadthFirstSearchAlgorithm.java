package graph.algorithm;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import graph.core.AbstractGraphAlgorithm;
import graph.core.Edge;
import graph.core.Graph;
import graph.core.GraphAlgorithm;
import graph.core.Vertex;
import graph.gui.GraphOverlay;
import graph.util.LinkedList;

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
		public Color edgeColor(Edge edge) {
			return colorMap.get(edgeLabels.get(edge));
		}

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
	
	public void search(Map<String, Vertex<V>> parameters) {
		for (Vertex<V> vertex: G.vertices()) {
			vertexLabels.put(vertex, UNEXPLORED);
		}
		for (Edge<E> edge: G.edges()) {
			edgeLabels.put(edge, UNEXPLORED);
		}
		for (Vertex<V> vertex: G.vertices()) {
			if (vertexLabels.get(vertex) == UNEXPLORED) {
				search(vertex);
			}
		}
	}
	
	public void search(Vertex<V> s) {
		LinkedList<Vertex<V>> linkedList = new LinkedList<Vertex<V>>();
		linkedList.insertLast(s);
		vertexLabels.put(s, VISITED);
		
		while (!linkedList.isEmpty()){
			Vertex<V> tempVertex = linkedList.remove(linkedList.first());
			for (Edge<E> edge: G.incidentEdges(tempVertex)) {
				if (edgeLabels.get(edge) == UNEXPLORED){
					if (vertexLabels.get(G.opposite(tempVertex, edge)) == UNEXPLORED) {
						edgeLabels.put(edge, DISCOVERY);
						linkedList.insertLast(G.opposite(tempVertex, edge));
						vertexLabels.put(G.opposite(tempVertex, edge),VISITED);
					}
					else {
						edgeLabels.put(edge, CROSS);
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
