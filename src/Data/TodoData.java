package Data;

import java.util.ArrayList;

import Enums.TodoType;

public class TodoData {
    private String name;
    private TodoType type;
    private int colorRGB;
    private ArrayList<TodoData> todoData = new ArrayList<>();;

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

    public int getColorRGB() {
        return colorRGB;
    }

    public void setColorRGB(int colorRGB) {
        this.colorRGB = colorRGB;
    }

    public ArrayList<TodoData> getTodoData() {
        return todoData;
    }

    public void setTodoData(TodoData todoData) {
        this.todoData.addLast(todoData);
    }
}
