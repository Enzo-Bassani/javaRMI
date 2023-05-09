import java.rmi.RemoteException;
import java.rmi.Remote;

public interface RoomInterface extends Remote{

	public boolean isAvailable(int row, int column) throws RemoteException, Exception;
	public void reserve(int row, int column) throws RemoteException, Exception;
	public void free(int row, int column) throws RemoteException, Exception;
	public boolean[][] display() throws RemoteException;
	
}
