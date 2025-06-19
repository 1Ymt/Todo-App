package Steuerung;
import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class FolderManager extends JPanel{

    private Steuerung steuerung;
    CardLayout cardLayout;
    private ArrayList<JPanel> ordnerArray;

    public FolderManager(Steuerung steuerung, JPanel mainMenu) {
        this.steuerung = steuerung;
        this.ordnerArray = new ArrayList<>();

        this.cardLayout = new CardLayout();
        this.setLayout(cardLayout);

        this.add(mainMenu, "1");
        ordnerArray.add(mainMenu);
    }

    public void next(JPanel panel) {
        this.add(panel);
        ordnerArray.add(panel);
        cardLayout.next(this);
    }

    public void back(JPanel panel) {
        cardLayout.previous(this);
        int lastIndex = ordnerArray.size() - 1;
        this.remove(ordnerArray.get(lastIndex));
        ordnerArray.remove(lastIndex);
    }
}
