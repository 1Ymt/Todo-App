package Steuerung;

import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Data.TodoData;
import Interface.TodoListController;
import TodoItem.OrdnerSteuerung;
import UI.AppFrame;

public class FileManager {

    private Steuerung steuerung;
    private AppFrame appFrame;
    private FolderManager folderManager;

    private OrdnerSteuerung mainMenu;

    private final String FILENAME = "Saved";

    public FileManager(Steuerung steuerung, AppFrame appFrame, FolderManager folderManager) {
        this.steuerung = steuerung;
        this.appFrame = appFrame;
        this.folderManager = folderManager;
    }

    public void save() {
        try (FileWriter writer = new FileWriter(FILENAME)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            gson.toJson(mainMenu.toData(), writer);
            System.out.println("Successfully Saved");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openSaveFile() { //Naming
        File file = new File(FILENAME);
        if (!file.isFile() || file.length() == 0) {
            createMainMenuPanel();
            return;
        }

        Gson gson = new Gson();
        try (FileReader reader = new FileReader(FILENAME)) {
            TodoData data = gson.fromJson(reader, TodoData.class);
            mainMenu = new OrdnerSteuerung(steuerung, appFrame, data.getName(), null, new Color(data.getColorRGB()));
            folderManager.addMainMenu(mainMenu.getMenuPanel());
            loadFile(data, mainMenu);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadFile(TodoData data, TodoListController parent) {
        for (TodoData todoData : data.getTodoData().reversed()) {
            switch (todoData.getType()) {
                case Ordner:
                    OrdnerSteuerung ordner = new OrdnerSteuerung(steuerung, appFrame, todoData.getName(), parent, new Color(todoData.getColorRGB()));
                    parent.addTodoItem(ordner);
                    loadFile(todoData, ordner);
                    break;
                case Task:
                    break;
                case Notizen:
                    break;
                default:
                    throw new IllegalArgumentException("No TodoType " + todoData.getType());
            }
        }
    }
    
    private void createMainMenuPanel() {
        mainMenu = new OrdnerSteuerung(steuerung, appFrame, "Main Menu", null, Color.BLACK);
        folderManager.addMainMenu(mainMenu.getMenuPanel());
    }
}
