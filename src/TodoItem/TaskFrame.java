package TodoItem;
import java.awt.*;
import javax.swing.*;

import Enums.UIColor;
import Steuerung.Steuerung;
import UI.AppFrame;

public class TaskFrame extends TodoFrame {

    private Steuerung steuerung;
    private TaskSteuerung taskSteuerung;
    private AppFrame appFrame;
    
    private JPanel menuList;

    public TaskFrame(Steuerung steuerung, TaskSteuerung taskSteuerung, AppFrame appFrame) {
        this.steuerung = steuerung;
        this.taskSteuerung = taskSteuerung;
        this.appFrame = appFrame;

        this.menuList = new JPanel();
    }
    
    /*
     * Hier wird die Ordner an der MenuListe angeheftet. 
     * Es wird auch die Ordner an der jeweiligen Ordner Klasse verbunden, um es zu öffnen.
     */
    @Override
    public JPanel getDisplayPanel() {
        JPanel taskPanel = new JPanel();
        taskPanel.setLayout(new FlowLayout());
        taskPanel.setMaximumSize(new Dimension(appFrame.getWidth(), 60));
        taskPanel.setBackground(steuerung.getUiColor(UIColor.bgLight));
        taskPanel.addMouseListener(taskSteuerung.mouseClicked());
        taskPanel.setToolTipText(taskSteuerung.getName());
        taskPanel.setBorder(BorderFactory.createRaisedBevelBorder());

        JPanel taskZeichen = new JPanel(new BorderLayout());
        taskZeichen.setPreferredSize(new Dimension(45, 45));
        taskZeichen.setOpaque(false);

        JLabel taskIcon = new JLabel("Task");

        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setOpaque(false);

        JLabel taskName = new JLabel(taskSteuerung.getName());
        taskName.setFont(new Font("ARIAL_BOLD", Font.BOLD, 15));
        taskName.setPreferredSize(new Dimension(200, 40));
        taskName.setBackground(steuerung.getUiColor(UIColor.text));

        taskZeichen.add(taskIcon, BorderLayout.CENTER);

        wrapperPanel.add(taskName);

        taskPanel.add(taskZeichen);
        taskPanel.add(wrapperPanel);

        return taskPanel;
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

        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(createMenuListPanel(), BorderLayout.CENTER);

        return centerPanel;
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

        JLabel menuName = new JLabel(taskSteuerung.getName());
        menuName.setFont(new Font("ARIAL_BOLD", Font.BOLD, 20));
        menuName.setBackground(steuerung.getUiColor(UIColor.text));

        JPanel wrapperLabel = new JPanel();
        wrapperLabel.add(menuName);
        wrapperLabel.setBackground(steuerung.getUiColor(UIColor.bgDark));

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(30, 30));
        backButton.addActionListener(e -> taskSteuerung.previousPanel(menuPanel()));
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
        
        
        menuNamePanel.add(wrapperLabel, BorderLayout.CENTER);
        
        return menuNamePanel;
    }

    private JPanel createTodoItemButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonPanel.setBackground(steuerung.getUiColor(UIColor.bgDark));
        buttonPanel.setPreferredSize(new Dimension(appFrame.getSize().width, 45));

        JButton todoItemButton = new JButton("+");
        todoItemButton.addActionListener(e -> taskSteuerung.openNewTaskSegment());
        todoItemButton.setBackground(steuerung.getUiColor(UIColor.primary));
        todoItemButton.setForeground(steuerung.getUiColor(UIColor.highlight));
        todoItemButton.setFont(new Font("ARIAL_BOLD", Font.BOLD, 25));

        JPanel wrapperButton = new JPanel(new BorderLayout());
        wrapperButton.setPreferredSize(new Dimension(200, 40));
        wrapperButton.add(todoItemButton, BorderLayout.CENTER);

        buttonPanel.add(wrapperButton);

        return buttonPanel;
    }

    public JPanel getMenuList() {
        return menuList;
    }
}
