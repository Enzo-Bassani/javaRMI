import java.lang.SecurityManager;
import java.rmi.Naming;

public class Server {
	
    public Server() {  
		if(System.getSecurityManager() == null) {
		     System.setSecurityManager(new SecurityManager());
		}
		try {
	        Naming.rebind("Room", new Room());  
	    }  
	    catch( Exception e ) {  
	        System.out.println( "Nao registrou o objeto: " + e );  
	    }	    
	}
	
	public static void main(String[] args) {
		new Server();
	}

}
