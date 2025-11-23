package UI;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Enums.UiColor;
import Steuerung.Steuerung;

public class MenuBar extends JMenuBar{
    private Steuerung steuerung;

    MenuBar(Steuerung steuerung, AppFrame appFrame) {
        this.steuerung = steuerung;

        this.setBackground(steuerung.getUiColor(UiColor.bgLight));
        this.add(saving());
        this.add(config());
    }

    private JMenu saving() {
        JMenu menuItem = new JMenu("File");

        JMenuItem savingButton = new JMenuItem("Saving");
        savingButton.addActionListener(e -> steuerung.save());
        savingButton.setEnabled(true);

        JMenuItem openButton = new JMenuItem("Open");
        openButton.addActionListener(e -> steuerung.openSaveFile());
        openButton.setEnabled(true);

        menuItem.add(savingButton);
        menuItem.add(openButton);
        return menuItem;
    }

    private JMenu config() {
        JMenu menuItem = new JMenu("Einstellung");

        JMenuItem configButton = new JMenuItem("Config");
        configButton.addActionListener(e -> steuerung.openConfig());
        configButton.setEnabled(true);


        menuItem.add(configButton);
        return menuItem;
    }
}
