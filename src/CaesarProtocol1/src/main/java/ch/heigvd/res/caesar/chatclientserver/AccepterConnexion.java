/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.res.caesar.chatclientserver;

import ch.heigvd.res.caesar.chatclientserver.Authentification;
import java.io.*;

import java.net.*;

/**
 *
 * @author ISO
 */
public class AccepterConnexion implements Runnable {
    private ServerSocket socketserver = null;

    private Socket socket = null;


    public Thread t1;

    public AccepterConnexion(ServerSocket ss){

     socketserver = ss;

    }

    

    public void run() {

        

        try {

            while(true){

                

            socket = socketserver.accept();

            System.out.println("Un zéro veut se connecter  ");

            

            t1 = new Thread(new Authentification(socket));

            t1.start();

            

            }

        } catch (IOException e) {

            

            System.err.println("Erreur serveur");

        }

        

    }
}
