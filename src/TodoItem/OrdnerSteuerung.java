package TodoItem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Data.OrdnerData;
import Data.TodoData;
import Enums.TodoType;
import Interface.TodoListController;
import Steuerung.Steuerung;
import UI.AppFrame;

public class OrdnerSteuerung extends TodoItem implements TodoListController {
    
    private ArrayList<TodoItem> todoItems;
    private Color ordnerFarbe;

    private Steuerung steuerung;
    private OrdnerFrame ordnerFrame;

    public OrdnerSteuerung(Steuerung steuerung, AppFrame appFrame, String name, TodoListController parent, Color farbe) {
        super(name, TodoType.Ordner, parent);
        this.ordnerFarbe = farbe;
        this.steuerung = steuerung;

        this.ordnerFrame = new OrdnerFrame(steuerung, this, appFrame, farbe);
        this.todoItems = new ArrayList<>();
    }

    @Override
    public ArrayList<TodoItem> getTodoItems() {
        return todoItems;
    }

    @Override
    public JPanel getMenuList() {
        return ordnerFrame.getMenuList();
    }

    @Override
    public void addTodoItem(TodoItem todoItem) {
        todoItems.addFirst(todoItem);
        updateMenuList();
    }

    @Override
    public void updateMenuList() {
        JPanel menuList = ordnerFrame.getMenuList();
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
        OrdnerData data = new OrdnerData();

        data.setName(getName());
        data.setType(getType());
        data.setColorRGB(ordnerFarbe.getRGB());

        for (TodoItem todoItem : todoItems) {
            data.setTodoData(todoItem.toData());
        }
        return data;
    }

    @Override
    public JPanel display() {
        return ordnerFrame.getDisplayPanel();
    }

    public String getAllOrdnerPath() {
        String path = getName() + "\\";
        TodoListController parent = getParent();
        while (parent != null) {
            path = parent.getParentName() + "\\" + path;
            parent = parent.getTodoParent();
        }
        path = "C:" + path;
        return path;
    }

    public void setOrdnerFarbe(Color farbe) {
        ordnerFrame.setOrdnerFarbe(farbe);
    }

    public Color getOrdnerFarbe() {
        return ordnerFarbe;
    }

    public ImageIcon getIcon(Color color, int size) {
        return steuerung.setOrdnerIcon(color, size);
    }

    public void previousPanel(JPanel panel) {
        steuerung.previousMenuPanel();
    }

    public void createNewTodoItem() {
        steuerung.openTodoItemCreation(this);
    }

    public JPanel getMenuPanel() {
        return ordnerFrame.menuPanel();
    }

    @Override
    protected MouseListener mouseClicked() {
        OrdnerSteuerung ordner = this;
        MouseListener ml = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1) {
                    steuerung.nextMenuPanel(ordner.getName(), ordnerFrame.menuPanel());                   
                }else if(e.getButton() == MouseEvent.BUTTON3) {
                    steuerung.changeOrdnerProperties(ordner);
                }
            }
        };
            return ml;
        }
}
