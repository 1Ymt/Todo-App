package UI;
import java.awt.*;
import javax.swing.*;

import Steuerung.Steuerung;

public class AppFrame extends JFrame{

    private Steuerung steuerung;

    public AppFrame(Steuerung steuerung, JPanel folderManager) {
        this.steuerung = steuerung;

        this.setTitle("Todo App");
        this.setSize(1000, 600);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(500, 100));
        this.setResizable(true);

        MenuBar menuBar = new MenuBar(steuerung, this);
        this.setJMenuBar(menuBar);
        this.add(folderManager);

        this.setVisible(true);
    }
}
