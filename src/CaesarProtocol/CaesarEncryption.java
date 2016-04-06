
package CaesarProtocol;


public class CaesarEncryption {
    
    public static String encrypt(String message, int delta){
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String messageEncrypt = "";
        for (int i = 0; i < message.length(); i++){
            if (alphabet.indexOf(message.charAt(i)) == -1)
                    messageEncrypt += message.charAt(i);
            else
                messageEncrypt += alphabet.charAt((alphabet.indexOf(message.charAt(i)) + delta)%26);
        }
        return messageEncrypt;
    }
    
    public static String decrypt(String message, int delta){
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String messageDecrypt = "";
        for (int i = 0; i < message.length(); i++){
            if (alphabet.indexOf(message.charAt(i)) == -1)
                    messageDecrypt += message.charAt(i);
            else
                 messageDecrypt += alphabet.charAt((Math.abs(alphabet.indexOf(message.charAt(i)) - delta))%26);
        }
        return messageDecrypt;
    }
    
   public static void main(String[] args) {
   String message = "thibaut";
   String messagede = "";
      
       messagede = encrypt(message, 3);
        System.out.println(messagede);
        System.out.println(decrypt(messagede, 3));
  }
    
}
