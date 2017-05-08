package TestMaven;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by linux on 17-5-8.
 */
public class Client {
    public static void main(String[] args) {

        try {
            Socket scoket = new Socket("127.0.0.1", 8000);
            BufferedReader in = new BufferedReader(new InputStreamReader(scoket.getInputStream()));
            PrintWriter os = new PrintWriter(scoket.getOutputStream());
            BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
            String line = sin.readLine();
            while (!line.equals("bye")) {
                os.println(line);
                os.flush();

                System.out.println("Client:" + line);
                System.out.println("Server:" + in.readLine());

                line = sin.readLine();
            }

            scoket.close();
            in.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }


    }
}
