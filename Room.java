import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Room extends UnicastRemoteObject implements RoomInterface {
	private Chair[][] chairs;
	private static int ROWS = 20;
	private static int COLUMNS = 20;
	private static String invalidArgMsg = "Cadeira %d-%d não existe. Formato da sala: " + ROWS + "x" + COLUMNS;

	public Room() throws RemoteException {
		chairs = new Chair[ROWS][COLUMNS];
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				chairs[i][j] = new Chair();
			}
		}
	}

	@Override
	public boolean isAvailable(int row, int column) throws RemoteException, Exception {
		if (!validInput(row, column)) {
			throw new Exception(String.format(invalidArgMsg, row, column));
		}
		return chairs[row][column].isAvailable();
	}

	public void reserve(int row, int column) throws RemoteException, Exception {
		if (!validInput(row, column)) {
			throw new Exception(String.format(invalidArgMsg, row, column));
		}
		chairs[row][column].reserve();
	}

	public void free(int row, int column) throws RemoteException, Exception {
		if (!validInput(row, column)) {
			throw new Exception(String.format(invalidArgMsg, row, column));
		}
		chairs[row][column].free();
	}

	@Override
	public boolean[][] display() throws RemoteException {
		// return chairs;
		boolean [][] chairsMap = new boolean[ROWS][COLUMNS];
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				chairsMap[i][j] = chairs[i][j].isAvailable();
			}
		}
		return chairsMap;
	}

	private static boolean validInput(int row, int column) {
		return row < ROWS && column < COLUMNS;
	}

}
