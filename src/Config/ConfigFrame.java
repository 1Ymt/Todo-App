package Config;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import Enums.Theme;
import Enums.UIColor;
import Steuerung.Steuerung;
import UI.AppFrame;

public class ConfigFrame extends JPanel {

    private Steuerung steuerung;
    private ConfigSteuerung configSteuerung;
    private AppFrame appFrame;

    private JPanel mainPanel;

    public ConfigFrame(Steuerung steuerung, ConfigSteuerung configSteuerung, AppFrame appFrame) {
        this.steuerung = steuerung;
        this.configSteuerung = configSteuerung;
        this.appFrame = appFrame;
        initializeFrame();
        createPanels();
    }

    private void initializeFrame() {
        setLayout(new BorderLayout());
    }

    private void createPanels() {
        add(createTopPanel(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
        add(createSidePanel(), BorderLayout.EAST);
        add(createSidePanel(), BorderLayout.WEST);

        //TODO: Create all panels in UiColors
        createUIContent();
        
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(steuerung.getUiColor(UIColor.bgDark));
        topPanel.setPreferredSize(new Dimension(800, 50));

        return topPanel;
    }

    private JScrollPane createCenterPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(steuerung.getUiColor(UIColor.bgDark));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
        scrollPane.getVerticalScrollBar().setBackground(steuerung.getUiColor(UIColor.bgLight));
        return scrollPane;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBackground(steuerung.getUiColor(UIColor.bgDark));
        bottomPanel.setPreferredSize(new Dimension(800, 50));
        
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        wrapperPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;

        wrapperPanel.add(createConfirmButtons("Abbrechen"));
        wrapperPanel.add(createConfirmButtons("Bestätigt"));

        bottomPanel.add(wrapperPanel, BorderLayout.EAST);

        return bottomPanel;
    }

    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(steuerung.getUiColor(UIColor.bgDark));
        sidePanel.setPreferredSize(new Dimension(70, 600));
        return sidePanel;
    }

    private JPanel createConfirmButtons(String name) {
        JPanel wrapperButton = new JPanel();
        wrapperButton.setOpaque(false);

        JButton button = new JButton(name);
        button.setName(name);
        button.setPreferredSize(new Dimension(130, 30));
        button.addActionListener(e -> configSteuerung.confirmButtonsListener(button));
        if (name.equals("Bestätigt")) {
            button.setBackground(steuerung.getUiColor(UIColor.primary));
        } else {
            button.setBackground(steuerung.getUiColor(UIColor.info));
        }
        button.setForeground(steuerung.getUiColor(UIColor.highlight));
        button.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, steuerung.getUiColor(UIColor.borderMuted), steuerung.getUiColor(UIColor.border)));

        wrapperButton.setMaximumSize(button.getPreferredSize());
        wrapperButton.add(button);
        return wrapperButton;
    }

    //All contents
    private void createUIContent() {
        JPanel heading = createContentHeading("UI Colors", UIColor.bg, UIColor.bg, UIColor.text);

        heading.add(createSeparator());
        heading.add(createUIThemePanel("Theme", "Set your current Theme across all Platforms", UIColor.bg, UIColor.textMuted));
        heading.add(createSeparator());
        heading.add(createUIColorPanel("Background-Dark", "", UIColor.bg, UIColor.textMuted, UIColor.bgDark));
        heading.add(createSeparator());
        heading.add(createUIColorPanel("Background", "", UIColor.bg, UIColor.textMuted, UIColor.bg));
        heading.add(createSeparator());
        heading.add(createUIColorPanel("Background-Light", "", UIColor.bg, UIColor.textMuted, UIColor.bgLight));
        heading.add(createSeparator());
        heading.add(createUIColorPanel("Text", "", UIColor.bg, UIColor.textMuted, UIColor.text));
        heading.add(createSeparator());
        heading.add(createUIColorPanel("Text-Muted", "", UIColor.bg, UIColor.textMuted, UIColor.textMuted));
        heading.add(createSeparator());
        heading.add(createUIColorPanel("Highlight", "", UIColor.bg, UIColor.textMuted, UIColor.highlight));
        heading.add(createSeparator());
        heading.add(createUIColorPanel("Border", "", UIColor.bg, UIColor.textMuted, UIColor.border));
        heading.add(createSeparator());
        heading.add(createUIColorPanel("Border-Muted", "", UIColor.bg, UIColor.textMuted, UIColor.borderMuted));
        heading.add(createSeparator());
        heading.add(createUIColorPanel("Primary", "", UIColor.bg, UIColor.textMuted, UIColor.primary));
        heading.add(createSeparator());
        heading.add(createUIColorPanel("Secondary", "", UIColor.bg, UIColor.textMuted, UIColor.secondary));
        heading.add(createSeparator());
        heading.add(createUIColorPanel("Danger", "", UIColor.bg, UIColor.textMuted, UIColor.danger));
        heading.add(createSeparator());
        heading.add(createUIColorPanel("Warning", "", UIColor.bg, UIColor.textMuted, UIColor.warning));
        heading.add(createSeparator());
        heading.add(createUIColorPanel("Success", "", UIColor.bg, UIColor.textMuted, UIColor.success));
        heading.add(createSeparator());
        heading.add(createUIColorPanel("Info", "Text Example", UIColor.bg, UIColor.textMuted, UIColor.info));
    }
    
    //Ui Content
    private JPanel createUIThemePanel(String name, String description, UIColor panelColor, UIColor normalTextColor) {
        JPanel sectionPanel = createSectionPanel(name, description, panelColor, normalTextColor);

        JComboBox<Theme> themePanel = new JComboBox<>(Theme.values());
        themePanel.setBackground(steuerung.getUiColor(panelColor));
        themePanel.setForeground(steuerung.getUiColor(normalTextColor));
        themePanel.setSelectedItem(configSteuerung.getColorData().getTheme());
        themePanel.addActionListener(e-> configSteuerung.changeTheme(themePanel));

        JPanel wrapperButton = new JPanel(new GridBagLayout());
        wrapperButton.setPreferredSize(new Dimension(100, 45));
        wrapperButton.setBackground(steuerung.getUiColor(panelColor));
        wrapperButton.add(themePanel);

        sectionPanel.add(wrapperButton, BorderLayout.EAST);

        return sectionPanel;
    }
    
    private JPanel createUIColorPanel(String name, String description, UIColor panelColor, UIColor normalTextColor, UIColor uiColor) {
        JPanel sectionPanel = createSectionPanel(name, description, panelColor, normalTextColor);

        JButton colorButton = new JButton();
        colorButton.setName(uiColor.name());
        colorButton.setPreferredSize(new Dimension(30, 30));
        colorButton.setBackground(steuerung.getUiColor(uiColor));
        colorButton.setBorder(BorderFactory.createMatteBorder(1, 1, 2, 2, steuerung.getUiColor(UIColor.border)));
        colorButton.addActionListener(e -> configSteuerung.changeUIColor(colorButton, name));

        JPanel wrapperButton = new JPanel(new GridBagLayout());
        wrapperButton.setPreferredSize(new Dimension(45, 45));
        wrapperButton.setBackground(steuerung.getUiColor(panelColor));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        wrapperButton.add(colorButton, gbc);

        sectionPanel.add(wrapperButton, BorderLayout.EAST);

        return sectionPanel;
    }

    //Helper Class fürs erstellen von Swing Components
    private JLabel createHeading(String name, UIColor bgColor, UIColor textColor, Font font) {
        JLabel headingName = new JLabel(name);
        headingName.setBackground(steuerung.getUiColor(bgColor));
        headingName.setForeground(steuerung.getUiColor(textColor));
        headingName.setFont(font);

        return headingName;
    }

    private JPanel createContentHeading(String headingName, UIColor sectionColor, UIColor panelColor,UIColor headingTextColor) {
        //The main panel for all the content
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));
        sectionPanel.setBackground(steuerung.getUiColor(sectionColor));

        //Panel for the head
        JPanel headingPanel = new JPanel();
        headingPanel.setLayout(new BorderLayout());
        headingPanel.setBackground(steuerung.getUiColor(panelColor));
        headingPanel.setPreferredSize(new Dimension(mainPanel.getWidth(), 60));
        headingPanel.setMaximumSize(new Dimension(2000, 60));

        //Name of the content
        JLabel label = createHeading(headingName, panelColor, headingTextColor, new Font("Arial", Font.BOLD, 17));
        label.setBackground(Color.black);
        label.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // left padding 
        
        //Default Button
        JButton defaultButton = new JButton("Default");
        defaultButton.setPreferredSize(new Dimension(60, 30));
        defaultButton.setBackground(steuerung.getUiColor(UIColor.secondary));
        defaultButton.setBorder(BorderFactory.createMatteBorder(1, 1, 2, 2, steuerung.getUiColor(UIColor.border)));
        defaultButton.setForeground(steuerung.getUiColor(UIColor.highlight));
        defaultButton.addActionListener(e -> configSteuerung.resetUIColor());

        JPanel wrapperButton = new JPanel(new GridBagLayout());
        wrapperButton.setMaximumSize(defaultButton.getPreferredSize());
        wrapperButton.setBackground(steuerung.getUiColor(panelColor));
        wrapperButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 15)); // right padding 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        wrapperButton.add(defaultButton, gbc);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(steuerung.getUiColor(panelColor));
        buttonPanel.add(wrapperButton, BorderLayout.CENTER);
        
        headingPanel.add(buttonPanel, BorderLayout.EAST);
        headingPanel.add(label, BorderLayout.WEST);


        sectionPanel.add(headingPanel);

        //Add this content to centerPanel
        mainPanel.add(sectionPanel);
        return sectionPanel;
    }
    
    private JPanel createSectionPanel(String name, String description, UIColor panelColor, UIColor normalTextColor) {
        //Main panel for the invidual section
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BorderLayout());
        sectionPanel.setBackground(steuerung.getUiColor(panelColor));
        sectionPanel.setPreferredSize(new Dimension(mainPanel.getWidth(), 60));
        sectionPanel.setMaximumSize(new Dimension(2000, 60));

        //The name of the section
        JLabel label = createHeading(name, panelColor, normalTextColor, new Font("Arial", Font.BOLD, 15));
        label.setBackground(Color.black);
        
        if (description.equals("")) {
            label.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // left padding 
            sectionPanel.add(label, BorderLayout.WEST);

        } else {
            label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // left padding 

            //Create a new text panel to fit the name and description
            JPanel textPanel = new JPanel();
            textPanel.setOpaque(false);
            sectionPanel.add(textPanel, BorderLayout.WEST);

            JPanel wrapperLabel = new JPanel();
            wrapperLabel.setLayout(new BoxLayout(wrapperLabel, BoxLayout.Y_AXIS));
            wrapperLabel.setOpaque(false);
            wrapperLabel.add(label);

            //The description of the panel
            JLabel descriptionLabel = createHeading(description, panelColor, normalTextColor, new Font("Arial", Font.PLAIN, 13));
            descriptionLabel.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10)); // left padding
            wrapperLabel.add(descriptionLabel);

            textPanel.add(wrapperLabel);
        }
        return sectionPanel;
    }
    
    private JSeparator createSeparator() {
        JSeparator s = new JSeparator();
        s.setOrientation(SwingConstants.HORIZONTAL);
        s.setMaximumSize(new Dimension(800, 5));
        s.setBackground(steuerung.getUiColor(UIColor.border));
        return s;
    }
}
