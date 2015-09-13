package graph.algorithm;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import graph.core.AbstractGraphAlgorithm;
import graph.core.Edge;
import graph.core.Graph;
import graph.core.GraphAlgorithm;
import graph.core.Parameter;
import graph.core.Vertex;
import graph.gui.GraphOverlay;
import graph.util.Heap;
import graph.util.Position;

public class DijkstraShortestPathTreeAlgorithm<V,E> extends AbstractGraphAlgorithm<V,E>{
	private class DijkstraOverlay implements GraphOverlay {
		Map<Integer, Color> colorMap = new HashMap<Integer, Color>();
		
		{
			colorMap.put(GraphAlgorithm.UNEXPLORED, Color.BLACK);
			colorMap.put(GraphAlgorithm.DISCOVERY, Color.RED);
			colorMap.put(GraphAlgorithm.VISITED, Color.RED);
			colorMap.put(GraphAlgorithm.BACK, Color.GREEN);
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
	private Map<Vertex<V>, Integer> vertexWeight;
	private Map<Vertex<V>, Position<Vertex<V>>> vertexPosition;
	private Map<Edge<E>, Integer> edgeLabels;
	
	public DijkstraShortestPathTreeAlgorithm() {
		super();
		vertexLabels = new HashMap<Vertex<V>, Integer>();
		edgeLabels = new HashMap<Edge<E>, Integer>();
		vertexWeight = new  HashMap<Vertex<V>, Integer>();
		vertexPosition = new HashMap<Vertex<V>, Position<Vertex<V>>>();
		addParameter(new Parameter("s","Give the start parameter for the algorithm"));
	}
	
	public DijkstraShortestPathTreeAlgorithm(Graph<V,E> graph) {
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
		
		Vertex<V> vertex = parameters.get("s");
		search(vertex);
	}
	
	public void search(Vertex<V> s) {
		Heap<Integer, Vertex<V>> heap = new Heap<Integer, Vertex<V>>();
		
		for (Vertex<V> vertex : G.vertices()) {
			if (vertex.equals(s))
				vertexWeight.put(vertex, 0);
			else 
				vertexWeight.put(vertex, Integer.MAX_VALUE);
			Position<Vertex<V>> position = heap.insert(vertexWeight.get(vertex), vertex);
			vertexPosition.put(vertex, position);
		}
		
		while (!heap.isEmpty()) {
			Vertex<V> uVertex = heap.remove();
			vertexLabels.put(uVertex, VISITED);
			for (Edge<E> edge : G.incidentEdges(uVertex)) {
				if (edgeLabels.get(edge) == UNEXPLORED) {
					Vertex<V> vVertex = G.opposite(uVertex, edge);
					int newWeight = vertexWeight.get(uVertex) + Integer.parseInt((String)edge.element());
					if (newWeight < vertexWeight.get(vVertex)) {
						vertexWeight.put(vVertex, newWeight);
						heap.replaceKey(vertexPosition.get(vVertex), newWeight);
						for (Edge<E> edge2 : G.incidentEdges(vVertex)) {
							if (edgeLabels.get(edge2) == DISCOVERY)
								edgeLabels.put(edge2, BACK);
						}
						edgeLabels.put(edge, DISCOVERY);
					}
					else {
						edgeLabels.put(edge, BACK);
					}
				}
			}
		}
	}

	@Override
	public GraphOverlay getOverlay() {
		return new DijkstraOverlay();
	}
}
