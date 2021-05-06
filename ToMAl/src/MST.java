import java.util.Scanner;
public class MST {
    // Number of vertices in the graph
    int V;
    int parent[];
    
    MST( int noOfNodes) {
    	
    	this.V = noOfNodes;
    	
    	// Array to store constructed MST
    	this.parent = new int[V];
    }
    // A utility function to find the vertex with minimum key
    // value, from the set of vertices not yet included in MST
    int minKey(int key[], Boolean mstSet[]) {
        // Initialize minimum value
        int min = Integer.MAX_VALUE, min_index = -1;
  
        for (int v = 0; v < V; v++)
            if (mstSet[v] == false && key[v] < min) {
                min = key[v];
                min_index = v;
            }
  
        return min_index;
    }
  
    // A utility function to print the constructed MST stored in
    // parent[]
    void printMST(int parent[], int graph[][], int initiator) {
        
    	System.out.println("The spanning tree is:- ");
        for (int i = 0; i < V; i++)
        	if(i!=initiator) {
        		System.out.println( UpdatedRaymondMain.nodeList.get( i ).name + " - " + UpdatedRaymondMain.nodeList.get( parent[i] ).name + "\t" );
        	}
    }
  
    // Function to construct and print MST for a graph represented
    // using adjacency matrix representation
    void primMST(int graph[][], int initiator) {        
        // Key values used to pick minimum weight edge in cut
        int key[] = new int[V];
  
        // To represent set of vertices included in MST
        Boolean mstSet[] = new Boolean[V];
  
        // Initialize all keys as INFINITE
        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }
  
        // Always include first 1st vertex in MST.
        key[initiator] = 0; // Make key 0 so that this vertex is
        // picked as first vertex(initial vertex)
        parent[initiator] = -1; // First node is always root of MST(does not have any parent)
  
        // The MST will have V vertices
        for (int count = 0; count < V-1 ; count++) {
            // Pick the minimum key vertex from the set of vertices
            // not yet included in MST
            int u = minKey(key, mstSet);
  
            // Add the picked vertex to the MST Set
            mstSet[u] = true;
  
            // Update key value and parent index of the adjacent
            // vertices of the picked vertex. Consider only those
            // vertices which are not yet included in MST
            for (int v = 0; v < V; v++)
  
                // graph[u][v] is non zero only for adjacent vertices of m
                // mstSet[v] is false for vertices not yet included in MST
                // Update the key only if graph[u][v] is smaller than key[v]
                if (graph[u][v] != 0 && mstSet[v] == false && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                    //graph[u][v]++;
                }
        }
  
        // print the constructed MST
        printMST(parent, graph, initiator);
    }
  
    public void MSTGenerator( int initiator ) {
        /* Let us create the following graph
        2 3
        (0)--(1)--(2)
        | / \ |
        6| 8/ \5 |7
        | /     \ |
        (3)-------(4)
            9         */
    	
    	Scanner sc = new Scanner(System.in);
    	
        int graph[][] = new int[ V ][ V ];
        /*	{ { 0, 1, 1, 0, 0 },
				{ 0, 0, 1, 1, 0 },
        		{ 0, 0, 0, 1, 1 },
        		{ 0, 0, 0, 0, 1 },
        		{ 0, 0, 0, 0, 0 } } */
                              
        System.out.println("Enter the graph");
        
        for( int i = 0; i < V; i++ ) {
        	for( int j = 0; j < V; j++ ) {
        
        		graph[ i ][ j ] = sc.nextInt();
        	}
        }
        // Print the solution
        primMST(graph, initiator);
    }
}