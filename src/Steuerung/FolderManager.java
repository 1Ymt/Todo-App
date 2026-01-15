package Steuerung;
import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class FolderManager extends JPanel{

    private Steuerung steuerung;
    private CardLayout cardLayout;
    private ArrayList<JPanel> ordnerArray;

    public FolderManager(Steuerung steuerung) {
        this.steuerung = steuerung;
        this.ordnerArray = new ArrayList<>();

        this.cardLayout = new CardLayout();
        this.setLayout(cardLayout);
    }

    public void addMainMenu(JPanel mainMenu) {
        this.add(mainMenu, ("1"));
        this.revalidate();
    }

    public void next(JPanel panel) {
        this.add(panel);
        ordnerArray.add(panel);

        this.revalidate();
        this.repaint();

        cardLayout.next(this);
    }

    public void back(JPanel panel) {
        cardLayout.previous(this);
        int lastIndex = ordnerArray.size() - 1;
        this.remove(ordnerArray.get(lastIndex));
        ordnerArray.remove(lastIndex);
        this.revalidate();
        this.repaint();
    }
}
