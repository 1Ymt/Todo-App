package TodoItem;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

import Interface.TodoItemTask;
import Steuerung.Steuerung;
import UI.AppFrame;

public class Ordner extends TodoItem implements TodoItemTask{

    private Ordner ordner;
    private Steuerung steuerung;
    private AppFrame appFrame;
    
    private Color farbe;
    private ArrayList<TodoItem> todoItems;
    private JPanel menuList;

    public Ordner(Steuerung steuerung, TodoItemTask parent, AppFrame appFrame, String name, Color farbe) {
        super(name, "Ordner", parent);
        this.steuerung = steuerung;
        this.appFrame = appFrame;
        this.ordner = this;
        this.farbe = farbe;

        this.todoItems = new ArrayList<>();

        this.menuList = new JPanel();
    }
    @Override
    public TodoItem[] getTodoItems() {
        return todoItems.toArray(new TodoItem[0]);
    }

    @Override
    public JPanel getMenuList() {
        return menuList;
    }

    @Override
    public void addTodoItem(TodoItem todoItem) {
        todoItems.add(todoItem);
        updateMenuList();
    }

    @Override
    public void updateMenuList() {
        menuList.removeAll();
        for (TodoItem todoItem : todoItems) {
            menuList.add(todoItem.display());
            menuList.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        
        menuList.revalidate();
        menuList.repaint();
    }

    public Color getFarbe() {
        return farbe;
    }

    public void setFarbe(Color farbe) {
        this.farbe = farbe;
    }

    public JPanel ordnerMenuListPanel() {
        JPanel backgroundListPanel = new JPanel();
        backgroundListPanel.setLayout(new BorderLayout());
        
        backgroundListPanel.add(createMenuListPanel(), BorderLayout.CENTER);
        backgroundListPanel.add(createTopMenuPanel(), BorderLayout.NORTH);
        backgroundListPanel.add(createSidePanel(), BorderLayout.WEST);
        backgroundListPanel.add(createSidePanel(), BorderLayout.EAST);

        return backgroundListPanel;
    }

    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(Color.WHITE);
        sidePanel.setPreferredSize(new Dimension(50, appFrame.getSize().height));
        return sidePanel;
    }

    private JPanel createMenuListPanel() {
        menuList.setName("MenuList");
        menuList.setBackground(new Color(110, 128, 210));
        menuList.setLayout(new BoxLayout(menuList, BoxLayout.Y_AXIS));
        return menuList;
    }

    private JPanel createTopMenuPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(appFrame.getSize().width, 90));
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        
        topPanel.add(createMenuName());
        topPanel.add(createTodoItemButtonPanel());

        return topPanel;
    }

    private JPanel createMenuName() {
        JPanel menuNamePanel = new JPanel(new BorderLayout());
        menuNamePanel.setPreferredSize(new Dimension(appFrame.getSize().width, 45));

        JLabel menuName = new JLabel(this.getName());
        menuName.setFont(new Font("ARIAL_BOLD", Font.BOLD, 20));

        JPanel wrapperLabel = new JPanel();
        wrapperLabel.setPreferredSize(menuName.getPreferredSize());
        wrapperLabel.setBackground(Color.WHITE);
        wrapperLabel.add(menuName);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(30,30));
        backButton.addActionListener(e -> steuerung.previousMenuPanel(ordnerMenuListPanel()));

        JPanel wrapperButton = new JPanel(new GridBagLayout());
        wrapperButton.setPreferredSize(new Dimension(45, 45));
        wrapperButton.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        wrapperButton.add(backButton, gbc);

        JPanel balancePanel = new JPanel();
        balancePanel.setPreferredSize(wrapperButton.getPreferredSize());
        balancePanel.setBackground(Color.WHITE);

        menuNamePanel.add(wrapperButton, BorderLayout.WEST);
        menuNamePanel.add(balancePanel, BorderLayout.EAST);
        menuNamePanel.add(wrapperLabel, BorderLayout.CENTER);
        
        return menuNamePanel;
    }

    private JPanel createTodoItemButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setPreferredSize(new Dimension(appFrame.getSize().width, 45));

        JButton todoItemButton = new JButton("Neu");
        todoItemButton.addActionListener(e -> steuerung.createNeueTodoItemMainMenu(this));

        JPanel wrapperButton = new JPanel(new BorderLayout());
        wrapperButton.setPreferredSize(new Dimension(200, 40));
        wrapperButton.add(todoItemButton, BorderLayout.CENTER);

        buttonPanel.add(wrapperButton);

        return buttonPanel;
    }

    /*
     * Hier wird die Ordner an der MenuListe angeheftet. 
     * Es wird auch die Ordner an der jeweiligen Ordner Klasse verbunden, um es zu öffnen.
     */
    @Override
    public JPanel display() {
        JPanel orderPanel = new JPanel();
        orderPanel.setName(this.getName());
        orderPanel.setLayout(new FlowLayout());
        orderPanel.setMaximumSize(new Dimension(500, 60));
        orderPanel.setBackground(new Color(95, 111, 181));
        orderPanel.addMouseListener(mouseClicked());
        orderPanel.setToolTipText(this.getName());
        JPanel ordnerZeichen = new JPanel(new BorderLayout());
        ordnerZeichen.setPreferredSize(new Dimension(45,45));
        ordnerZeichen.setOpaque(false);

        JLabel ordnerIcon = new JLabel(steuerung.setOrdnerIcon(farbe, 45));

        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setOpaque(false);

        JLabel ordnerName = new JLabel(this.getName());
        ordnerName.setFont(new Font("ARIAL_BOLD", Font.BOLD, 15));
        ordnerName.setPreferredSize(new Dimension(200,40));

        ordnerZeichen.add(ordnerIcon, BorderLayout.CENTER);

        wrapperPanel.add(ordnerName);

        orderPanel.add(ordnerZeichen);
        orderPanel.add(wrapperPanel);

        return orderPanel;
    }

    private MouseListener mouseClicked() {
        MouseListener ml = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1) {
                    steuerung.nextMenuPanel(ordnerMenuListPanel());

                }else if(e.getButton() == MouseEvent.BUTTON3) {
                    steuerung.changeOrdnerProperties(ordner);
                }
            }
        };
        return ml;
    }
}
