package graph.impl;

import graph.core.Edge;
import graph.core.Graph;
import graph.core.InvalidVertexException;
import graph.core.Vertex;
import graph.util.LinkedList;
import graph.util.List;
import graph.util.Position;

import java.util.Iterator;



public class AdjacentListGraph<V,E> implements Graph<V,E> {
	private class AdjacentListVertex implements Vertex<V> {
		List<AdjacentListEdge> edgesList;
		Position<AdjacentListVertex> position;		
		V element;
		
		public AdjacentListVertex(V element) {
			this.element = element;
			edgesList = new LinkedList<AdjacentListEdge>();
		}
		
		public Position<AdjacentListEdge> insertEdge(AdjacentListEdge e){
			Position<AdjacentListEdge> edge = edgesList.insertLast(e);
			if (this.equals(e.start))
				e.post_p = edge;
			else
				e.post_n = edge;
			return edge;
		}
		
		@Override
		public V element() {
			return element;
		}
		
		public String toString() {
			return element.toString();
		}
	}
	
	private class AdjacentListEdge implements Edge<E> {
		Position<AdjacentListEdge> position;
		Position<AdjacentListEdge> post_p, post_n;		
		E element;
		AdjacentListVertex start, end;
		
		public AdjacentListEdge(AdjacentListVertex start, AdjacentListVertex end, E element) {
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
	
	private List<AdjacentListVertex> vertices;
	private List<AdjacentListEdge> edges;
	
	public AdjacentListGraph() {
		vertices = new LinkedList<AdjacentListVertex>();
		edges = new LinkedList<AdjacentListEdge>();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Vertex<V>[] endVertices(Edge<E> e) {
		AdjacentListEdge edge = (AdjacentListEdge) e;
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
		if ( ((AdjacentListVertex)v).edgesList.size() < ((AdjacentListVertex)w).edgesList.size()){
			for (AdjacentListEdge edge: ((AdjacentListVertex)v).edgesList) {
				if (opposite(v, edge).equals(w)) return true;
			}
		}else{
			for (AdjacentListEdge edge: ((AdjacentListVertex)w).edgesList) {
				if (opposite(w, edge).equals(v)) return true;
			}
		}
		return false;
	}

	@Override
	public V replace(Vertex<V> v, V x) {
		AdjacentListVertex vertex = (AdjacentListVertex) v;
		V temp = vertex.element;
		vertex.element = x;
		return temp;
	}

	@Override
	public E replace(Edge<E> e, E x) {
		AdjacentListEdge edge = (AdjacentListEdge) e;
		E temp = edge.element;
		edge.element = x;
		return temp;
	}

	@Override
	public Vertex<V> insertVertex(V v) {
		AdjacentListVertex vertex = new AdjacentListVertex(v);
		Position<AdjacentListVertex> position = vertices.insertLast(vertex);
		vertex.position = position;
		return vertex;
	}

	@Override
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E o) {
		AdjacentListEdge edge = new AdjacentListEdge((AdjacentListVertex) v, (AdjacentListVertex) w, o);
		edge.post_p = ((AdjacentListVertex)v).insertEdge(edge);
		edge.post_n = ((AdjacentListVertex)w).insertEdge(edge);
	
		Position<AdjacentListEdge> position = edges.insertLast(edge);
		edge.position = position;
		return edge;
	}

	@Override
	public V removeVertex(Vertex<V> v) {
		Iterator<Edge<E>> it = incidentEdges(v).iterator();
		while (it.hasNext()) it.remove();
		
		AdjacentListVertex vertex = (AdjacentListVertex) v;
		vertices.remove(vertex.position);
		return vertex.element;
	}

	@Override
	public E removeEdge(Edge<E> e) {
		AdjacentListEdge edge = (AdjacentListEdge) e;
		edges.remove(edge.position);
		edge.start.edgesList.remove(edge.post_p);
		edge.end.edgesList.remove(edge.post_n);
		return edge.element;
	}

	@Override
	public List<Edge<E>> incidentEdges(Vertex<V> v) {
		LinkedList<Edge<E>> list = new LinkedList<Edge<E>>();		
		for (AdjacentListEdge edge : ((AdjacentListVertex)v).edgesList) {
			list.insertLast(edge);
		}		
		return list;
	}

	@Override
	public List<Vertex<V>> vertices() {
		LinkedList<Vertex<V>> list = new LinkedList<Vertex<V>>();
		for (AdjacentListVertex vertex : vertices) {
			list.insertLast(vertex);
		}
		return list;
	}

	@Override
	public List<Edge<E>> edges() {
		LinkedList<Edge<E>> list = new LinkedList<Edge<E>>();
		for (AdjacentListEdge edge : edges) {
			list.insertLast(edge);
		}
		return list;
	}

}
