package spp.dijkstra;

import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Dijkstra {
	
	public List<Vertex> fullpath;
	public ArrayList<ArrayList<Vertex>> paths = new ArrayList<ArrayList<Vertex>>();
	public int distance;
	
	public int hasNode(ArrayList<ArrayList<Vertex>> temppaths){
		int numberofnodes = 0;
	    for (ArrayList<Vertex> sgpath:temppaths){
	    	Vertex latestnode = sgpath.get(sgpath.size()-1);
	    	if(latestnode.fornodes!=null){
	    		numberofnodes++;
	    	}
	    }
	    return numberofnodes;
	}
	
	public void getSP(String origin, String destination, Vertex[] network){
		//initialization
		Vertex source = null;
		Vertex target = null;
		//get the source and target node from the network, given the name of OD
		for (Vertex node : network){
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
	    for (Vertex node:network){
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
	    ArrayList<ArrayList<Vertex>> temppaths = new ArrayList<ArrayList<Vertex>>();
	    ArrayList<Vertex> incrementpath = new ArrayList<Vertex>();
	    incrementpath.add(target);
	    temppaths.add(incrementpath);
	    
	    for (ArrayList<Vertex> path : temppaths){
			System.out.println("Path: " + path);
		}
	    int counter = 0;
	    while(hasNode(temppaths)>0){
	    	for(ArrayList<Vertex> sgpath:temppaths){
	    		Vertex latestnode = sgpath.get(sgpath.size()-1);
	    		System.out.println("current last node's forward nodes: " + latestnode.fornodes);
		    	if(latestnode.fornodes!=null){
		    		for(Vertex node : latestnode.fornodes){
		    			System.out.println("current node: " + node);
		    			ArrayList<Vertex> newpath = sgpath;
			    		newpath.add(node);
			    		System.out.println("Path: " + newpath);
			    		temppaths.add(counter, newpath);
			    		counter ++;
		    		}
		    	temppaths.remove(sgpath);
		    	}
	    	}
	    }
	    this.paths = temppaths;
	    List<Vertex> path = new ArrayList<Vertex>();
	    path.add(target);
	    Vertex temp = target.prenode;
	    while(temp!=null){
	    	path.add(temp);
	    	temp = temp.prenode;
	    }
	    Collections.reverse(path);
		this.distance = target.mindist;
		this.fullpath = path;
	}
	
}
