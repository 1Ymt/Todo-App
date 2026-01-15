package TodoItem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JPanel;

import Data.TodoData;
import Enums.TodoType;
import Interface.TodoListController;
import Steuerung.Steuerung;
import UI.AppFrame;

public class TaskSteuerung extends TodoItem implements TodoListController {
    
    private ArrayList<TodoItem> todoItems;

    private Steuerung steuerung;
    private TaskFrame taskFrame;

    public TaskSteuerung(Steuerung steuerung, AppFrame appFrame, String name, TodoListController parent) {
        super(name, TodoType.Task, parent);
        this.steuerung = steuerung;

        this.taskFrame = new TaskFrame(steuerung, this, appFrame);
        this.todoItems = new ArrayList<>();
    }

    @Override
    public ArrayList<TodoItem> getTodoItems() {
        return todoItems;
    }

    @Override
    public JPanel getMenuList() {
        return taskFrame.getMenuList();
    }

    @Override
    public void addTodoItem(TodoItem todoItem) {
        todoItems.addFirst(todoItem);
        updateMenuList();
    }

    @Override
    public void updateMenuList() {
        JPanel menuList = taskFrame.getMenuList();
        menuList.removeAll();
        for (TodoItem todoItem : todoItems) {
            menuList.add(todoItem.display());
            menuList.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        menuList.revalidate();
        menuList.repaint();
    }

    @Override
    public String getParentName() {
        return getName();
    }

    @Override
    public TodoListController getTodoParent() {
        return getParent();
    }

    @Override
    public TodoData toData() {
        throw new UnsupportedOperationException("Unimplemented method 'toData'");
    }

    @Override
    public JPanel display() {
        return taskFrame.getDisplayPanel();
    }

    public void previousPanel(JPanel panel) {
        steuerung.previousMenuPanel(panel);
    }

    public void createNewTaskSegment() {
        throw new UnsupportedOperationException("Unimplemented method 'createNewTask'");
    }

    public JPanel getMenuPanel() {
        return taskFrame.menuPanel();
    }

    @Override
    protected MouseListener mouseClicked() {
        MouseListener ml = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1) {
                    steuerung.nextMenuPanel(taskFrame.menuPanel());                   
                }else if(e.getButton() == MouseEvent.BUTTON3) {
                    
                }
            }
        };
            return ml;
        }
}
