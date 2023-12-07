package stratego;

import java.util.ArrayList;
import javax.swing.*;

public class STRATEGO {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameBoard().setVisible(true));
    }
}
