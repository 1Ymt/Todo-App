package Steuerung;

import java.awt.*;
import java.awt.MenuBar;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Data.TodoData;
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
        
        File file = new File("Saved");
             if(file.isFile()) {
                 openSaveFile();
             }
        
        
    }

    /*
     * Wenn man eine neue TodoItem erstellt, wird diese Method aufgerufen, um die TodoItemDialog zu zeigen.
     */
    public void createNeueTodoItemMainMenu(TodoItemTask todoItemClass) {
        new TodoItemDialog(this, appFrame, todoItemClass);
    }

    public void createOrdner(String name, Color farbe, JDialog dialog, TodoItemTask todoItemTask) {
        if(name.equals("")) {
            name = "Unbennant";
            int index = 1;

            for (TodoItem todoItem : todoItemTask.getTodoItems().reversed()) {
                if(todoItem.isOrdner() && todoItem.getName().equals(name)) {
                    name = "Unbennant" +  " (" + index + ")";
                    index++;
                }
            }
        }else {
            for (TodoItem todoItem : todoItemTask.getTodoItems()) {
                if(todoItem.isOrdner() && todoItem.getName().equals(name)) {
                    JOptionPane.showMessageDialog(dialog, "Name bereits vergeben.");
                    return;
                }
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

    public void serialize() {
        try (FileWriter writer = new FileWriter("Saved")){
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            gson.toJson(mainMenu.toData(), writer);
            System.out.println("Successfully Saved");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openSaveFile() {    //Naming
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("Saved")){
            TodoData data = gson.fromJson(reader, TodoData.class);
            deserialize(data, mainMenu);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    private void deserialize(TodoData data, TodoItemTask parent) {
        for (TodoData todoData : data.getTodoData().reversed()) {
            if(todoData.getType().equals("Ordner")) {

            Ordner ordner = new Ordner(this, parent, appFrame, todoData.getName(), new Color(todoData.getColorRGB()));
            parent.addTodoItem(ordner);
            deserialize(todoData, ordner);
            }else if(todoData.getType().equals("Task")) {

            }else if(todoData.getType().equals("Notizen")) {

            }
        }
    }
}
