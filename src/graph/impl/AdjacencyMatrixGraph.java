package graph.impl;

import graph.core.Edge;
import graph.core.Graph;
import graph.core.InvalidVertexException;
import graph.core.Vertex;
import graph.util.LinkedList;
import graph.util.List;
import graph.util.Position;

import java.util.Iterator;

public class AdjacencyMatrixGraph<V,E> implements Graph<V,E>{
	private class MatrixVertex implements Vertex<V> {
		Position<MatrixVertex> position;
		V element;
		int key;
		
		public MatrixVertex(V element) {
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
	
	private class MatrixEdge implements Edge<E> {
		Position<MatrixEdge> position;
		E element;
		MatrixVertex start, end;
		
		public MatrixEdge(MatrixVertex start, MatrixVertex end, E element) {
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
	
	private List<MatrixVertex> vertices;
	private List<MatrixEdge> edges;
	private Edge<E>[][] adjMatrix;
	
	public AdjacencyMatrixGraph(){
		vertices = new LinkedList<MatrixVertex>();
		edges = new LinkedList<MatrixEdge>();
		adjMatrix = (Edge<E>[][]) new Edge[1][1];
	}
	
	@Override
	public Vertex<V>[] endVertices(Edge<E> v) {
		MatrixEdge edge = (MatrixEdge) v;
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
		MatrixVertex vertexv = (MatrixVertex) v;
		MatrixVertex vertexw = (MatrixVertex) w;
		if (adjMatrix[vertexv.key][vertexw.key] == null)
			return false;
		else
			return true;
	}

	@Override
	public V replace(Vertex<V> v, V x) {
		MatrixVertex vertex = (MatrixVertex) v;
		V temp = vertex.element;
		vertex.element = x;
		return temp;
	}

	@Override
	public E replace(Edge<E> e, E x) {
		MatrixEdge edge = (MatrixEdge) e;
		E temp = edge.element;
		edge.element = x;
		return temp;
	}

	@Override
	public Vertex<V> insertVertex(V o) {
		MatrixVertex vertex = new MatrixVertex(o);
		if (vertices.isEmpty()){
			Position<MatrixVertex> position = vertices.insertLast(vertex);
			vertex.key = 0;
			vertex.position = position;
			return vertex;
		}
		else {
			vertex.key = vertices.last().element().key + 1;
			Position<MatrixVertex> position = vertices.insertLast(vertex);
			vertex.position = position;
			Edge<E>[][] myAdjMatrix = (Edge<E>[][]) new Edge[adjMatrix.length+1][adjMatrix.length+1];
			for (int i = 0;i < adjMatrix.length ; i++) {
				for (int j = 0;j < adjMatrix.length ; j++) {
					myAdjMatrix[i][j] = adjMatrix[i][j]; 
				}
			}
			adjMatrix = myAdjMatrix;
			return vertex;
		}
	}

	@Override
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E o) {
		MatrixVertex vertexv = (MatrixVertex) v;
		MatrixVertex vertexw = (MatrixVertex) w;
		MatrixEdge edge = new MatrixEdge(vertexv, vertexw, o);
		adjMatrix[vertexv.key][vertexw.key] = edge;
		adjMatrix[vertexw.key][vertexv.key] = edge;
		Position<MatrixEdge> position = edges.insertLast(edge);
		edge.position = position;
		return edge;
	}

	@Override
	public V removeVertex(Vertex<V> v) {
		Edge<E>[][] newEdges = (Edge<E>[][])new Edge[adjMatrix.length - 1][adjMatrix.length - 1];
		int toDelete = ((MatrixVertex)v).key;
		for (int i = 0;i < adjMatrix.length;i++) {
			if (i < toDelete) {
				for (int j = 0;j < adjMatrix.length;j++){
					if (j > toDelete)
						newEdges[i][j - 1] = adjMatrix[i][j];
					else if (j < toDelete)
					newEdges[i][j] = adjMatrix[i][j];
				}
			}
			else if (i > toDelete) {
				for (int j = 0;j < adjMatrix.length;j++){
					if (j > toDelete)
						newEdges[i - 1][j - 1] = adjMatrix[i][j];
					else if (j < toDelete)
					newEdges[i - 1][j] = adjMatrix[i][j];
				}
			}
		}
		return null;
	}

	@Override
	public E removeEdge(Edge<E> e) {
		MatrixVertex vertexv = (MatrixVertex) endVertices(e)[0];
		MatrixVertex vertexw = (MatrixVertex) endVertices(e)[1];
		adjMatrix[vertexv.key][vertexw.key] = null;
		adjMatrix[vertexw.key][vertexv.key] = null;
		return e.element();
	}

	@Override
	public List<Edge<E>> incidentEdges(Vertex<V> v) {
		MatrixVertex vertex = (MatrixVertex) v;
		LinkedList<Edge<E>> list = new LinkedList<Edge<E>>();
		for (int j = 0;j < vertices.size();j++) {
			if (adjMatrix[vertex.key][j] == null) continue;
			list.insertLast(adjMatrix[vertex.key][j]);
		}
		return list;
	}

	@Override
	public List<Vertex<V>> vertices() {
		LinkedList<Vertex<V>> list = new LinkedList<Vertex<V>>();
		for (MatrixVertex vertex : vertices) {
			list.insertLast(vertex);
		}
		return list;
	}

	@Override
	public List<Edge<E>> edges() {
		LinkedList<Edge<E>> list = new LinkedList<Edge<E>>();
		for (MatrixEdge edge : edges) {
			list.insertLast(edge);
		}
		return list;
	}

}
