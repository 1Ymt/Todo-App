package TodoItem;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;

import javax.swing.JPanel;

import Data.TodoData;
import Enums.TodoType;
import Interface.TodoListController;

public abstract class TodoItem {

    private String name;
    private TodoType type;
    private TodoListController parent;
    private LocalDateTime date;

    public TodoItem(String name, TodoType type, TodoListController parent) {
        this.name = name;
        this.type = type;
        this.parent = parent;
        this.date = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public TodoType getType() {
        return type;
    }

    public TodoListController getParent() {
        return parent;
    }

    public LocalDateTime isCreatedDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void updateDate() {
        this.date = LocalDateTime.now();
    }

    public boolean isOrdner() {
        if(type == TodoType.Ordner) {
            return true;
        }
        return false;
    }

    public boolean isNotizen() {
        if(type == TodoType.Notizen) {
            return true;
        }
        return false;
    }

    public boolean isTask() {
        if (type == TodoType.Task) {
            return true;
        }
        return false;
    }
    

    public abstract TodoData toData();
    public abstract JPanel display();
    protected abstract MouseListener mouseClicked();
}
