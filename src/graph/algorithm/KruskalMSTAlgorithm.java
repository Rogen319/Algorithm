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
import graph.util.LinkedList;
import graph.util.Position;

public class KruskalMSTAlgorithm<V,E> extends AbstractGraphAlgorithm<V,E> {
	private class KruskalOverlay implements GraphOverlay {
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
	private Map<Edge<E>, Integer> edgeWeight;
	private Map<Vertex<V>, LinkedList<Vertex<V>>> vertexParent;
	private Map<Edge<E>, Integer> edgeLabels;
	
	public KruskalMSTAlgorithm() {
		super();
		vertexLabels = new HashMap<Vertex<V>, Integer>();
		edgeLabels = new HashMap<Edge<E>, Integer>();
		edgeWeight = new  HashMap<Edge<E>, Integer>();
		vertexParent = new HashMap<Vertex<V>, LinkedList<Vertex<V>>>();
	}
	
	public KruskalMSTAlgorithm(Graph<V,E> graph) {
		this();
		G = graph;
	}
	
	public void setGraph(Graph<V,E> graph) {
		G = graph;
	}
	
	public void search(Map<String, Vertex<V>> parameters) {
		Heap<Integer, Edge<E>> heap = new Heap<Integer, Edge<E>>();
		LinkedList<Edge<E>> edges = new LinkedList<Edge<E>>();
		for (Vertex<V> vertex: G.vertices()) {
			vertexLabels.put(vertex, VISITED);
			LinkedList<Vertex<V>> list = new LinkedList<Vertex<V>>();
			list.insertLast(vertex);
			vertexParent.put(vertex, list);
		}
		
		for (Edge<E> edge: G.edges()) {
			edgeLabels.put(edge, UNEXPLORED);
			edgeWeight.put(edge, Integer.parseInt((String)edge.element()));
			heap.insert(edgeWeight.get(edge), edge);
		}
		
		while (edges.size() < G.vertices().size() - 1) {
			Edge<E> edge = heap.remove();
			Vertex<V>[] vertexs = G.endVertices(edge);
			if (vertexParent.get(vertexs[0]) != vertexParent.get(vertexs[1])) {
				edges.insertLast(edge);
				edgeLabels.put(edge, DISCOVERY);
				LinkedList<Vertex<V>> list1 = vertexParent.get(vertexs[0]);
				LinkedList<Vertex<V>> list2 = vertexParent.get(vertexs[1]);
				for (Vertex<V> vertex : list2) {
					list1.insertLast(vertex);
					vertexParent.put(vertex, list1);
				}
			}
		}
	}

	@Override
	public GraphOverlay getOverlay() {
		return new KruskalOverlay();
	}
}
