/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.res.caesar.filter;

/**
 *
 * @author ISO
 */
public class CaesarEncryption {
    
    public static String encrypt(String message, int delta){
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String messageEncrypt = "";
        for (int i = 0; i < message.length(); i++){
            if (alphabet.indexOf(message.charAt(i)) == -1)
                    messageEncrypt += message.charAt(i);
            else
                messageEncrypt += alphabet.charAt(alphabet.indexOf(message.charAt(i)) + delta);
        }
        return messageEncrypt;
    }
    
    public static String decrypt(String message, int delta){
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String messageDecrypt = "";
        for (int i = 0; i < message.length(); i++){
            if (alphabet.indexOf(message.charAt(i)) == -1)
                    messageDecrypt += message.charAt(i);
             messageDecrypt += alphabet.charAt(alphabet.indexOf(message.charAt(i)) - delta);
        }
        return messageDecrypt;
    }
    
   
    
}
