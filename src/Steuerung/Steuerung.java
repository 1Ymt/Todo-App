package Steuerung;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Dialog.*;
import Interface.TodoItemTask;
import TodoItem.*;
import UI.*;

public class Steuerung {
    private AppFrame appFrame;
    private MainMenuPanel mainMenu;
    private FolderManager folderManager;

    public Steuerung() {
        this.appFrame = new AppFrame(this);
        this.mainMenu = new MainMenuPanel(this, appFrame);
        this.folderManager = new FolderManager(this, mainMenu);

        appFrame.addMainPanel(folderManager);
    }

    /*
     * Wenn man eine neue TodoItem erstellt, wird diese Method aufgerufen, um die TodoItemDialog zu zeigen.
     */
    public void createNeueTodoItemMainMenu(TodoItemTask todoItemClass) {
        new TodoItemDialog(this, appFrame, todoItemClass);
    }

    public void createOrdner(String name, Color farbe, JDialog dialog, TodoItemTask todoItemTask) {
        TodoItem[] todoItems = todoItemTask.getTodoItems();

        if(name.equals("")) {
            name = "Unbennant";
            int index = 1;

            for (TodoItem todoItem : todoItems) {
                if(todoItem.isOrdner() && todoItem.getName().equals(name)) {
                    name = "Unbennant" +  " (" + index + ")";
                    index++;
                }
            }
        }

        for (TodoItem todoItem : todoItems) {
            if(todoItem.isOrdner() && todoItem.getName().equals(name)) {
                JOptionPane.showMessageDialog(dialog, "Name bereits vergeben.");
                return;
            }
        }
        dialog.dispatchEvent(new WindowEvent(dialog, WindowEvent.WINDOW_CLOSING));
        Ordner ordner = new Ordner(this, todoItemTask, appFrame, name, farbe);
        todoItemTask.addTodoItem(ordner);
    }

    public void changeOrdnerProperties(Ordner ordner) {
        new ChangeOrdnerProperties(this, appFrame, ordner);
    }

    public void nextMenuPanel(JPanel panel) {
        folderManager.next(panel);
    }

    public void previousMenuPanel(JPanel panel) {
        folderManager.back(panel);
    }

    public ImageIcon setOrdnerIcon(Color color, int size) {
        if(color.equals(Color.RED)) {
            ImageIcon ordner = new ImageIcon(getClass().getResource("/Icons/Ordner/ordner_rot.PNG"));
            Image image = ordner.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
		    ImageIcon icon = new ImageIcon(image);
            return icon;

        }else if(color.equals(Color.ORANGE)) {
            ImageIcon ordner = new ImageIcon(getClass().getResource("/Icons/Ordner/ordner_orange.PNG"));
            Image image = ordner.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
		    ImageIcon icon = new ImageIcon(image);
            return icon;

        }else if(color.equals(Color.YELLOW)) {
            ImageIcon ordner = new ImageIcon(getClass().getResource("/Icons/Ordner/ordner_gelb.PNG"));
            Image image = ordner.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
		    ImageIcon icon = new ImageIcon(image);
            return icon;
            
        }else if(color.equals(Color.GREEN)) {
            ImageIcon ordner = new ImageIcon(getClass().getResource("/Icons/Ordner/ordner_gruen.PNG"));
            Image image = ordner.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
		    ImageIcon icon = new ImageIcon(image);
            return icon;
            
        }else if(color.equals(Color.CYAN)) {
            ImageIcon ordner = new ImageIcon(getClass().getResource("/Icons/Ordner/ordner_tuerkis.PNG"));
            Image image = ordner.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
		    ImageIcon icon = new ImageIcon(image);
            return icon;
            
        }else if(color.equals(Color.MAGENTA)) {
            ImageIcon ordner = new ImageIcon(getClass().getResource("/Icons/Ordner/ordner_magenta.PNG"));
            Image image = ordner.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
		    ImageIcon icon = new ImageIcon(image);
            return icon;
            
        }else if(color.equals(Color.GRAY)) {
            ImageIcon ordner = new ImageIcon(getClass().getResource("/Icons/Ordner/ordner_grau.PNG"));
            Image image = ordner.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
		    ImageIcon icon = new ImageIcon(image);
            return icon;
            
        }else if(color.equals(Color.BLACK)) {
            ImageIcon ordner = new ImageIcon(getClass().getResource("/Icons/Ordner/ordner_schwarz.PNG"));
            Image image = ordner.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
		    ImageIcon icon = new ImageIcon(image);
            return icon;
            
        }else 
        System.out.println("Fehler");
        return null;
    }
}
