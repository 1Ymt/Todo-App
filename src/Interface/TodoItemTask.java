package Interface;

import javax.swing.JPanel;

import TodoItem.TodoItem;

public interface TodoItemTask {
    TodoItem[] getTodoItems();
    JPanel getMenuList();
    void addTodoItem(TodoItem todoItem);
    void updateMenuList();
}
