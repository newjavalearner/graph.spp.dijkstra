package spp.dijkstra;

import java.lang.Comparable;
import java.util.ArrayList;

public class Vertex implements Comparable<Vertex>{
	public String nodeid;
	public Vertex prenode;
	public ArrayList<Vertex> fornodes = new ArrayList<Vertex>();
	public Vertex[] backnodes;
	public Edge[] postlinks;
	public int mindist = 10000;
	//initialization of the vertex
	public Vertex(String name){
		this.nodeid = name;
	}
	// cast type to string, return the id of this node
	public String toString() { return nodeid; }
	// compare the node potential
	public int compareTo(Vertex other)
    {
        return Double.compare(mindist, other.mindist);
    }
}
