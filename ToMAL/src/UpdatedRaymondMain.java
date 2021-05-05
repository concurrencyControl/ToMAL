import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class UpdatedRaymondMain {

	public static ArrayList<Node> nodeList = new ArrayList<Node>();
		
	@SuppressWarnings("unchecked")
	synchronized public static void printStatus() throws InterruptedException{
		//prints the status

		System.out.println("The lists of the nodes in the queue are:\n");
		
		for(Node i : nodeList ) 
		{
			// for each node in node list
			System.out.print( i.name + " : " );
			
			if( i.queue.size() == 0 ) // local queue empty
				System.out.println("The queue is empty.");
			
			else {
				LinkedList<Node> sec_list = new LinkedList<Node>();
		        sec_list = (LinkedList<Node>) i.queue.clone();
				for(Node j : sec_list ) {
					//print the local queue of each node
					
					System.out.print( j.name + " " );
				}
			}
			System.out.println();	
			
		}	
		
		System.out.println("The lists of the local variables are are:\n");
		for(Node i : nodeList )
		{
			if(i.localvar==null)
			{
				System.out.print( "local variable of " + i.name + " : " + i.localvar + "\n" );
			}
			else
			{
				System.out.print( "local variable of " + i.name + " : " + i.localvar.name + "\n" );
			}
			
			// for each node in node list
			//System.out.print( "local variable of " + i.name + " : " + i.localvar.name + "\n" );
			
		}
		
	}
	
	
	public static void main(String[] args) throws InterruptedException
	{
		// main 
		
		Scanner sc = new Scanner(System.in);		
		
		// taking the names of each node as characters (space separated)
		
		System.out.println("Enter the name of nodes:-     ");
		String names = sc.nextLine();
		names = names.replace(" ","");

		int i, len = names.length();
		
		for( i = 0; i < len; i++ ) {
			// creating objects
			nodeList.add( new Node( names.charAt(i) ) );
		}
		
		System.out.println("Enter the name of the node having the token:-  ");
		char root = sc.next().charAt(0);				//initiator
		Node rootnode = null;
		MST tree = new MST(len);
		
		for( Node j : nodeList )
			if( j.name == root )
				rootnode = j;
		
		tree.MSTGenerator( nodeList.indexOf( rootnode ) );
		
		nodeList.get(names.indexOf(root)).setParent(null);
		
		for( Node j : nodeList ) {
			// setting their parents
			if ( j.name != root ) {

				Node par = nodeList.get( tree.parent[ nodeList.indexOf( j ) ] ); 
				j.setParent( par );
			}
		}
		
		printStatus();  // default status
		
		for( Node j : nodeList ) {
			// start
			if( j.parent != null ) {
				j.start();
			}
		}
	}
}