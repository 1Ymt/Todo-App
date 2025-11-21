package TodoItem;

import java.awt.*;

import javax.swing.*;
import javax.swing.text.EditorKit;
import javax.swing.text.StyledDocument;

import UI.AppFrame;

public class NotizenFrame {

    private NotizenSteuerung notizenSteuerung;
    private AppFrame appFrame;

    private String[] textFormattingNames;

    private JTextPane textPane;
    private JToggleButton[] textFormattingButtons;
    private JComboBox<String> fonts;
    private JComboBox<Integer> fontSizes;

    public NotizenFrame(NotizenSteuerung notizenSteuerung, AppFrame appFrame) {
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
        notizenPanel.setMaximumSize(new Dimension(500, 60));
        notizenPanel.setBackground(new Color(95, 111, 181));
        notizenPanel.addMouseListener(notizenSteuerung.mouseClicked());
        notizenPanel.setToolTipText(notizenSteuerung.getName());
        JPanel ordnerZeichen = new JPanel(new BorderLayout());
        ordnerZeichen.setPreferredSize(new Dimension(45, 45));
        ordnerZeichen.setOpaque(false);

        JLabel notizenIcon = new JLabel("Notizen");

        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setOpaque(false);

        JLabel notizenName = new JLabel(notizenSteuerung.getName());
        notizenName.setFont(new Font("ARIAL_BOLD", Font.BOLD, 15));
        notizenName.setPreferredSize(new Dimension(200, 40));

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

        //topPanel.add(createMenuNamePanel());
        topPanel.add(createToolbarMenuPanel());

        return topPanel;
    }
    
    private JPanel createToolbarMenuPanel() {
        JPanel toolbarMenu = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        toolbarMenu.setBackground(Color.black);
        
        
        fonts.setPreferredSize(new Dimension(250, 30));
        fonts.setFont(new Font("Arial", Font.PLAIN, 15));
        fonts.addActionListener(e -> notizenSteuerung.updateFont(fonts));

        
        fontSizes.setPreferredSize(new Dimension(50, 30));
        fontSizes.setEditable(true);
        fontSizes.addActionListener(e -> notizenSteuerung.updateFontSize(fontSizes));
        
        toolbarMenu.add(fonts);
        toolbarMenu.add(fontSizes);
        for (int i = 0; i < textFormattingNames.length; i++) {
            toolbarMenu.add(createTextFormattingButton(textFormattingNames[i], i));
        }
        return toolbarMenu;
    }

    private JPanel createTextFormattingButton(String name, int i) {
        //WrapperPanel für den Button erzeugt um ein besseres Managment für den Layout zu haben
        JPanel wrapperButton = new JPanel(); 
        wrapperButton.setOpaque(false);
        //wrapperButton.setBackground(Color.BLACK);

        textFormattingButtons[i] = new JToggleButton(name);
        textFormattingButtons[i].setName(name);
        textFormattingButtons[i].setPreferredSize(new Dimension(35,30));
        textFormattingButtons[i].setBackground(Color.WHITE);
        textFormattingButtons[i].addItemListener(e -> notizenSteuerung.textFormattingButtonsSteuerung(textFormattingButtons, textFormattingButtons[i], textPane));

        wrapperButton.setMaximumSize(textFormattingButtons[i].getPreferredSize());
        wrapperButton.add(textFormattingButtons[i]);

        return wrapperButton;
    }

    private JPanel createMenuListPanel() {
        JPanel textFieldPanel = new JPanel();
        textFieldPanel.setBackground(Color.GRAY);

        textPane.setPreferredSize(new Dimension(800, 300));
        textPane.setBackground(new Color(230, 230, 230));
        textPane.addCaretListener(e -> notizenSteuerung.carretListener(textPane, textFormattingButtons));

        textFieldPanel.add(textPane);
        return textFieldPanel;
    }
    

    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(Color.WHITE);
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

