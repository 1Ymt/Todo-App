package UI;

import javax.swing.*;

import Data.TodoData;
import Enums.TodoType;
import Interface.TodoListController;

import java.awt.*;
import java.util.ArrayList;

import Steuerung.Steuerung;
import TodoItem.TodoItem;

public class MainMenu extends JPanel implements TodoListController{

    private Steuerung steuerung;
    private AppFrame appFrame;
    private ArrayList<TodoItem> todoItems;
    private JPanel menuList;
    private JScrollPane scrollPane;

    public MainMenu(Steuerung steuerung, AppFrame appFrame) {
        this.steuerung = steuerung;
        this.appFrame = appFrame;
        this.todoItems = new ArrayList<>();

        this.menuList = new JPanel();

        this.setPreferredSize(appFrame.getSize());
        this.setLayout(new BorderLayout());

        this.scrollPane = new JScrollPane(createMainMenuListPanel(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.getVerticalScrollBar().setBackground(steuerung.getPanelBackgroundColor(0));
        scrollPane.getVerticalScrollBar().setOpaque(true);
        scrollPane.setBorder(null);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(createTopMenuPanel(), BorderLayout.NORTH);
        this.add(createSidePanel(), BorderLayout.WEST);
        this.add(createSidePanel(), BorderLayout.EAST);
    }

    public TodoData toData() {
        TodoData data = new TodoData();
        
        data.setName("Main Menu");
        data.setType(TodoType.MainMenu);
        
        for (TodoItem todoItem : todoItems) {
            data.setTodoData(todoItem.toData());
        }
        return data;
    }


    @Override
    public ArrayList<TodoItem> getTodoItems() {
        return todoItems;
    }

    @Override
    public JPanel getMenuList() {
        return menuList;
    }

    @Override
    public void addTodoItem(TodoItem todoItem) {
        todoItems.addFirst(todoItem);
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
        scrollPane.revalidate();
        scrollPane.repaint();
    }
    
    @Override
    public String getParentName() {
        return getName();
    }

    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(steuerung.getPanelBackgroundColor(0));
        sidePanel.setPreferredSize(new Dimension(50, (int) appFrame.getSize().getHeight()));
        return sidePanel;
    }

    private JPanel createMainMenuListPanel() {
        menuList.setName("MenuList");
        menuList.setBackground(steuerung.getPanelBackgroundColor(0));
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
        wrapperLabel.setBackground(steuerung.getPanelBackgroundColor(0));
        wrapperLabel.add(menuName);

        JPanel leftBalancePanel = new JPanel();
        leftBalancePanel.setPreferredSize(new Dimension(45, 45));
        leftBalancePanel.setBackground(steuerung.getPanelBackgroundColor(0));

        JPanel rightBalancePanel = new JPanel();
        rightBalancePanel.setPreferredSize(new Dimension(45, 45));
        rightBalancePanel.setBackground(steuerung.getPanelBackgroundColor(0));

        menuNamePanel.add(leftBalancePanel, BorderLayout.WEST);
        menuNamePanel.add(rightBalancePanel, BorderLayout.EAST);
        menuNamePanel.add(wrapperLabel, BorderLayout.CENTER);
        
        return menuNamePanel;
    }

    private JPanel createTodoItemButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,0));
        buttonPanel.setBackground(steuerung.getPanelBackgroundColor(0));
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
