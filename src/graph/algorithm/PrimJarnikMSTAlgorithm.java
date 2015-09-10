package graph.algorithm;

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

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class PrimJarnikMSTAlgorithm<V,E> extends AbstractGraphAlgorithm<V, E> {
	private class PrimOverlay implements GraphOverlay {
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
	private Map<Vertex<V>, Edge<E>> parentLabels;
	private List<Vertex<V>> cloud;
	
	public PrimJarnikMSTAlgorithm() {
		super();		
		vertexLabels = new HashMap<Vertex<V>, Integer>();
		edgeLabels = new HashMap<Edge<E>, Integer>();
		distanceLabels = new HashMap<Vertex<V>, Integer>();
		positionLabels = new HashMap<Vertex<V>, Position<Vertex<V>>>();
		parentLabels = new HashMap<Vertex<V>, Edge<E>>();
		cloud = new LinkedList<Vertex<V>>();
		addParameter(new Parameter("s", "Give the start parameter for the algorithm"));
	}
	
	public PrimJarnikMSTAlgorithm(Graph<V,E> graph) {
		this();
		G = graph;
	}
	
	public void setGraph(Graph<V,E> graph) {
		G = graph;
	}
	
	public void search(Map<String, Vertex<V>> parameters) {
		Vertex<V> s = parameters.get("s");
		PriorityQueue<Integer, Vertex<V>> q = new Heap<Integer, Vertex<V>>();
		for (Vertex<V> vertex: G.vertices()){
			if ( vertex.equals(s) )
				distanceLabels.put(vertex, 0);
			else
				distanceLabels.put(vertex, INFINITE);
			parentLabels.put(vertex, null);
			Position<Vertex<V>> pos = q.insert(distanceLabels.get(vertex), vertex);
			positionLabels.put(vertex, pos);
		}
		
		while (!q.isEmpty()){
			Vertex<V> u = q.remove();
			cloud.insertLast(u);
			for (Edge<E> edge: G.incidentEdges(u)){
				Vertex<V> z = G.opposite(u, edge);
				int r = Integer.parseInt((String)edge.element());
				if ( r < distanceLabels.get(z) && !inCloud(z)){
		//			System.out.println("r:" + r +" distance:" + distanceLabels.get(z));
					distanceLabels.put(z, r);
					parentLabels.put(z, edge);
					q.replaceKey(positionLabels.get(z), r);
				}			
			}
		}
		for (Edge<E> e: G.edges()){
			edgeLabels.put(e, BACK);
		}
		for (Vertex<V> v: G.vertices()){
			vertexLabels.put(v, VISITED);
			if ( parentLabels.get(v) != null)
				edgeLabels.put(parentLabels.get(v), DISCOVERY);
		}
	}
	
	private boolean inCloud(Vertex<V> v){
		for (Vertex<V> u: cloud){
			if (u.equals(v))
				return true;
		}
		return false;
	}

	@Override
	public GraphOverlay getOverlay() {
		return new PrimOverlay();
	}
}