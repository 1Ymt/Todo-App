package Dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;


import javax.swing.*;
import javax.swing.border.TitledBorder;

import Enums.UIColor;
import Steuerung.Steuerung;
import TodoItem.TaskSegment;

public class TaskSegmentDialog extends JDialog {
    private Steuerung steuerung;
    private TaskSegment taskSegment;
    private JTextArea beschreibungsArea;

    private JTextField titelName;
    private JPanel mainMenu;
    private boolean isEdited;

    public TaskSegmentDialog(Steuerung steuerung, JFrame appFrame, TaskSegment taskSegment, String titel, String beschreibung, boolean isEdit) {
        super(appFrame, "Task", true);
        this.steuerung = steuerung;
        this.taskSegment = taskSegment;
        this.isEdited = isEdit;

        this.titelName = new JTextField(35);
        this.titelName.setText(titel);
        this.beschreibungsArea = new JTextArea(7, 35);
        this.beschreibungsArea.setText(beschreibung);

        this.setSize(500, 350);
        this.setLayout(new BorderLayout());

        this.add(createMainMenuListPanel(), BorderLayout.CENTER);
        this.add(createConfirmPanelMenu(), BorderLayout.SOUTH);
        this.add(createSidePanel(), BorderLayout.WEST);
        this.add(createSidePanel(), BorderLayout.EAST);

        this.setLocationRelativeTo(appFrame); // Die Anzeige wird dann im Zentrum angezeigt.
        this.setVisible(true);
    }

    private JPanel createMainMenuListPanel() {
        mainMenu = new JPanel(new BorderLayout());
        mainMenu.setBackground(steuerung.getUiColor(UIColor.bg));

        mainMenu.add(createTaskNamePanel(), BorderLayout.NORTH);
        mainMenu.add(createBemerkungPanel(), BorderLayout.CENTER);
        return mainMenu;
    }
    
    private JPanel createTaskNamePanel() {
        JPanel taskNameMenu = new JPanel();
        taskNameMenu.setLayout(new GridBagLayout());
        taskNameMenu.setBackground(steuerung.getUiColor(UIColor.bg));
        taskNameMenu.setPreferredSize(new Dimension(mainMenu.getWidth(), 100));
        taskNameMenu.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(steuerung.getUiColor(UIColor.border)), "Titel", TitledBorder.CENTER, TitledBorder.TOP));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel wrapperJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        wrapperJPanel.setOpaque(false);

        titelName.setPreferredSize(new Dimension(300, 35));
        titelName.setBackground(steuerung.getUiColor(UIColor.bgLight));
        titelName.setForeground(steuerung.getUiColor(UIColor.text));
        titelName.setFont(new Font("ARIAL", Font.PLAIN, 14));

        wrapperJPanel.setMaximumSize(titelName.getPreferredSize());
        wrapperJPanel.add(titelName);

        taskNameMenu.add(wrapperJPanel, gbc);

        return taskNameMenu;
    }

    private JPanel createBemerkungPanel() {
        JPanel bemerkungMenu = new JPanel();

        bemerkungMenu.setLayout(new GridBagLayout());
        bemerkungMenu.setBackground(steuerung.getUiColor(UIColor.bg));
        bemerkungMenu.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(steuerung.getUiColor(UIColor.border)), "Bemerkung", TitledBorder.CENTER, TitledBorder.TOP));
        
        beschreibungsArea.setFont(new Font("ARIAL", Font.PLAIN, 14));
        beschreibungsArea.setLineWrap(true);
        beschreibungsArea.setWrapStyleWord(true);
        beschreibungsArea.setBackground(steuerung.getUiColor(UIColor.bgLight));
        beschreibungsArea.setForeground(steuerung.getUiColor(UIColor.text));

        JScrollPane scrollPane = new JScrollPane(beschreibungsArea);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
        scrollPane.getVerticalScrollBar().setBackground(steuerung.getUiColor(UIColor.bgLight));

        bemerkungMenu.add(scrollPane);

        return bemerkungMenu;
    }

    private JPanel createConfirmPanelMenu() {
        JPanel confirmPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        confirmPanel.setPreferredSize(new Dimension(this.getWidth(), 50));
        confirmPanel.setBackground(steuerung.getUiColor(UIColor.bgDark));

        confirmPanel.add(createConfirmButtons("Abbrechen"));
        confirmPanel.add(createConfirmButtons("Bestätigt"));

        return confirmPanel;
    }

    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(steuerung.getUiColor(UIColor.bgDark));
        sidePanel.setPreferredSize(new Dimension(20, this.getHeight()));
        return sidePanel;
    }

    private JPanel createConfirmButtons(String name) {
        JPanel wrapperButton = new JPanel(new BorderLayout());

        JButton button = new JButton(name);
        button.setName(name);
        button.addActionListener(e -> confirmButtonsListener(button));
        if (name.equals("Bestätigt")) {
            button.setBackground(steuerung.getUiColor(UIColor.primary));
        } else {
            button.setBackground(steuerung.getUiColor(UIColor.info));
        }
        button.setForeground(steuerung.getUiColor(UIColor.highlight));

        wrapperButton.setPreferredSize(new Dimension(100, 30));
        wrapperButton.add(button, BorderLayout.CENTER);
        return wrapperButton;
    }

    private void confirmButtonsListener(JButton button) {
        if (button.getName().equals("Bestätigt")) {

            taskSegment.setTitel(titelName.getText());
            taskSegment.setBeschreibung(beschreibungsArea.getText());

            taskSegment.createTaskSegment(this, isEdited);

        } else if (button.getName().equals("Abbrechen")) {
            //Wenn das Button betätigt wird, dann schließt sich das Fenster

            this.dispose();
        }
    }

    public String getTaskTitle() {
        return titelName.getText();
    }
}

    
