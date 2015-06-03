package spp.dijkstra;

import java.util.ArrayList;

public class SimpleTest {
	public static void main(String[] args){
		Vertex[] graph = new Vertex[7];
		
		graph[0] = new Vertex("A");
		graph[1] = new Vertex("B");
		graph[2] = new Vertex("C");
		graph[3] = new Vertex("D");
		graph[4] = new Vertex("E");
		graph[5] = new Vertex("F");
		graph[6] = new Vertex("G");

	    // set the edges and weight
		graph[0].postlinks = new Edge[]{ new Edge(graph[1], 8), new Edge(graph[2], 5), new Edge(graph[4], 9)};
		graph[1].postlinks = new Edge[]{ new Edge(graph[3], 4), new Edge(graph[4], 5)};
		graph[2].postlinks = new Edge[]{ new Edge(graph[5], 7) };
		graph[3].postlinks = new Edge[]{ new Edge(graph[6], 2) };
		graph[4].postlinks = new Edge[]{ new Edge(graph[3], 3), new Edge(graph[5], 5) };
		graph[5].postlinks = new Edge[]{ new Edge(graph[6], 2) };
		
		Dijkstra djk = new Dijkstra();
		System.out.println("The origin and destination: " + graph[0] + " and " + graph[3]);
		djk.getNetwork(graph);
		djk.getSP("A", "G");
		System.out.println("The shortest distance: " + djk.distance);
		System.out.println("Paths are: " + djk.paths);
	}
	
}
