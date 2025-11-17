package Data;

import Enums.TodoType;

public class TodoData {
    private String name;
    private TodoType type;

    public TodoData() {

    }

    public TodoType getType() {
        return type;
    }

    public void setType(TodoType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
}
