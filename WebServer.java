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

    public void execute() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
        server.createContext("/isAvailable", new isAvailable());
        server.createContext("/reserve", new reserve());
        server.createContext("/free", new free());
        server.createContext("/display", new display());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    public static void main(String[] args) throws Exception {
        WebServer webServer = new WebServer();
        webServer.execute();
    }

    static class isAvailable implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {
            String query = t.getRequestURI().getQuery();
            int[] args = getArgs(query);

            String response;
            boolean available = true;
            try {
                available = room.isAvailable(args[0], args[1]);
                response = available ? "A cadeira esta disponivel" : "A cadeira NAO esta disponivel";
            } catch (Exception e) {
                response = "Exception: " + e.toString();
            }

            System.out.println(response);
            sendResponse(t, response);
        }
    }

    static class reserve implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {
            String query = t.getRequestURI().getQuery();
            int[] args = getArgs(query);

            String response;
            try {
                room.reserve(args[0], args[1]);
                response = "Cadeira " + args[0] + "-" + args[1] + " reservada";
            } catch (Exception e) {
                response = "Exception: " + e.toString();
            }

            System.out.println(response);
            sendResponse(t, response);
        }
    }

    static class free implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {
            String query = t.getRequestURI().getQuery();
            int[] args = getArgs(query);

            String response;
            try {
                room.free(args[0], args[1]);
                response = "Cadeira " + args[0] + "-" + args[1] + " liberada";
            } catch (Exception e) {
                response = "Exception: " + e.toString();
            }

            System.out.println(response);
            sendResponse(t, response);
        }
    }

    static class display implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "";
            boolean[][] chairs;
            try {
                chairs = room.display();
                for (boolean[] row : chairs) {
                    for (boolean chair : row) {
                        response += chair ? "0" : "1";
                    }
                    response += "\n";
                }
            } catch (Exception e) {
                response = "Exception: " + e.toString();
            }

            System.out.println(response);
            sendResponse(t, response);
        }
    }

    static private int[] getArgs(String query) {
        Scanner in = new Scanner(query);
        int[] args = new int[2];
        in.useDelimiter(",");
        args[0] = in.nextInt();
        args[1] = in.nextInt();
        in.close();
        return args;
    }

    static private void sendResponse(HttpExchange t, String response) throws IOException  {
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}