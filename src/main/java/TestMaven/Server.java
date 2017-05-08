package TestMaven;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 */
public class Server {
    public static void main(String[] args) {

        try {
            ServerSocket server = new ServerSocket(8000);
            Socket clientSocket = server.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
            String line = sin.readLine();
            while (!line.equals("bye")) {
                out.println(line);
                out.flush();
                System.out.println("Server:" + line);
                System.out.println("Client:" + in.readLine());
                line = sin.readLine();
            }
            in.close();
            out.close();
            clientSocket.close();
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
}
