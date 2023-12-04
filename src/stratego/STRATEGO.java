package stratego;

import java.util.ArrayList;
import javax.swing.*;

public class STRATEGO {

    public static void main(String[] args) {
        //load characters
       //load characters
         //Open the InitMenu
       //InitCharacters initCharacters = InitCharacters.getInstance();
       // Get the array of characters
        //Character[] characters = initCharacters.getCharacters();
        
        SwingUtilities.invokeLater(() -> new InitMenu().setVisible(true));
//          for (Character character : characters) {
//                System.out.println("Found character: " + character.getName());
//            }
        
        
         // Imprimir el estado de cada personaje
        //  for (Character character : characters) {
        //     character.printCharacterStates();
        // }
       
       

        // Open the Login Dialog after InitMenu is closed
//        LoginDialog loginDialog = new LoginDialog(null, true);
//       loginDialog.setVisible(true);
//
//        // Check if login was successful
//        if (loginDialog.isLoginSuccessful()) {
//            // Create and show the game board
//            SwingUtilities.invokeLater(() -> new GameBoard());
//        } else {
//            // Handle failed login or user canceled
//            JOptionPane.showMessageDialog(null, "Login Failed or Canceled", "Error", JOptionPane.ERROR_MESSAGE);
//        }
    }
}
