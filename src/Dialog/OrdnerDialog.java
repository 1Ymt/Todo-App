package Dialog;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import Enums.UIColor;
import Interface.TodoListController;

import java.awt.*;
import java.awt.event.WindowEvent;

import Steuerung.Steuerung;

public class OrdnerDialog extends JDialog{

    private final Color[] colors = new Color[] {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN, 
                                                Color.MAGENTA, Color.GRAY, Color.BLACK};

    private Steuerung steuerung;
    private TodoListController todoItemClass;

    private JTextField ordnerName;
    private JLabel ordnerIcon;
    private JButton[] colorButton;
    private Color choosenColor;
    ImageIcon checkMarkIcon;
    
    public OrdnerDialog(Steuerung steuerung, JFrame appFrame, TodoListController todoItemClass) {
        super(appFrame, "Ordner", true);
        this.steuerung = steuerung;
        this.todoItemClass = todoItemClass;

        this.checkMarkIcon = new ImageIcon(getClass().getResource("/Icons/checkmark.PNG"));
        this.colorButton = new JButton[colors.length];
        this.choosenColor = Color.CYAN;

        this.setSize(600, 400);
        this.setLayout(new BorderLayout());

        this.add(createMainMenuPanel(), BorderLayout.CENTER);
        this.add(createConfirmPanelMenu(), BorderLayout.SOUTH);
        this.add(createSidePanel(), BorderLayout.WEST);
        this.add(createSidePanel(), BorderLayout.EAST);

        this.setLocationRelativeTo(appFrame); // Der Anzeige wird dann im Zentrum angezeigt.
        this.setVisible(true);
    }
    

    private JPanel createMainMenuPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.setBackground(steuerung.getUiColor(UIColor.bg));
        mainPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, steuerung.getUiColor(UIColor.bgDark), steuerung.getUiColor(UIColor.border)));
        

        mainPanel.add(createOrdnerVisual());
        mainPanel.add(createOrdnerConfigMenu());

        return mainPanel;
    }
    
    private JPanel createOrdnerVisual() {
        JPanel ordnerBackgroundPanel = new JPanel();
        ordnerBackgroundPanel.setBackground(steuerung.getUiColor(UIColor.bg));
        ordnerBackgroundPanel.setLayout(new GridBagLayout());
        

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        
        JPanel ordnerPanel = new JPanel();
        ordnerPanel.setPreferredSize(new Dimension(150,120));
        ordnerPanel.setBackground(steuerung.getUiColor(UIColor.bgLight));
        ordnerPanel.setBorder(BorderFactory.createRaisedBevelBorder());

        ordnerIcon = new JLabel(steuerung.setOrdnerIcon(choosenColor, 100));

        ordnerPanel.add(ordnerIcon);
        ordnerBackgroundPanel.add(ordnerPanel, gbc);
        return ordnerBackgroundPanel;
    }

    private JPanel createOrdnerConfigMenu() {
        JPanel createOrdnerConfigPanel = new JPanel();
        createOrdnerConfigPanel.setLayout(new BoxLayout(createOrdnerConfigPanel, BoxLayout.Y_AXIS));
        createOrdnerConfigPanel.setBackground(steuerung.getUiColor(UIColor.bg));

        JPanel wrapperJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,  5, 5));
        wrapperJPanel.setOpaque(false);

        ordnerName = new JTextField(20);
        ordnerName.setPreferredSize(new Dimension(300, 35));
        ordnerName.setBackground(steuerung.getUiColor(UIColor.bgLight));
        ordnerName.setForeground(steuerung.getUiColor(UIColor.text));

        wrapperJPanel.setMaximumSize(ordnerName.getPreferredSize());
        wrapperJPanel.add(ordnerName);

        JPanel wrapperColorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,  10, 10));
        wrapperColorPanel.setOpaque(false);

        for (int i = 0; i < colorButton.length; i++) {
            wrapperColorPanel.add(createColorChooserButton(i));
        }
        wrapperColorPanel.setMaximumSize(new Dimension(300,40));

        createOrdnerConfigPanel.add(Box.createVerticalGlue());
        createOrdnerConfigPanel.add(wrapperJPanel);
        createOrdnerConfigPanel.add(wrapperColorPanel);
        createOrdnerConfigPanel.add(Box.createVerticalGlue());
        return createOrdnerConfigPanel;
    }


    private JPanel createColorChooserButton(int i) {
        JPanel wrapperButton = new JPanel(new BorderLayout());
        
        colorButton[i] = new JButton();
        colorButton[i].setBackground(colors[i]);
        colorButton[i].addActionListener(e -> colorButtonListener(colorButton[i]));

        if(choosenColor == colorButton[i].getBackground()) {
            colorButton[i].setIcon(checkmarkIcon());
        }
        
        wrapperButton.setPreferredSize(new Dimension(20, 20));
        wrapperButton.add(colorButton[i], BorderLayout.CENTER);
        return wrapperButton;
    }
        
    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(steuerung.getUiColor(UIColor.bgDark));
        sidePanel.setPreferredSize(new Dimension(50, (int) this.getSize().getHeight()));
        return sidePanel;
    }

    /*
     * Panel für die Confirm Menu
     */
    private JPanel createConfirmPanelMenu() {
        JPanel confirmPanel =  new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        confirmPanel.setPreferredSize(new Dimension(this.getWidth(), 50));
        confirmPanel.setBackground(steuerung.getUiColor(UIColor.bgDark));

        confirmPanel.add(createConfirmButtons("Abbrechen"));
        confirmPanel.add(createConfirmButtons("Bestätigt"));

        return confirmPanel;
    }

    /*
     * Hilfsmethode für die createConfirmPanelMenu, um die Bestätig und Abbrechen Button zu erschaffen
     */
    private JPanel createConfirmButtons(String name) {
        JPanel wrapperButton = new JPanel();
        wrapperButton.setOpaque(false);

        JButton button = new JButton(name);
        button.setName(name);
        button.setPreferredSize(new Dimension(100, 30));
        if (name.equals("Bestätigt")) {
            button.setBackground(steuerung.getUiColor(UIColor.primary));
        } else {
            button.setBackground(steuerung.getUiColor(UIColor.info));
        }
        button.setForeground(steuerung.getUiColor(UIColor.highlight));
        button.addActionListener(e -> confirmButtonsListener(button));
        
        wrapperButton.setMaximumSize(button.getPreferredSize());
        wrapperButton.add(button);
        return wrapperButton;
    }

    /*
     * Diese Funktion ist für den Bestätigt und Abbrechen Button verantwortlich. 
     */
    private void confirmButtonsListener(JButton button) {
        if(button.getName().equals("Bestätigt")) {
            steuerung.createOrdner(ordnerName.getText(), choosenColor, this, todoItemClass);

        }else if(button.getName().equals("Abbrechen")) {
            //Wenn das Button betätigt wird, dann schließt sich das Fenster
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

    private void colorButtonListener(JButton sourceButton) {
        sourceButton.setIcon(checkmarkIcon());
        choosenColor = sourceButton.getBackground();
        //ordnerPanel.setBackground(choosenColor);
        ordnerIcon.setIcon(steuerung.setOrdnerIcon(choosenColor, 100));

        for (int i = 0; i < colorButton.length; i++) {
            if(colorButton[i] != sourceButton) { //choosenColor != colorButton[i].getBackground()
                colorButton[i].setIcon(null);
            }
        }
    }

    private ImageIcon checkmarkIcon() {
		int size = 10;
		Image image = checkMarkIcon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(image);
        return icon;
    }
}
