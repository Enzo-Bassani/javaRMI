import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.util.Scanner;

public class WebServer {

    static RoomInterface room;

    public WebServer() {
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

    static class isAvailable implements HttpHandler {

        int input_row, input_column;
        Scanner in;

        @Override
        public void handle(HttpExchange t) throws IOException {
            String query = t.getRequestURI().getQuery();
            in = new Scanner(query);
            in.useDelimiter(",");
            input_row = in.nextInt();
            input_column = in.nextInt();

            boolean available = true;
            try {
                available = room.isAvailable(input_row, input_column);
            } catch (Exception e) {
                System.out.println("Bruh");
            }

            String response = available ? "A cadeira esta disponivel" : "A cadeira NAO esta disponivel";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    public void execute() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
        server.createContext("/isAvailable", new isAvailable());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    public static void main(String[] args) throws Exception {
        WebServer webServer = new WebServer();
        webServer.execute();
    }

}