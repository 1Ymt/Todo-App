package Interface;

import java.util.ArrayList;

import javax.swing.JPanel;

import TodoItem.TodoItem;

public interface TodoListController {
    ArrayList<TodoItem> getTodoItems();
    JPanel getMenuList();
    void addTodoItem(TodoItem todoItem);

    void updateMenuList();
    String getParentName();
}
