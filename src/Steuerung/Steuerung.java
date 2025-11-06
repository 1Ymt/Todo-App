package Steuerung;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Dialog.*;
import Enums.Theme;
import Interface.TodoListController;
import TodoItem.*;
import UI.*;

public class Steuerung {
    private Theme currentTheme = Theme.DarkMode;

    private AppFrame appFrame;
    private FolderManager folderManager;
    private FileManager fileManager;

    public Steuerung() {
        this.folderManager = new FolderManager(this);
        this.appFrame = new AppFrame(this, folderManager);
        this.fileManager = new FileManager(this, appFrame, folderManager);
        
        fileManager.openSaveFile();
    }

    /*
     * Wenn man eine neue TodoItem erstellt, wird diese Method aufgerufen, um die TodoItemDialog zu zeigen.
     */
    public void createNeueTodoItemMainMenu(TodoListController todoItemClass) {
        new TodoItemDialog(this, appFrame, todoItemClass);
    }

    public Color getPanelBackgroundColor(int order) {
        if (currentTheme == Theme.DarkMode) {
            switch (order) {
                case 0:
                    return new Color(50, 50, 50);

                default:
                    return Color.WHITE;
            }
        }
        return null;
    }
    

    public void createOrdner(String name, Color ordnerFarbe, JDialog dialog, TodoListController TodoListClass) {
        if(name.equals("")) {
            name = "Unbennant";
            int index = 1;

            for (TodoItem todoItem : TodoListClass.getTodoItems().reversed()) {
                if(todoItem.isOrdner() && todoItem.getName().equals(name)) {
                    name = "Unbennant" +  " (" + index + ")";
                    index++;
                }
            }
        }else {
            for (TodoItem todoItem : TodoListClass.getTodoItems()) {
                if(todoItem.isOrdner() && todoItem.getName().equals(name)) {
                    JOptionPane.showMessageDialog(dialog, "Name bereits vergeben.");
                    return;
                }
            }
        }

        
        dialog.dispatchEvent(new WindowEvent(dialog, WindowEvent.WINDOW_CLOSING));
        OrdnerSteuerung ordner = new OrdnerSteuerung(this, appFrame, name, TodoListClass, ordnerFarbe);
        TodoListClass.addTodoItem(ordner);
    }

    public void changeOrdnerProperties(OrdnerSteuerung ordner) {
        new ChangeOrdnerProperties(this, appFrame, ordner);
    }

    public void nextMenuPanel(JPanel panel) {
        folderManager.next(panel);
    }

    public void previousMenuPanel(JPanel panel) {
        folderManager.back(panel);
    }

    public ImageIcon setOrdnerIcon(Color color, int size) {
        if (color.equals(Color.RED)) {
            ImageIcon ordner = new ImageIcon(getClass().getResource("/Icons/Ordner/ordner_rot.PNG"));
            Image image = ordner.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(image);
            return icon;

        } else if (color.equals(Color.ORANGE)) {
            ImageIcon ordner = new ImageIcon(getClass().getResource("/Icons/Ordner/ordner_orange.PNG"));
            Image image = ordner.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(image);
            return icon;

        } else if (color.equals(Color.YELLOW)) {
            ImageIcon ordner = new ImageIcon(getClass().getResource("/Icons/Ordner/ordner_gelb.PNG"));
            Image image = ordner.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(image);
            return icon;

        } else if (color.equals(Color.GREEN)) {
            ImageIcon ordner = new ImageIcon(getClass().getResource("/Icons/Ordner/ordner_gruen.PNG"));
            Image image = ordner.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(image);
            return icon;

        } else if (color.equals(Color.CYAN)) {
            ImageIcon ordner = new ImageIcon(getClass().getResource("/Icons/Ordner/ordner_tuerkis.PNG"));
            Image image = ordner.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(image);
            return icon;

        } else if (color.equals(Color.MAGENTA)) {
            ImageIcon ordner = new ImageIcon(getClass().getResource("/Icons/Ordner/ordner_magenta.PNG"));
            Image image = ordner.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(image);
            return icon;

        } else if (color.equals(Color.GRAY)) {
            ImageIcon ordner = new ImageIcon(getClass().getResource("/Icons/Ordner/ordner_grau.PNG"));
            Image image = ordner.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(image);
            return icon;

        } else if (color.equals(Color.BLACK)) {
            ImageIcon ordner = new ImageIcon(getClass().getResource("/Icons/Ordner/ordner_schwarz.PNG"));
            Image image = ordner.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(image);
            return icon;

        } else
            throw new IllegalArgumentException("No Icon for color " + color);
    }

    //Steuerung für die Speicherung und Öffnen des Todo Apps.
    public void save() {
        fileManager.save();
    }

    public void openSaveFile() {
        fileManager.openSaveFile();
    }
    
}
