package Steuerung;
import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class FolderManager extends JPanel{

    private Steuerung steuerung;
    CardLayout cardLayout;
    private ArrayList<JPanel> ordner;

    public FolderManager(Steuerung steuerung, JPanel mainMenu) {
        this.steuerung = steuerung;
        this.ordner = new ArrayList<>();

        this.cardLayout = new CardLayout();
        this.setLayout(cardLayout);

        this.add(mainMenu, "1");
        ordner.add(mainMenu);
    }

    public void next(JPanel panel) {
        this.add(panel);
        ordner.add(panel);
        cardLayout.next(this);
    }

    public void back(JPanel panel) {
        cardLayout.previous(this);
        int lastIndex = ordner.size() - 1;
        this.remove(ordner.get(lastIndex));
        ordner.remove(lastIndex);
    }
}
