import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;

import java.util.Scanner;

public class Client {

	Scanner in;
	RoomInterface room;

	public Client() {
		in = new Scanner(System.in);
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

	public void execute() {
		String comando;
		int input_row, input_column;
		String response;

		try {
			System.out.println("Entre com um dos comandos a seguir:\n \t\treserve <linha> <coluna>\n\t\tfree <linha> <coluna>\n\t\tisAvailable <linha> <coluna>\n\t\tdisplay");
			while (true) {
				comando = in.next();
				if (comando.equalsIgnoreCase("reserve")) {
						input_row = in.nextInt();
						input_column = in.nextInt();
						if(room.isAvailable(input_row, input_column)) {
							room.reserve(input_row, input_column);
							response = "Cadeira " + input_row + "-" + input_column + " reservada";
						} else {
							response = "Cadeira ja reservada ";
						}
					System.out.println(response);
				} else if (comando.equalsIgnoreCase("free")) {
					input_row = in.nextInt();
					input_column = in.nextInt();
					room.free(input_row, input_column);
					System.out.println("Cadeira " + input_row + "-" + input_column + " liberada");
				} else if (comando.equalsIgnoreCase("isAvailable")) {
					input_row = in.nextInt();
					input_column = in.nextInt();
					boolean available = room.isAvailable(input_row, input_column);
					System.out.println(available ? "A cadeira está disponível" : "A cadeira NÃO está disponível");
				} else if (comando.equalsIgnoreCase("display")) {
					boolean[][] chairs = room.display();
					String chairMap = "";
					for (boolean[] row : chairs) {
						for (boolean chair : row) {
							chairMap += chair ? "0" : "1";
						}
						chairMap += "\n";
					}
					System.out.println(chairMap);
				}

				else {
					System.out.println("Saindo do programa");
					break;
				}
			}
			in.close();
		} catch (Exception e) {
			System.out.println("Exception: " + e.toString());
		}
	}

	public static void main(String[] args) {
		Client c = new Client();
		c.execute();
	}
}