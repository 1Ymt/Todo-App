package Data;

import java.util.ArrayList;

public class TodoData {
    private String name;
    private String type;
    private int colorRGB;
    private ArrayList<TodoData> todoData = new ArrayList<>();;

    public TodoData() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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
