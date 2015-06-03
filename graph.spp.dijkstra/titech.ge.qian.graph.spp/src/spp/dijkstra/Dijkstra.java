package spp.dijkstra;

import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Dijkstra {
	
	public List<Vertex> fullpath;
	public ArrayList<String> paths;
	public int distance;
	public Vertex[] graph;
	
	public void getNetwork(Vertex[] network){
		this.graph = network;
	}
	
	public Vertex getLastNode(String sgpath){
		Vertex latestnode = null;
    	for (Vertex node : graph){
			if(node.nodeid.equalsIgnoreCase(sgpath.substring(sgpath.length() - 1))){
				latestnode = node;
			}
		}
    	return latestnode;
	}
	
	public int hasNode(List<String> temppaths){
		int numberofnodes = 0;
	    for (String sgpath:temppaths){
	    	Vertex latestnode = getLastNode(sgpath);
	    	if(!latestnode.fornodes.isEmpty()){
	    		numberofnodes++;
	    	}
	    }
	    return numberofnodes;
	}
	
	public void getSP(String origin, String destination){
		//initialization
		Vertex source = null;
		Vertex target = null;
		//get the source and target node from the network, given the name of OD
		for (Vertex node : graph){
			if(node.nodeid.equalsIgnoreCase(origin)){
				source = node;
			}
			if(node.nodeid.equalsIgnoreCase(destination)){
				target = node;
			}
		}
		source.mindist = 0;
		// This priority queue stores the vertices that formulate the shortest path from the source node to the target node.
		PriorityQueue<Vertex> vtq = new PriorityQueue<Vertex>();
	    for (Vertex node:graph){
	    	vtq.add(node);
	    }
	    //iteration over nodes
	    while (!vtq.isEmpty()){
	    	//fetch the node with highest priority in queue
	    	Vertex cur = vtq.poll();
	    	System.out.println("Currently checking node: " + cur);
		    // stop when find the minimum distance to target node
	    	if (cur.nodeid.equals(target.nodeid)){
		    	break;
		    }
	    	//update the distances from the source to nodes forward
		    for (Edge post : cur.postlinks){
		    	int tempdist = cur.mindist + post.weight;
		    	Vertex postnode = post.target;
		    	if (tempdist < postnode.mindist){
		    		vtq.remove(postnode);
		    		postnode.mindist = tempdist;
		    		System.out.println("current short distance: "+tempdist);
		    		if(!postnode.fornodes.isEmpty()){
		    			System.out.println("not empty");
		    			postnode.fornodes.clear();
			    		postnode.fornodes.add(cur);
		    		}else{
		    			System.out.println("empty");
		    			postnode.fornodes.add(cur);
		    		}
		    		
		    		vtq.add(postnode);
		    	}
		    	else if (tempdist == postnode.mindist){
		    		vtq.remove(postnode);
		    		postnode.mindist = tempdist;
		    		postnode.fornodes.add(cur);
		    		vtq.add(postnode);
		    	}
		    }
	    }
	    //the problem: arraylist of arraylist is used improperly in this implementation
	    List<String> temppaths = new CopyOnWriteArrayList<String>();
	    String incrementpath = target.nodeid;
	    temppaths.add(incrementpath);
	    for (String path : temppaths){
			System.out.println("Paths: " + path);
		}
	    while(hasNode(temppaths)!=0){
	    	for(String sgpath:temppaths){
	    		Vertex latestnode = getLastNode(sgpath);
		    	if(!latestnode.fornodes.isEmpty()){
		    		for(Vertex node : latestnode.fornodes){
		    			System.out.println("current node: " + node);
		    			String newpath = sgpath;
			    		newpath+=node;
			    		temppaths.add(newpath);
		    		}
		    	temppaths.remove(sgpath);
		    	}
	    	}
	    }
	    //reverse the order of nodes to form  forward path;
	    ArrayList<String> pathset = new ArrayList<String>();
	    for (String path : temppaths){
	    	StringBuffer newpath = new StringBuffer(path);
	    	String reversed = newpath.reverse().toString();
	    	pathset.add(reversed);
	    }
	    this.paths = pathset;
		this.distance = target.mindist;
	}
	
}
