package TodoItem;
import java.awt.*;
import javax.swing.*;

import Enums.UIColor;
import Steuerung.Steuerung;
import UI.AppFrame;

/*
 * ---WICHTIG---
 * Eine TodoData Klasse erstellen, um Informationen für den Json File zu speichern.
 * Mache auch so, dass man genau sieht, ob eine Ordner oder eine Task anfängt, mache das am besten mit einem Array so dass es so im Json aussieht
 * 
 * {
 *  Ordner "NAME": [
 *      "name": "Hallo",
        "type": "Ordner",
        "number": "1",
        "colorRGB": -16777216,
        "todoItem": []
        }
 *  ]
 * }
 * 
 * So it die TodoData Klasse:
 * 
 * public class TodoData {
        String name;
        String type;
        String number;
        int colorRGB;
        List<TodoData> todoItem;

        public TodoData(String name, String type, String number, int colorRGB) {
            this.name = name;
            this.type = type;
            this.number = number;
            this.colorRGB = colorRGB;
            this.todoItem = new ArrayList<>();
        }
    }
 */

public class OrdnerFrame extends TodoFrame{

    private Steuerung steuerung;
    private OrdnerSteuerung ordnerSteuerung;
    private AppFrame appFrame;
    
    private Color ordnerFarbe;
    private JPanel menuList;

    public OrdnerFrame(Steuerung steuerung, OrdnerSteuerung ordnerSteuerung, AppFrame appFrame, Color farbe) {
        this.steuerung = steuerung;
        this.ordnerSteuerung = ordnerSteuerung;
        this.appFrame = appFrame;

        this.ordnerFarbe = farbe;

        this.menuList = new JPanel();
    }
    
    /*
     * Hier wird die Ordner an der MenuListe angeheftet. 
     * Es wird auch die Ordner an der jeweiligen Ordner Klasse verbunden, um es zu öffnen.
     */
    @Override
    public JPanel getDisplayPanel() {
        if (ordnerSteuerung.getParent() == null) {
            return null;
        }

        JPanel orderPanel = new JPanel();
        orderPanel.setLayout(new FlowLayout());
        
        orderPanel.setPreferredSize(new Dimension(ordnerSteuerung.getParent().getMenuList().getWidth(), 60));
        orderPanel.setMaximumSize(new Dimension(2000, 60));
        orderPanel.setBackground(steuerung.getUiColor(UIColor.bgLight));
        orderPanel.addMouseListener(ordnerSteuerung.mouseClicked());
        orderPanel.setToolTipText(ordnerSteuerung.getName());
        orderPanel.setBorder(BorderFactory.createRaisedBevelBorder());

        JPanel ordnerZeichen = new JPanel(new BorderLayout());
        ordnerZeichen.setPreferredSize(new Dimension(45,45));
        ordnerZeichen.setOpaque(false);

        JLabel ordnerIcon = new JLabel(ordnerSteuerung.getIcon(ordnerFarbe, 45));

        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setOpaque(false);

        JLabel ordnerName = new JLabel(ordnerSteuerung.getName());
        ordnerName.setFont(new Font("ARIAL_BOLD", Font.BOLD, 15));
        ordnerName.setPreferredSize(new Dimension(200, 40));
        ordnerName.setBackground(steuerung.getUiColor(UIColor.text));

        ordnerZeichen.add(ordnerIcon, BorderLayout.CENTER);

        wrapperPanel.add(ordnerName);

        orderPanel.add(ordnerZeichen);
        orderPanel.add(wrapperPanel);

        return orderPanel;
    }

    /*
     * Panels 
     */
    @Override
    public JPanel menuPanel() {
        JPanel backgroundListPanel = new JPanel();
        backgroundListPanel.setLayout(new BorderLayout());

        backgroundListPanel.add(createCenterPanel(), BorderLayout.CENTER);
        backgroundListPanel.add(createTopMenuPanel(), BorderLayout.NORTH);
        backgroundListPanel.add(createSidePanel(), BorderLayout.WEST);
        backgroundListPanel.add(createSidePanel(), BorderLayout.EAST);

        return backgroundListPanel;
    }

    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(steuerung.getUiColor(UIColor.bgDark));
        sidePanel.setPreferredSize(new Dimension(50, appFrame.getSize().height));
        return sidePanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(steuerung.getUiColor(UIColor.bgDark));

        centerPanel.add(createFolderPathPanel(), BorderLayout.NORTH);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(createMenuListPanel(), BorderLayout.CENTER);

        return centerPanel;
    }

    private JPanel createFolderPathPanel() {
        JPanel pathPanel = new JPanel();
        pathPanel.setLayout(new BorderLayout());
        pathPanel.setBackground(steuerung.getUiColor(UIColor.bgLight));
        pathPanel.setPreferredSize(new Dimension(appFrame.getWidth(), 25));
        pathPanel.setMaximumSize(new Dimension(2000, 25));
        pathPanel.setBorder(BorderFactory.createRaisedBevelBorder());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel path = new JLabel(ordnerSteuerung.getAllOrdnerPath());
        path.setForeground(steuerung.getUiColor(UIColor.textMuted));
        path.setFont(new Font("Arial", Font.BOLD, 13));
        path.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); //padding 

        JPanel wrapperLabel = new JPanel();
        wrapperLabel.setOpaque(true);
        wrapperLabel.add(path);

        pathPanel.add(path, BorderLayout.WEST);
        return pathPanel;
    }

    private JScrollPane createMenuListPanel() {
        menuList.setName("MenuList");
        menuList.setBackground(steuerung.getUiColor(UIColor.bg));
        menuList.setLayout(new BoxLayout(menuList, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(menuList);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
        scrollPane.getVerticalScrollBar().setBackground(steuerung.getUiColor(UIColor.bgLight));

        return scrollPane;
    }

    private JPanel createTopMenuPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(appFrame.getSize().width, 90));
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        
        topPanel.add(createMenuNamePanel());
        topPanel.add(createTodoItemButtonPanel());

        return topPanel;
    }

    private JPanel createMenuNamePanel() {
        JPanel menuNamePanel = new JPanel(new BorderLayout());
        menuNamePanel.setPreferredSize(new Dimension(appFrame.getSize().width, 45));

        JLabel menuName = new JLabel(ordnerSteuerung.getName());
        menuName.setFont(new Font("ARIAL_BOLD", Font.BOLD, 20));
        menuName.setBackground(steuerung.getUiColor(UIColor.text));

        JPanel wrapperLabel = new JPanel();
        wrapperLabel.add(menuName);
        wrapperLabel.setBackground(steuerung.getUiColor(UIColor.bgDark));

        if (ordnerSteuerung.getParent() != null) {
            JButton backButton = new JButton("Back");
            backButton.setPreferredSize(new Dimension(30, 30));
            backButton.addActionListener(e -> ordnerSteuerung.previousPanel(menuPanel()));
            backButton.setBackground(steuerung.getUiColor(UIColor.primary));

            JPanel wrapperButton = new JPanel(new GridBagLayout());
            wrapperButton.setPreferredSize(new Dimension(45, 45));
            wrapperButton.setBackground(steuerung.getUiColor(UIColor.bgDark));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.CENTER;
            wrapperButton.add(backButton, gbc);

            JPanel balancePanel = new JPanel();
            balancePanel.setPreferredSize(wrapperButton.getPreferredSize());
            balancePanel.setBackground(wrapperButton.getBackground());

            menuNamePanel.add(wrapperButton, BorderLayout.WEST);
            menuNamePanel.add(balancePanel, BorderLayout.EAST);
        }
        
        menuNamePanel.add(wrapperLabel, BorderLayout.CENTER);
        
        return menuNamePanel;
    }

    private JPanel createTodoItemButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonPanel.setBackground(steuerung.getUiColor(UIColor.bgDark));
        buttonPanel.setPreferredSize(new Dimension(appFrame.getSize().width, 45));

        JButton todoItemButton = new JButton("+");
        todoItemButton.addActionListener(e -> ordnerSteuerung.createNewTodoItem());
        todoItemButton.setBackground(steuerung.getUiColor(UIColor.primary));
        todoItemButton.setForeground(steuerung.getUiColor(UIColor.highlight));
        todoItemButton.setFont(new Font("ARIAL_BOLD", Font.BOLD, 25));

        JPanel wrapperButton = new JPanel(new BorderLayout());
        wrapperButton.setPreferredSize(new Dimension(200, 40));
        wrapperButton.add(todoItemButton, BorderLayout.CENTER);

        buttonPanel.add(wrapperButton);

        return buttonPanel;
    }

    public void setOrdnerFarbe(Color farbe) {
        ordnerFarbe = farbe;
    }

    public JPanel getMenuList() {
        return menuList;
    }
}
