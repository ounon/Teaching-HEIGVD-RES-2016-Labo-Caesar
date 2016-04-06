package CaesarProtocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

public class ChatClientServeur implements Runnable {

    private Socket socket;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private Scanner sc;
    private Thread t3, t4;
    private String delta;

    public ChatClientServeur(Socket s, String del) {
        socket = s;
        delta = del;
    }

    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            sc = new Scanner(System.in);

            Thread t4 = new Thread(new Emission(out));
            t4.start();
            Thread t3 = new Thread(new Reception(in));
            t3.start();

        } catch (IOException e) {
            System.err.println("Le serveur distant s'est déconnecté !");
        }
    }

    class Emission implements Runnable {

        private PrintWriter out;

        private String login = null, message = null;

        private Scanner sc = null;

        public Emission(PrintWriter out) {

            this.out = out;

        }

        public void run() {

            sc = new Scanner(System.in);

            while (true) {

                System.out.println("Votre message :");

                message = sc.nextLine();

                out.println(CaesarEncryption.encrypt(message, Integer.parseInt(delta)));
                out.flush();

            }

        }

    }

    class Reception implements Runnable {

        private BufferedReader in;

        private String message = null;

        public Reception(BufferedReader in) {

            this.in = in;

        }

        public void run() {

            while (true) {

                try {

                    message = in.readLine();

                    System.out.println("Le serveur vous dit :" + message);
                    System.out.println("Le serveur vous dit :" + CaesarEncryption.decrypt(message, Integer.parseInt(delta)));

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }

        }

    }

}
