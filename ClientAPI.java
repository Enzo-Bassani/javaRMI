import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;

import java.util.Scanner;

public class ClientAPI {

	RoomInterface room;

	public ClientAPI() {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			room = (RoomInterface) Naming.lookup("rmi://127.0.0.1/Room");
		} catch (RemoteException e) {
			System.out.println();
			System.out.println("RemoteException: " + e.toString());
		} catch (NotBoundException e) {
			System.out.println();
			System.out.println("NotBoundException: " + e.toString());
		} catch (Exception e) {
			System.out.println();
			System.out.println("Exception: " + e.toString());
		}
	}



    public void reservaCadeira(int linha, int coluna) {
        try{    
			room.reserve(linha, coluna);
		} catch (Exception e) {
        }
    }  
        
            
	public void liberarCadeira(int linha, int coluna) {
    	try{
    	    room.free(linha, coluna);
        } catch (Exception e) {
        }
    }

	
    public void isValid(int linha, int coluna) {
		try{
        	boolean available = room.isAvailable(linha, coluna);
        } catch (Exception e) {
	}
    }
	public static void main(String[] args) {
		new ClientAPI();
	}
}