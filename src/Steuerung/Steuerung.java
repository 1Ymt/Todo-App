package Steuerung;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Config.ColorData;
import Config.ConfigSteuerung;
import Dialog.*;
import Enums.Theme;
import Enums.UiColor;
import Interface.TodoListController;
import TodoItem.*;
import UI.*;

public class Steuerung {
    private Theme currentTheme = Theme.DarkMode;

    private AppFrame appFrame;
    private FolderManager folderManager;
    private FileManager fileManager;
    private ConfigSteuerung configSteuerung;

    public Steuerung() {
        this.configSteuerung = new ConfigSteuerung(this);

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

    public Color getUiColor(UiColor names) {
        ColorData colorData = configSteuerung.getColorData();
        switch (names) {
            case bgDark:
                return new Color(colorData.getBgDark());

            case bg:
                return new Color(colorData.getBg());

            case bgLight:
                return new Color(colorData.getBgLight());
            
            case text:
                return new Color(colorData.getText());
            
            case textMuted:
                return new Color(colorData.getTextMuted());
            
            case highlight:
                return new Color(colorData.getHighlight());
            
            case border:
                return new Color(colorData.getBorder());
            
            case borderMuted:
                return new Color(colorData.getBorderMuted());
            
            case primary:
                return new Color(colorData.getPrimary());
            
            case secondary:
                return new Color(colorData.getSecondary());
            
            case danger:
                return new Color(colorData.getDanger());
            
            case warning:
                return new Color(colorData.getWarning());
            
            case success:
                return new Color(colorData.getSuccess());
            
            case info:
                return new Color(colorData.getInfo());

            default: 
                throw new IllegalArgumentException("Unknown color name: " + names);
        }
    }

    public void createNotizen(String name, JDialog dialog, TodoListController todoListClass) {
        if(name.equals("")) {
            name = "Unbennantes Notiz";
            int index = 1;

            for (TodoItem todoItem : todoListClass.getTodoItems().reversed()) {
                if(todoItem.isNotizen() && todoItem.getName().equals(name)) {
                    name = "Unbennant Notiz" +  " (" + index + ")";
                    index++;
                }
            }
        }else {
            for (TodoItem todoItem : todoListClass.getTodoItems()) {
                if(todoItem.isNotizen() && todoItem.getName().equals(name)) {
                    JOptionPane.showMessageDialog(dialog, "Name bereits vergeben.");
                    return;
                }
            }
        }
        
        dialog.dispatchEvent(new WindowEvent(dialog, WindowEvent.WINDOW_CLOSING));
        NotizenSteuerung notizenSteuerung = new NotizenSteuerung(this, appFrame, name, todoListClass);
        todoListClass.addTodoItem(notizenSteuerung);
    }
    
    public void createOrdner(String name, Color ordnerFarbe, JDialog dialog, TodoListController todoListClass) {
        if(name.equals("")) {
            name = "Unbennant";
            int index = 1;

            for (TodoItem todoItem : todoListClass.getTodoItems().reversed()) {
                if(todoItem.isOrdner() && todoItem.getName().equals(name)) {
                    name = "Unbennant" +  " (" + index + ")";
                    index++;
                }
            }
        }else {
            for (TodoItem todoItem : todoListClass.getTodoItems()) {
                if(todoItem.isOrdner() && todoItem.getName().equals(name)) {
                    JOptionPane.showMessageDialog(dialog, "Name bereits vergeben.");
                    return;
                }
            }
        }
        
        dialog.dispatchEvent(new WindowEvent(dialog, WindowEvent.WINDOW_CLOSING));
        OrdnerSteuerung ordnerSteuerung = new OrdnerSteuerung(this, appFrame, name, todoListClass, ordnerFarbe);
        todoListClass.addTodoItem(ordnerSteuerung);
    }

    public void changeOrdnerProperties(OrdnerSteuerung ordner) {
        new ChangeOrdnerProperties(this, appFrame, ordner);
    }

    public void nextMenuPanel(JPanel nextPanel) {
        folderManager.next(nextPanel);
    }

    public void previousMenuPanel(JPanel currentPanel) {
        folderManager.back(currentPanel);
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

    public void openConfig() {
        folderManager.next(configSteuerung.openConfigFrame());
    }
    
}
