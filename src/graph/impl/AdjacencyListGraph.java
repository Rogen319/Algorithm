package graph.impl;

import graph.core.Edge;
import graph.core.Graph;
import graph.core.InvalidVertexException;
import graph.core.Vertex;
import graph.util.LinkedList;
import graph.util.List;
import graph.util.Position;

import java.util.Iterator;

public class AdjacencyListGraph<V,E> implements Graph<V,E> {
	
	private class AdjListVertex implements Vertex<V> {
		Position<AdjListVertex> position;
		LinkedList<AdjListEdge> adjList;
		V element;
		
		public AdjListVertex(V element) {
			this.element = element;
			adjList = new LinkedList<AdjListEdge>();
		}
		
		@Override
		public V element() {
			return element;
		}
		
		public String toString() {
			return element.toString();
		}
	}
	
	private class AdjListEdge implements Edge<E> {
		Position<AdjListEdge> position;
		Position<AdjListEdge> startposition;
		Position<AdjListEdge> endposition;
		E element;
		AdjListVertex start, end;
		
		public AdjListEdge(AdjListVertex start, AdjListVertex end, E element) {
			this.start = start;
			this.end = end;
			this.element = element;
			startposition = start.adjList.insertLast(this);
			endposition = end.adjList.insertLast(this);
		}
		
		@Override
		public E element() {
			return element;
		}
		
		public String toString() {
			return element.toString();
		}
	}
	
	private List<AdjListVertex> vertices;
	private List<AdjListEdge> edges;
	
	public AdjacencyListGraph(){
		vertices = new LinkedList<AdjListVertex>();
		edges = new LinkedList<AdjListEdge>();
	}
	
	@Override
	public Vertex<V>[] endVertices(Edge<E> v) {
		AdjListEdge e = (AdjListEdge) v;
		Vertex<V>[] vs = (Vertex<V>[]) new Vertex[2];
		vs[0] = e.start;
		vs[1] = e.end;
		return vs;
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
		for (AdjListEdge edge: ((AdjListVertex)v).adjList) {
			if (edge.end.equals(w)) return true;
			if (edge.start.equals(w)) return true;
		}
		return false;
	}

	@Override
	public V replace(Vertex<V> v, V x) {
		AdjListVertex av = (AdjListVertex) v;
		V temp = av.element;
		av.element = x;
		return temp;
	}

	@Override
	public E replace(Edge<E> e, E x) {
		AdjListEdge ae = (AdjListEdge) e;
		E temp = ae.element;
		ae.element = x;
		return temp;
	}

	@Override
	public Vertex<V> insertVertex(V o) {
		AdjListVertex vertex = new AdjListVertex(o);
		Position<AdjListVertex> position = vertices.insertLast(vertex);
		vertex.position = position;
		return vertex;
	}

	@Override
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E o) {
		AdjListEdge edge = new AdjListEdge((AdjListVertex) v, (AdjListVertex) w, o);
		Position<AdjListEdge> position = edges.insertLast(edge);
		edge.position = position;
		return edge;
	}

	@Override
	public V removeVertex(Vertex<V> v) {
		AdjListVertex vertex = (AdjListVertex) v;
		for (AdjListEdge edge: vertex.adjList) {
			removeEdge(edge);
		}
		
		vertices.remove(vertex.position);
		return vertex.element;
	}

	@Override
	public E removeEdge(Edge<E> e) {
		AdjListEdge edge = (AdjListEdge) e;
		edges.remove(edge.position);
		edge.start.adjList.remove(edge.startposition);
		edge.end.adjList.remove(edge.endposition);
		return edge.element;
	}

	@Override
	public List<Edge<E>> incidentEdges(Vertex<V> v) {
		AdjListVertex vertex = (AdjListVertex) v;
		LinkedList<Edge<E>> list = new LinkedList<Edge<E>>();
		for (AdjListEdge edge: vertex.adjList) {
			list.insertLast(edge);
		}
		return list;
	}

	@Override
	public List<Vertex<V>> vertices() {
		LinkedList<Vertex<V>> list = new LinkedList<Vertex<V>>();
		for (AdjListVertex vertex: vertices) {
			list.insertLast(vertex);
		}
		return list;
	}

	@Override
	public List<Edge<E>> edges() {
		LinkedList<Edge<E>> list = new LinkedList<Edge<E>>();
		for (AdjListEdge edge: edges) {
			list.insertLast(edge);
		}
		return list;
	}

}
