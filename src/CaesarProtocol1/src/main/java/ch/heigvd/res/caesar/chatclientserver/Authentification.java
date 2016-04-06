/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.res.caesar.chatclientserver;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Norah
 */
public class Authentification implements Runnable {
       

        private Socket socket;

        private PrintWriter out = null;

        private BufferedReader in = null;

        private String login = "", delta = "";

        public boolean authentifier = false;

        public Thread t2;

        public Authentification(Socket s) {

            socket = s;

        }

        public void run() {

            try {

                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                out = new PrintWriter(socket.getOutputStream());

                while (!authentifier) {

                    out.println("Entrez votre nom:");

                    out.flush();

                    login = in.readLine();

                    out.println("Entrez le delta :");

                    out.flush();

                    delta = in.readLine();

                    if (isValid(login, delta)) {

                        out.println("connecte");

                        System.out.println(login + " vient de se connecter ");

                        out.flush();

                        authentifier = true;

                    } else {
                        out.println("erreur");
                        out.flush();
                    }

                }

                t2 = new Thread(new Chat_ClientServeur(socket, login, delta));

                t2.start();

            } catch (IOException e) {

                System.err.println(login + " ne répond pas !");

            }

        }
          private boolean isValid(String login, String pass) {

            boolean connexion = false;
            if (!login.equals("") && !pass.equals("")) {
                connexion = true;
            }
            return connexion;
        }
    }


