package TodoItem;

import java.awt.*;

import javax.swing.*;
import javax.swing.text.EditorKit;
import javax.swing.text.StyledDocument;

import Enums.UiColor;
import Steuerung.Steuerung;
import UI.AppFrame;

public class NotizenFrame {

    private Steuerung steuerung;
    private NotizenSteuerung notizenSteuerung;
    private AppFrame appFrame;

    private String[] textFormattingNames;

    private JTextPane textPane;
    private JToggleButton[] textFormattingButtons;
    private JComboBox<String> fonts;
    private JComboBox<Integer> fontSizes;

    public NotizenFrame(Steuerung steuerung, NotizenSteuerung notizenSteuerung, AppFrame appFrame) {
        this.steuerung = steuerung;
        this.appFrame = appFrame;
        this.notizenSteuerung = notizenSteuerung;

        this.textFormattingNames = notizenSteuerung.getTextFormattingNames();

        this.textPane = new JTextPane();
        this.textFormattingButtons = new JToggleButton[notizenSteuerung.getTextFormattingNames().length];
        this.fonts = new JComboBox<String>(notizenSteuerung.getFontNames());
        this.fontSizes = new JComboBox<Integer>(notizenSteuerung.getFontSizes());
    }

    public JPanel getDisplayPanel() {
        JPanel notizenPanel = new JPanel();
        notizenPanel.setLayout(new FlowLayout());
        notizenPanel.setMaximumSize(new Dimension(appFrame.getWidth(), 60));
        notizenPanel.setBackground(steuerung.getUiColor(UiColor.bgLight));
        notizenPanel.addMouseListener(notizenSteuerung.mouseClicked());
        notizenPanel.setToolTipText(notizenSteuerung.getName());
        notizenPanel.setBorder(BorderFactory.createRaisedBevelBorder());

        JPanel ordnerZeichen = new JPanel(new BorderLayout());
        ordnerZeichen.setPreferredSize(new Dimension(45, 45));
        ordnerZeichen.setOpaque(false);

        JLabel notizenIcon = new JLabel("Notizen");

        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setOpaque(false);

        JLabel notizenName = new JLabel(notizenSteuerung.getName());
        notizenName.setFont(new Font("ARIAL_BOLD", Font.BOLD, 15));
        notizenName.setPreferredSize(new Dimension(200, 40));
        notizenName.setBackground(steuerung.getUiColor(UiColor.text));

        ordnerZeichen.add(notizenIcon, BorderLayout.CENTER);

        wrapperPanel.add(notizenName);

        notizenPanel.add(ordnerZeichen);
        notizenPanel.add(wrapperPanel);

        return notizenPanel;
    }
    
    public JPanel notizMenuPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        
        mainPanel.add(createTopMenuPanel(), BorderLayout.NORTH);
        mainPanel.add(createSidePanel(), BorderLayout.WEST);
        mainPanel.add(createSidePanel(), BorderLayout.EAST);
        mainPanel.add(createMenuListPanel(), BorderLayout.CENTER);
        
        return mainPanel;
    }

    private JPanel createTopMenuPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(appFrame.getSize().width, 90));
        topPanel.setBackground(steuerung.getUiColor(UiColor.bgDark));

        topPanel.add(createToolbarMenuPanel());

        return topPanel;
    }
    
    private JPanel createToolbarMenuPanel() {
        JPanel toolbarMenu = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        toolbarMenu.setBackground(steuerung.getUiColor(UiColor.bgDark));
        
        
        fonts.setPreferredSize(new Dimension(250, 30));
        fonts.setFont(new Font("Arial", Font.PLAIN, 15));
        fonts.addActionListener(e -> notizenSteuerung.updateFont(fonts));
        fonts.setBackground(steuerung.getUiColor(UiColor.highlight));
        fonts.setForeground(steuerung.getUiColor(UiColor.text));

        
        fontSizes.setPreferredSize(new Dimension(50, 30));
        fontSizes.setEditable(true);
        fontSizes.addActionListener(e -> notizenSteuerung.updateFontSize(fontSizes));
        fontSizes.setBackground(steuerung.getUiColor(UiColor.highlight));
        
        toolbarMenu.add(fonts);
        toolbarMenu.add(fontSizes);
        toolbarMenu.add(Box.createRigidArea(new Dimension(10, 0)));

        JPanel formattingButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        formattingButtonPanel.setOpaque(false);

        for (int i = 0; i < textFormattingNames.length; i++) {
            createTextFormattingButton(formattingButtonPanel, textFormattingNames[i], i);
        }
        toolbarMenu.add(formattingButtonPanel);
        return toolbarMenu;
    }

    private void createTextFormattingButton(JPanel formattingButtonPanel, String name, int i) {
        textFormattingButtons[i] = new JToggleButton(name);
        textFormattingButtons[i].setName(name);
        textFormattingButtons[i].setPreferredSize(new Dimension(35,30));
        textFormattingButtons[i].setBackground(steuerung.getUiColor(UiColor.highlight));
        textFormattingButtons[i].addItemListener(e -> notizenSteuerung.textFormattingButtonsSteuerung(textFormattingButtons, textFormattingButtons[i], textPane));

        formattingButtonPanel.add(textFormattingButtons[i]);
    }

    private JScrollPane createMenuListPanel() {
        textPane.setBackground(steuerung.getUiColor(UiColor.bgLight));
        textPane.addCaretListener(e -> notizenSteuerung.carretListener(textPane, textFormattingButtons));
        textPane.setForeground(steuerung.getUiColor(UiColor.text));

        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setBackground(steuerung.getUiColor(UiColor.bgLight));
        scrollPane.setBorder(null);
        return scrollPane;
    }
    

    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(steuerung.getUiColor(UiColor.bgDark));
        sidePanel.setPreferredSize(new Dimension(50, appFrame.getSize().height));
        return sidePanel;
    }

    public EditorKit getTextPaneEditorKit() {
        return textPane.getEditorKit();
    }
    
    public JComboBox<String> getFonts() {
        return fonts;
    }

    public JComboBox<Integer> getFontSizes() {
        return fontSizes;
    }

    public StyledDocument getStyledDocument() {
        return textPane.getStyledDocument();
    }
}

