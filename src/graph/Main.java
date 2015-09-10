package graph;

import java.util.Comparator;

import graph.algorithm.BreadthFirstSearchAlgorithm;
import graph.algorithm.DepthFirstSearchAlgorithm;
import graph.algorithm.DijkstraShortestPathAlgorithm;
import graph.algorithm.DijkstraShortestPathTreeAlgorithm;
import graph.algorithm.KruskalMSTAlgorithm;
import graph.algorithm.PrimJarnikMSTAlgorithm;
import graph.gui.JGraphFrame;
import graph.impl.AdjacentListGraph;
import graph.impl.AdjacentMatrixGraph;
import graph.impl.EdgeListGraph;
import graph.util.UncomparableException;

@SuppressWarnings("unused")
public class Main {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
/*		String s1 = new String("1");
		String s2 = new String("10");
		String s3 = new String("100");
		Comparator<String> comparator = new Comparator<String>() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public int compare(String key0, String key1) {
				if (Comparable.class.isInstance(key0)) {
					return ((Comparable) key0).compareTo(key1);
				}
				
				throw new UncomparableException();
			}
			
		};
		System.out.println(comparator.compare(s1, s2));
		System.out.println(comparator.compare(s2, s1));
		System.out.println(comparator.compare(s2, s3));
		System.out.println(comparator.compare(s3, s2));
		System.out.println(comparator.compare(s1, s1));
		System.out.println(comparator.compare(s2, s2));
*/
		JGraphFrame frame = new JGraphFrame(new EdgeListGraph<String, String>(), "answer_3.gra");
		frame.registerGraphAlgorithm("Complete Breadth FIrst Search Overlay", new BreadthFirstSearchAlgorithm<String, String>());
		frame.registerGraphAlgorithm("Complete Depth First Search Overlay", new DepthFirstSearchAlgorithm<String, String>());
		frame.registerGraphAlgorithm("Dijkstra Shortest Path Tree Overlay", new DijkstraShortestPathTreeAlgorithm<String, String>());
		frame.registerGraphAlgorithm("Dijkstra Shortest Path Overlay", new DijkstraShortestPathAlgorithm<String, String>());
		frame.registerGraphAlgorithm("Kruskal Overlay", new KruskalMSTAlgorithm<String, String>());
		frame.registerGraphAlgorithm("Prim Jarnik MST Overlay", new PrimJarnikMSTAlgorithm<String,String>());
		frame.setVisible(true);
	}


}
