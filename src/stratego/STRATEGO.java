package stratego;

import java.util.ArrayList;
import javax.swing.*;

public class STRATEGO {

    public static void main(String[] args) {
        // Open the InitMenu
        SwingUtilities.invokeLater(() -> new InitMenu().setVisible(true));
       

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
