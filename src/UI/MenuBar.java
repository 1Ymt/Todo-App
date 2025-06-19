package UI;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Steuerung.Steuerung;

public class MenuBar extends JMenuBar{
    private Steuerung steuerung;

    MenuBar(Steuerung steuerung, AppFrame appFrame) {
        this.steuerung = steuerung;
        this.add(saving());
    }

    private JMenu saving() {
        JMenu menuItem = new JMenu("Save");

        JMenuItem savingButton = new JMenuItem("Saving");
        savingButton.addActionListener(e -> steuerung.serialize());
        savingButton.setEnabled(true);

        JMenuItem openButton = new JMenuItem("Open");
        openButton.addActionListener(e -> steuerung.openSaveFile());
        openButton.setEnabled(true);

        menuItem.add(savingButton);
        menuItem.add(openButton);
        return menuItem;
    }
}
