package Data;

import java.util.ArrayList;

public class OrdnerData extends TodoData{
    private int colorRGB;
    private ArrayList<TodoData> todoData = new ArrayList<>();

    public OrdnerData() {
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
