package CaesarProtocol;

import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStreamReader;

import java.io.PrintWriter;

import java.net.Socket;
import java.util.Scanner;

public class ChatServeurClient implements Runnable {

    private String delta = "";
    private Socket socket = null;

    private BufferedReader in = null;

    private PrintWriter out = null;

    private String login = "";

    private Thread t3, t4;

    public ChatServeurClient(Socket s, String log, String del) {

        socket = s;

        login = log;

        delta = del;
    }

    public void run() {

        try {

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream());

            Thread t3 = new Thread(new Reception(in, login));

            t3.start();

            Thread t4 = new Thread(new Emission(out));

            t4.start();

        } catch (IOException e) {

            System.err.println(login + "s'est déconnecté ");

        }

    }

    class Emission implements Runnable {

        private PrintWriter out;

        private String message = null;

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

        private String message = null, login = null;

        public Reception(BufferedReader in, String login) {

            this.in = in;

            this.login = login;

        }

        public void run() {

            while (true) {

                try {

                    message = in.readLine();

                    System.out.println(login + " : " + message);

                    System.out.println(login + " : " + CaesarEncryption.decrypt(message, Integer.parseInt(delta)));

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }

        }

    }
}
