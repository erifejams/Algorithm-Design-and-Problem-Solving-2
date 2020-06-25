// Simple weighted graph representation 
// Uses an Adjacency Linked Lists, suitable for sparse graphs

import java.io.*;
import java.util.*; 


class Heap
{
    private int[] a;	   // heap array
    private int[] hPos;	   // hPos[a[k]] == k
    private int[] dist;    // dist[v] = priority of v

    private int N;         // heap size
   
    // The heap constructor gets passed from the Graph:
    //    1. maximum heap size
    //    2. reference to the dist[] array
    //    3. reference to the hPos[] array
    public Heap(int maxSize, int[] _dist, int[] _hPos) 
    {
        N = 0;
        this.a = new int[maxSize + 1];
        this.dist = _dist;
        this.hPos = _hPos;
    }


    public boolean isEmpty() 
    {
        return N == 0;
    }


    public void siftUp( int k) 
    {
        int v = a[k];

		a[0] = 0;
		dist[0] =0;
		while(dist[v] > dist[a[k/2]])
		{
			a[k] = a[k/2];
			hPos[a[k]] = k;
			
			k = k/2;
		}
		a[k] = v;
		hPos[v] = k;
    }


    public void siftDown( int k) 
    {
       int v, j;
	   
	   v = a[k];
	   while(k <= N/2){
		   j = 2 * k;
		   if(j <= N && dist[a[j]] < dist[a[j + 1]])
			   ++j;
		   if(dist[v] >= dist[a[j]])
			   break;
		   a[k] = a[j];
		   k = j;
	   }
	   a[k] = v;
	   hPos[v] = k;
    }


    public void insert( int x) 
    {
        a[++N] = x;
        siftUp(N);
    }


    public int remove() 
    {   
        int v = a[1];
        hPos[v] = 0; // v is no longer in heap
        a[1] = a[N--];
        siftDown(1);
        a[N+1] = 0;  // put null node into empty spot
        return v;
    }

}


public class MST {
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
	private int[] mst;
    
    // used for traversing graph
    private int[] visited;
    private int id;
    
    
    // default constructor
    public MST(String graphFile)  throws IOException
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
			
			// write code to put edge into adjacency list    
            t = new Node();
            t.vert = v;
            t.wgt = wgt;
            t.next = adj[u];
            adj[u] = t;
            
			//to create another node after
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
		for(v = 1; v <= V; ++v) //goes through all the vertics ands keps going until they hava all the vertics
		{
			visited[v] = 0; //sets visited vertics to false
		}
		System.out.print("\nDepth First Graph Traversal\n");
		System.out.println("Starting with Vertex " + toChar(s));
		
		//calling recursive function
		dfVisit(0,s);
		
		System.out.print("\n\n");
    }


    // Recursive Depth First Traversal for adjacency matrix0
    private void dfVisit( int prev, int v)
    {
		int u;
		Node t;
		
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
	
	public void DF_iteration(int s)
	{ 
		Node t;
		int v,u;
		Stack<Integer> stack = new Stack<Integer>();
		
		id = 0;
		for(v = 0; v <= V; ++v)
		{
			visited[v] = 0;
		}
		
		stack.push(s);
		while(stack.isEmpty() == false) //check to see if the stack is empty
		{
			v = stack.pop();
			if(visited[v] == 0) //if it had not been visited, then it is marked as visited
			{
				visited[v] = ++id;
				System.out.print("\n DF just visited vertex " + toChar(v));
				
				for(t = adj[v]; t != z; t = t.next)
				{
					u = t.vert;
					if(visited[u] == 0)
					{
						stack.push(u);
					}
					
				}
			}
		}
	}
	
	
	public int[] MST_Prim(int s)
	{
        int v,u;
        int wgt, wgt_sum = 0;
        int[]  dist, parent, hPos;
        Node t;
		
		
		parent = new int[V +1];
		hPos = new int[V+1];
		dist = new int[V+1];
        //u = t.vert;
		//wgt = t.wgt;
		
		for(v = 1; v <=V; v++)
		{
			dist[v] = Integer.MAX_VALUE; 
			parent[v] = 0; //treat 0 as a special null vertex
			hPos[v] = 0; //indicates that v is not an element of the heap
		}
		
		//dist[s] =0;
		
        Heap h =  new Heap(V + 1, hPos, dist); //priority queue(heap) initially empty
        h.insert(s); //s will be the root of the MST
        
		dist[s] = 0;
		
        while (!h.isEmpty())  //should repeat |V| -1 times
        {
            v = h.remove(); //add v to the MST
			dist[v] = -dist[v]; //marks v as now in the MST
			for(t = adj[v]; t != z; t = t.next) //examine each neighbour u of v
			{
				if(t.wgt < dist[t.vert])
				{
					dist[t.vert] = t.wgt;
					parent[t.vert] = v;
					wgt_sum = wgt_sum + t.wgt;
					if(hPos[t.vert] == 0)
					{
						h.insert(t.vert);
					}
					else
					{
						h.siftUp(hPos[t.vert]);
					}//end if
				}//end if
			}//end for
            
        }//end while
        System.out.print("\n\nWeight of MST = " + wgt_sum );
        
        mst = parent; 
		return parent;
	}
    
    public void showMST(int[] mst)
    {
		System.out.print("\n\nMinimum Spanning tree parent array is:\n");
		for(int v = 1; v <= V; ++v)
		{
			System.out.println(toChar(v) + " -> " + toChar(mst[v]));
		}
		System.out.println("");
    }


    public static void main(String[] args) throws IOException
    {
		int [] mst;
        int s = 3; 
        String fname = "sGraph.txt";               

        MST g = new MST(fname);
       
        g.display();      
        g.DF(s);
		g.DF_iteration(s);
		mst = g.MST_Prim(s); 
		g.showMST(mst);
    }

}

