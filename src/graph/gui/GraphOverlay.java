package graph.gui;

import graph.core.Edge;
import graph.core.Vertex;

import java.awt.Color;


public interface GraphOverlay {
	@SuppressWarnings("rawtypes")
	public Color edgeColor(Edge edge);
	@SuppressWarnings("rawtypes")
	public Color vertexColor(Vertex vertex);
}
