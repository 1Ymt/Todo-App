package UI;
import java.awt.*;
import javax.swing.*;

import Steuerung.Steuerung;

public class AppFrame extends JFrame{

    private Steuerung steuerung;

    public AppFrame(Steuerung steuerung) {
        this.steuerung = steuerung;

        this.setTitle("Todo App");
        this.setSize(600, 1000);
        this.setBackground(Color.BLACK);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        MenuBar menuBar = new MenuBar(steuerung, this);
        this.setJMenuBar(menuBar);
        
        this.setVisible(true);
    }

    public void addMainPanel(JPanel panel) {
        this.add(panel);
        
        invalidate();
        validate();
        repaint();
    }
}
