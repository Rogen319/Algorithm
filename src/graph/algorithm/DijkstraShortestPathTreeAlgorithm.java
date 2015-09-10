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
import graph.util.List;
import graph.util.Position;
import graph.util.PriorityQueue;

public class DijkstraShortestPathTreeAlgorithm<V,E> extends AbstractGraphAlgorithm<V, E> {
	private class DijkstraOverlay implements GraphOverlay {
		Map<Integer, Color> colorMap = new HashMap<Integer, Color>();
		
		{
			colorMap.put(GraphAlgorithm.UNEXPLORED, Color.BLACK);
			colorMap.put(GraphAlgorithm.DISCOVERY, Color.RED);
			colorMap.put(GraphAlgorithm.VISITED, Color.RED);
			colorMap.put(GraphAlgorithm.BACK, Color.GREEN);
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
	
	private final int INFINITE = 1048576;
	private Graph<V,E> G;
	private Map<Vertex<V>, Integer> vertexLabels;
	private Map<Edge<E>, Integer> edgeLabels;
	private Map<Vertex<V>, Integer> distanceLabels;
	private Map<Vertex<V>, Position<Vertex<V>>> positionLabels;
	private Map<Vertex<V>, List<Edge<E>>> pathLabels;
	
	public DijkstraShortestPathTreeAlgorithm() {
		super();		
		vertexLabels = new HashMap<Vertex<V>, Integer>();
		edgeLabels = new HashMap<Edge<E>, Integer>();
		distanceLabels = new HashMap<Vertex<V>, Integer>();
		positionLabels = new HashMap<Vertex<V>, Position<Vertex<V>>>();
		pathLabels = new HashMap<Vertex<V>, List<Edge<E>>>();
		addParameter(new Parameter("s", "Give the start parameter for the algorithm"));
	}
	
	public DijkstraShortestPathTreeAlgorithm(Graph<V,E> graph) {
		this();
		G = graph;
	}
	
	public void setGraph(Graph<V,E> graph) {
		G = graph;
	}
	
	@SuppressWarnings("unchecked")
	public void search(Map<String, Vertex<V>> parameters) {
		Vertex<V> s = parameters.get("s");
		PriorityQueue<Integer, Vertex<V>> q = new Heap<Integer, Vertex<V>>();
		
		for (Vertex<V> vertex: G.vertices()) {
			if (vertex.equals(s)){
				distanceLabels.put(vertex, 0);			
			}
			else{
				distanceLabels.put(vertex, INFINITE);
			}
			pathLabels.put(vertex, new LinkedList<Edge<E>>());
			Position<Vertex<V>> post = q.insert(distanceLabels.get(vertex), vertex);
			positionLabels.put(vertex, post);			
		}
		
		while ( !q.isEmpty() ){
			Vertex<V> u = q.remove();
			for (Edge<E> e: G.incidentEdges(u)){
				Vertex<V> z = G.opposite(u, e);				
				int r = distanceLabels.get(u) + Integer.parseInt(((Edge<String>)e).element());
				if ( r < distanceLabels.get(z)){					
					distanceLabels.put(z, r);
					q.replaceKey(positionLabels.get(z), r);
					LinkedList<Edge<E>> list = new LinkedList<Edge<E>>();
					for (Edge<E> edge: pathLabels.get(u)){
						list.insertLast(edge);
					}
					list.insertLast(e);					
					pathLabels.put(z, list);
				}
			}
		}
		setColor();
	}
	
	private void setColor(){

		for (Edge<E> edge: G.edges()){
			edgeLabels.put(edge, BACK);
		}
		for (Vertex<V> vertex: G.vertices()){
			vertexLabels.put(vertex, VISITED);
			for (Edge<E> edge: pathLabels.get(vertex)){
				edgeLabels.put(edge, DISCOVERY);
			}
		}
	}
	

	@Override
	public GraphOverlay getOverlay() {
		return new DijkstraOverlay();
	}
}
