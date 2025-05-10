package UI;

import javax.swing.*;

import Interface.TodoItemTask;

import java.awt.*;
import java.util.ArrayList;

import Steuerung.Steuerung;
import TodoItem.TodoItem;

public class MainMenuPanel extends JPanel implements TodoItemTask{

    private Steuerung steuerung;
    private AppFrame appFrame;
    private ArrayList<TodoItem> todoItems;
    private JPanel menuList;

    public MainMenuPanel(Steuerung steuerung, AppFrame appFrame) {
        this.steuerung = steuerung;
        this.appFrame = appFrame;
        this.todoItems = new ArrayList<>();

        this.menuList = new JPanel();

        this.setPreferredSize(appFrame.getSize());
        this.setLayout(new BorderLayout());

        this.add(createMainMenuListPanel(), BorderLayout.CENTER);
        this.add(createTopMenuPanel(), BorderLayout.NORTH);
        this.add(createSidePanel(), BorderLayout.WEST);
        this.add(createSidePanel(), BorderLayout.EAST);
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

    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(Color.WHITE);
        sidePanel.setPreferredSize(new Dimension(50, (int) appFrame.getSize().getHeight()));
        return sidePanel;
    }

    private JPanel createMainMenuListPanel() {
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

        JLabel menuName = new JLabel("Dokument");
        menuName.setFont(new Font("ARIAL_BOLD", Font.BOLD, 20));

        JPanel wrapperLabel = new JPanel();
        wrapperLabel.setPreferredSize(menuName.getPreferredSize());
        wrapperLabel.setBackground(Color.WHITE);
        wrapperLabel.add(menuName);

        JPanel leftBalancePanel = new JPanel();
        leftBalancePanel.setPreferredSize(new Dimension(45, 45));
        leftBalancePanel.setBackground(Color.WHITE);

        JPanel rightBalancePanel = new JPanel();
        rightBalancePanel.setPreferredSize(new Dimension(45, 45));
        rightBalancePanel.setBackground(Color.WHITE);

        menuNamePanel.add(leftBalancePanel, BorderLayout.WEST);
        menuNamePanel.add(rightBalancePanel, BorderLayout.EAST);
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
}
