import java.rmi.RemoteException;
import java.rmi.Remote;

public interface RoomInterface extends Remote{

	public boolean[][] display() throws RemoteException;
	public boolean isAvailable(int row, int column) throws RemoteException;
	public void reserve(int row, int column) throws RemoteException;
	public void free(int row, int column) throws RemoteException;
	
}
