// Simple weighted graph representation 
// Uses an Adjacency Linked Lists, suitable for sparse graphs

import java.io.*;
import java.util.*; 

public class DF{
    class Node {
        public int vert; //vertex of  node
        public int wgt; //data/value/weight
        public Node next; //next node
    }
    
    // V = number of vertices
    // E = number of edges
    // adj[] is the adjacency lists array
    private int V, E;
    private Node[] adj;
    private Node z; //this is the top node
	private int countConnectedNodes;
    
    // used for traversing graph
    private int[] visited;
    private int id;
    
    
    // default constructor
    public DF(String graphFile)  throws IOException
    {
        int u, v;
        int e, wgt;
        Node t;

        FileReader fr = new FileReader(graphFile);
		BufferedReader reader = new BufferedReader(fr);
	   
        String splits = " +";  // multiple whitespace as delimiter
		String line = reader.readLine();        
        String[] parts = line.split(splits);
        System.out.println("Parts[] = " + parts[0] + " " + parts[1]);
        
        V = Integer.parseInt(parts[0]);
        E = Integer.parseInt(parts[1]);
        
        // create sentinel node
        z = new Node(); 
        z.next = z;
		
        // create adjacency lists, initialized to sentinel node z
        visited = new int[V+1];
		adj = new Node[V+1];        
        for(v = 1; v <= V; ++v)
		{
            adj[v] = z; //adding a new node to the adj list
		}
		
        // read the edges
        System.out.println("Reading edges from text file");
		for(e = 1; e <= E; ++e)
        {
            line = reader.readLine();
            parts = line.split(splits);
            u = Integer.parseInt(parts[0]);
            v = Integer.parseInt(parts[1]); 
            wgt = Integer.parseInt(parts[2]);
            
            System.out.println("Edge " + toChar(u) + "--(" + wgt + ")--" + toChar(v));    
			
			//code to put edge into adjacency list    
            t = new Node();
            t.vert = v;
            t.wgt = wgt;
            t.next = adj[u];
            adj[u] = t;
            
			//to create another node after to put into adj list
            t = new Node();
            t.vert = u;
            t.wgt = wgt;
            t.next = adj[v];
            adj[v] = t;
        }
    }
	
    // convert vertex into char for pretty printing
    private char toChar(int u)
    {  
        return (char)(u + 64);
    }
    
    // method to display the graph representation
    public void display() {
        int v;
        Node n;
        
        for(v=1; v<=V; ++v){
            System.out.print("\nadj[" + toChar(v) + "] ->" );
            for(n = adj[v]; n != z; n = n.next) 
                System.out.print(" |" + toChar(n.vert) + " | " + n.wgt + "| ->");  
        }
        System.out.println("");
    }
	

    // method to initialize Depth First Traversal of Graph
    public void DF( int s) 
    {
		int v;
        id = 0;
		
		for(v = 1; v <= V; ++v) //goes through all the vertics ands keeps going until they have all the vertics
		{
			visited[v] = 0; //sets visited vertics to false
		}
		System.out.print("\nDepth First Graph Traversal\n");
		System.out.println("Starting with Vertex " + toChar(s));
		
		//visit each node if the node is not already visited 
		for(int i = 0; i<=V; i++)
		{
			if(visited[i] == 0) //if the visited element is not visited, it returns 0 and executes the code
			{
				dfVisit(i,s); //calling recursive function
			}
		}
		System.out.print("\n\n");
		System.out.print("the number of connected nodes are " + countConnectedNodes); //prints the number of connected nodes
    }


    // Recursive Depth First Traversal for adjacency list
    private void dfVisit( int prev, int v)
    {
		int u;
		Node t; 
		countConnectedNodes++; //this counts the connected nodes
		visited[v] = ++id; //mark the visited node as true
		System.out.print("\n Visited vertex " + toChar(v) + " along edge " + toChar(prev) + " -- "+ toChar(v));
		
		for(t = adj[v]; t != z; t = t.next)
		{
			u = t.vert;
			if(visited[u] == 0)
			{
				dfVisit(v, u); 
			}
			
		}
    }

    public static void main(String[] args) throws IOException
    {
        int s = 3; //starts from vertex C, since c is 3
        String fname = "myDiscGraph.txt";               

        DF g = new DF(fname);
       
        g.display();      
        g.DF(s);
    }

}