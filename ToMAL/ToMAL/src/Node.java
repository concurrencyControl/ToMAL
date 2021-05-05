import java.util.LinkedList;

public class Node extends Thread
{
	public static Object obj = new Object();
	
	char name; 
	Node localvar, parent;												// Local Variable to keep track of whom to return the token
	boolean flag, token, cs;											// flag to mark new addition to the queue during critical section
	LinkedList<Node> queue = new LinkedList<Node>();					// local queue for each node
	
	//Constructor
	public Node(char name) {
		
		this.name = name;
		flag = false;
		this.localvar = this.parent = null;
	}
	
	//Setting the parent of each node
	public void setParent(Node parent) {
		//method to set parents of each node
		
		this.parent = parent;
		if ( this.parent == null ) 
			//root will be the token holder by default
			
			token = true;
		else 
			token = cs = false;
	}
	
	@Override
	public void run() {
		try {
			// requesting token
			Thread.sleep((long)Math.random() * 1000);
			this.request(true);
		}
		catch( final Exception e) {
			e.printStackTrace();
		}
	}
	
	public void request(boolean req) throws InterruptedException {
			
		// adds the node to its own queue
		synchronized( obj ) {	
			
			if( req ) {
				System.out.println( this.name + " Wants to enter the critical section");
				this.queue.add( this );
			}
		
			// add node to parent queue 
			this.parent.queue.add( this );
		}
		
		if( !this.parent.token )
			// if not requesting
			this.parent.request( false );
		
		else
			this.parent.invoke();
		
	}

	synchronized public void invoke() throws InterruptedException {
			
		UpdatedRaymondMain.printStatus();  //printing status
		
		if( this.cs ) {
			while( this.cs ) {
				//wait until process is out of critical section
			}
		}
		
		if( this.token ) {
			
			// passing token
			if( this.queue.getFirst() == this ) {
				// if requesting node is having token
				this.criticalSection();
			}
			else {
				//if requesting node is not having token
				this.parent = this.queue.getFirst();
				this.parent.parent = null;
				
				// setting flag variables
				this.token = false;
				this.parent.token = true;
				
				System.out.println(this.parent.name + " has the token ");
				
				this.queue.removeFirst();
				
				if( this.queue.size() != 0 || this.localvar != null )
					// if queue of token holder not empty then add to parent's local variable
					this.parent.localvar = this;
				
				this.parent.invoke();
			}
		}
	}
	
	synchronized public void criticalSection() throws InterruptedException 
	{
		//critical section
		System.out.println("Process " + this.name + " is entering Critical Section");
		
		this.cs = true;
		this.queue.removeFirst();
		int length = this.queue.size();
		
		try {
			Thread.sleep((long)( Math.random() * 5000 ));
		}
		catch( final Exception e ) {
			e.printStackTrace();
		}
		
		System.out.println("Process " + this.name + " is coming out of Critical Section");
		
		this.cs = false;
		
		int updatedLen = this.queue.size();		
		// if queue is not empty
		if( this.localvar != null ) {
			this.returnToken(updatedLen - length);
		}
		else {
			if( this.queue.size() != 0 )
				this.invoke();
		}
	}
	
	synchronized public void returnToken(int len) throws InterruptedException {
		
		Node  a = this.localvar;
		
		this.parent = this.localvar;
		this.localvar.parent = null;
		this.token = false;
		this.localvar.token = true;
		
		System.out.println("Process " + this.localvar.name + " has the token.");
		
		for(int i = this.queue.size() - len; i<this.queue.size(); i++) {
			this.localvar.queue.add(this.queue.get(i));
		}
		
		this.localvar = null;
		
		if(a.localvar != null) {
			a.returnToken(len);
		}
		else {
			if( a.queue.size() != 0 )
				a.invoke();
		}
	}
	
}