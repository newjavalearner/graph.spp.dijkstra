package spp.dijkstra;

public class Edge {
	public int weight;
	public Vertex target;
	public Edge(Vertex end, int linkcost){
		target = end;
		weight = linkcost;
	}
}
