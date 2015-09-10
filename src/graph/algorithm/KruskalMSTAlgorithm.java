package graph.algorithm;

import graph.core.AbstractGraphAlgorithm;
import graph.core.Edge;
import graph.core.Graph;
import graph.core.GraphAlgorithm;
import graph.core.Vertex;
import graph.gui.GraphOverlay;
import graph.util.Heap;
import graph.util.LinkedList;
import graph.util.PriorityQueue;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class KruskalMSTAlgorithm<V,E> extends AbstractGraphAlgorithm<V, E> {
	private class KruskalOverlay implements GraphOverlay {
		Map<Integer, Color> colorMap = new HashMap<Integer, Color>();
		
		{
			colorMap.put(GraphAlgorithm.UNEXPLORED, Color.BLACK);
			colorMap.put(GraphAlgorithm.DISCOVERY, Color.RED);
			colorMap.put(GraphAlgorithm.VISITED, Color.RED);
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
	private Map<Vertex<V>, LinkedList<Vertex<V>>> cloudLabels;
	
	public KruskalMSTAlgorithm() {
		super();
		vertexLabels = new HashMap<Vertex<V>, Integer>();
		edgeLabels = new HashMap<Edge<E>, Integer>();
		cloudLabels = new HashMap<Vertex<V>, LinkedList<Vertex<V>>>();
	}
	
	public KruskalMSTAlgorithm(Graph<V,E> graph) {
		this();
		G = graph;
	}
	
	public void setGraph(Graph<V,E> graph) {
		G = graph;
	}
	
	public void search(Map<String, Vertex<V>> parameters) {
		LinkedList<Edge<E>> T = new LinkedList<Edge<E>>();
		for (Vertex<V> v: G.vertices()){
			LinkedList<Vertex<V>> list = new LinkedList<Vertex<V>>();
			list.insertLast(v);
			cloudLabels.put(v, list);
		}
		PriorityQueue<E, Edge<E>> q = new Heap<E, Edge<E>>();
		for (Edge<E> edge: G.edges()){
			q.insert(edge.element(),edge);// mlog(m)
		}		
		while ( T.size() < G.vertices().size() - 1){//n
			Edge<E> edge = q.remove();// log(m)
			Vertex<V>[] endpoints = G.endVertices(edge);
			if ( !cloudLabels.get(endpoints[0]).equals(cloudLabels.get(endpoints[1]))){				
				T.insertLast(edge);
				LinkedList<Vertex<V>> list = cloudLabels.get(endpoints[0]);
				for (Vertex<V> v: cloudLabels.get(endpoints[1])){//n
					list.insertLast(v);
				}
				for (Vertex<V> v: cloudLabels.get(endpoints[1])){//n
					cloudLabels.put(v, list);
				}				
			}		
		}
		for(Edge<E> edge: G.edges()){
			edgeLabels.put(edge, UNEXPLORED);
		}
		for(Edge<E> edge: T){
			edgeLabels.put(edge, DISCOVERY);
		}
		for(Vertex<V> vertex: G.vertices()){
			vertexLabels.put(vertex, VISITED);
		}
	}
	
/*	private boolean equals( LinkedList<Vertex<V>> l1, LinkedList<Vertex<V>> l2){
		if (l1.size() != l2.size())
			return false;
		else{
			for (Vertex<V> u: l1){
				boolean hasIt = false;
				for (Vertex<V> v: l2){
					if (u.equals(v)){
						System.out.println("OK");
						hasIt = true;
					}
				}
				
				if (!hasIt){
//					System.out.println("False: Return");
					return false;
				}
//				System.out.println("True: Processing...");
			}
		}
		return true;
	}
*/	
	@Override
	public GraphOverlay getOverlay() {
		return new KruskalOverlay();
	}
}
