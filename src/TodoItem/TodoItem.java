package TodoItem;
import java.time.LocalDateTime;

import javax.swing.JPanel;

import Interface.TodoItemTask;

public abstract class TodoItem {

    private String name;
    private String type;
    private TodoItemTask parent;
    private LocalDateTime datum;

    public TodoItem(String name, String type, TodoItemTask parent) {
        this.name = name;
        this.type = type;
        this.parent = parent;
        this.datum = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public TodoItemTask getParent() {
        return parent;
    }

    public LocalDateTime isCreatedDate() {
        return datum;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void updateDate() {
        this.datum = LocalDateTime.now();
    }

    public boolean isOrdner() {
        if(type.equals("Ordner")) {
            return true;
        }
        return false;
    }

    public boolean isNotizen() {
        if(type.equals("Notizen")) {
            return true;
        }
        return false;
    }

    public boolean isTask() {
        if(type.equals("Task")) {
            return true;
        }
        return false;
    }

    public abstract JPanel display();
}
