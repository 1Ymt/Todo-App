package Steuerung;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

public class FolderManager extends JPanel{

    private Steuerung steuerung;
    private CardLayout cardLayout;
    private HashMap<String, JPanel> panelMap;
    private ArrayList<String> panelNameOrder;

    private String currentName;

    public FolderManager(Steuerung steuerung) {
        this.steuerung = steuerung;
        this.panelMap = new HashMap<>();
        this.panelNameOrder = new ArrayList<>();
        this.currentName = null;

        this.cardLayout = new CardLayout();
        this.setLayout(cardLayout);
    }

    private void registerPanel(String name, JPanel panel) {
        JPanel old = panelMap.get(name);
        if (old != null) {
            this.remove(old);
        }

        panelMap.put(name, panel);
        this.add(panel, name);
    }

    private void showPanel(String name) {
        cardLayout.show(this, name);
        currentName = name;

        this.revalidate();
        this.repaint();
    }

    public void addMainMenu(String name, JPanel mainMenu) {
        registerPanel(name, mainMenu);

        panelNameOrder.clear();
        panelNameOrder.add(name);
        showPanel(name);
    }

    public void firstMenuPanel() {
        if (panelNameOrder.isEmpty()) return;

        String first = panelNameOrder.get(0);

        for (String panelName : new ArrayList<>(panelMap.keySet())) {
            if (!panelName.equals(first)) {
                this.remove(panelMap.get(panelName));
                panelMap.remove(panelName);
            }
        }

        panelNameOrder.clear();
        panelNameOrder.add(first);

        showPanel(first);
    }

    public void removeMainMenu() {
        this.remove(0);
        panelNameOrder.clear();
        panelMap.clear();

        this.revalidate();
    }

    public void reloadPanel(JPanel panel) {
        if (currentName == null) return;

        registerPanel(currentName, panel);
        showPanel(currentName);
    }

    public void next(String name, JPanel panel) {
        registerPanel(name, panel);

        panelNameOrder.add(name);
        showPanel(name);
    }

    public void back() {
        if (panelNameOrder.size() <= 1) return;

        panelNameOrder.remove(panelNameOrder.size() - 1);
        String previous = panelNameOrder.get(panelNameOrder.size() - 1);

        showPanel(previous);
    }
}
