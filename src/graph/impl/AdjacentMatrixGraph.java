package graph.impl;

import graph.core.Edge;
import graph.core.Graph;
import graph.core.InvalidVertexException;
import graph.core.Vertex;
import graph.util.LinkedList;
import graph.util.List;
import graph.util.Position;

import java.util.Iterator;



public class AdjacentMatrixGraph<V,E> implements Graph<V,E> {
	private class AdjacentMatrixVertex implements Vertex<V> {
		Position<AdjacentMatrixVertex> position;
		V element;
		int index;
		
		public AdjacentMatrixVertex(V element) {
			this.element = element;
		}
		
		@Override
		public V element() {
			return element;
		}
		
		public String toString() {
			return element.toString();
		}
	}
	
	private class AdjacentMatrixEdge implements Edge<E> {
		Position<AdjacentMatrixEdge> position;
		E element;
		AdjacentMatrixVertex start, end;
		
		public AdjacentMatrixEdge(AdjacentMatrixVertex start, AdjacentMatrixVertex end, E element) {
			this.start = start;
			this.end = end;
			this.element = element;
		}
		
		@Override
		public E element() {
			return element;
		}
		
		public String toString() {
			return element.toString();
		}
	}
	private static final int MAXLINE = 1024;
	private List<AdjacentMatrixVertex> vertices;
	private List<AdjacentMatrixEdge> edges;
	private Object[][] matrix;
	private int idx = 0;
	
	public AdjacentMatrixGraph() {
		vertices = new LinkedList<AdjacentMatrixVertex>();
		edges = new LinkedList<AdjacentMatrixEdge>();
		matrix = new Object[MAXLINE][MAXLINE];
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Vertex<V>[] endVertices(Edge<E> e) {
		AdjacentMatrixEdge edge = (AdjacentMatrixEdge) e;
		Vertex<V>[] endpoints = (Vertex<V>[]) new Vertex[2];
		endpoints[0] = edge.start;
		endpoints[1] = edge.end;
		return endpoints;
	}

	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) {
		Vertex<V>[] endpoints = endVertices(e);
		if (endpoints[0].equals(v)) {
			return endpoints[1];
		} else if (endpoints[1].equals(v)) {
			return endpoints[0];
		}
		throw new InvalidVertexException();
	}

	@Override
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) {		
		return matrix[((AdjacentMatrixVertex)v).index][((AdjacentMatrixVertex)w).index] != null;
	}

	@Override
	public V replace(Vertex<V> v, V x) {
		AdjacentMatrixVertex vertex = (AdjacentMatrixVertex) v;
		V temp = vertex.element;
		vertex.element = x;
		return temp;
	}

	@Override
	public E replace(Edge<E> e, E x) {
		AdjacentMatrixEdge edge = (AdjacentMatrixEdge) e;
		E temp = edge.element;
		edge.element = x;
		return temp;
	}

	@Override
	public Vertex<V> insertVertex(V v) {
		AdjacentMatrixVertex vertex = new AdjacentMatrixVertex(v);
		vertex.index = idx;
		idx++;
		Position<AdjacentMatrixVertex> position = vertices.insertLast(vertex);
		vertex.position = position;
		return vertex;
	}

	@Override
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E o) {
		AdjacentMatrixEdge edge = new AdjacentMatrixEdge((AdjacentMatrixVertex) v, (AdjacentMatrixVertex) w, o);
		matrix[((AdjacentMatrixVertex) v).index][((AdjacentMatrixVertex) w).index] = edge;
		matrix[((AdjacentMatrixVertex) w).index][((AdjacentMatrixVertex) v).index] = edge;
		Position<AdjacentMatrixEdge> position = edges.insertLast(edge);
		edge.position = position;
		return edge;
	}

	@Override
	public V removeVertex(Vertex<V> v) {
		Iterator<Edge<E>> it = incidentEdges(v).iterator();		
		while (it.hasNext()) it.remove();
		int idx_V = ((AdjacentMatrixVertex)v).index;
		for (int i = 0; i < idx; i++){
			matrix[idx_V][i] = null;
			matrix[i][idx_V] = null;
		}			
		AdjacentMatrixVertex vertex = (AdjacentMatrixVertex) v;
		vertices.remove(vertex.position);
		return vertex.element;
	}

	@Override
	public E removeEdge(Edge<E> e) {
		AdjacentMatrixEdge edge = (AdjacentMatrixEdge) e;
		matrix[edge.start.index][edge.end.index] = null;
		matrix[edge.end.index][edge.start.index] = null;
		edges.remove(edge.position);
		return edge.element;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Edge<E>> incidentEdges(Vertex<V> v) {
		LinkedList<Edge<E>> list = new LinkedList<Edge<E>>();		
		for (int i = 0; i < idx; i++) {
			AdjacentMatrixEdge edge = (AdjacentMatrixEdge)matrix[((AdjacentMatrixVertex)v).index][i];
			if (edge != null){
				list.insertLast(edge);
			}
		}
		
		return list;
	}

	@Override
	public List<Vertex<V>> vertices() {
		LinkedList<Vertex<V>> list = new LinkedList<Vertex<V>>();
		for (AdjacentMatrixVertex vertex : vertices) {
			list.insertLast(vertex);
		}
		return list;
	}

	@Override
	public List<Edge<E>> edges() {
		LinkedList<Edge<E>> list = new LinkedList<Edge<E>>();
		for (AdjacentMatrixEdge edge : edges) {
			list.insertLast(edge);
		}
		return list;
	}

}
