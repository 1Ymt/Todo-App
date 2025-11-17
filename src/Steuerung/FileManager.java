package Steuerung;

import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import Data.NotizenData;
import Data.OrdnerData;
import Data.TodoData;
import Enums.TodoType;
import Interface.TodoListController;
import TodoItem.NotizenSteuerung;
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
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls()
                    .registerTypeHierarchyAdapter(TodoData.class, new TodoDataSerializer()).create();

            gson.toJson(mainMenu.toData(), writer);
            JOptionPane.showMessageDialog(appFrame, "Wurde erfolgreicht gespeichert!");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openSaveFile() {
        File file = new File(FILENAME);
        if (!file.isFile() || file.length() == 0) {
            createMainMenuPanel();
            return;
        }

        try (FileReader reader = new FileReader(FILENAME)) {

            TodoItemDeserializer deserializer = new TodoItemDeserializer();
            deserializer.registerDataType(TodoType.Notizen, NotizenData.class);
            deserializer.registerDataType(TodoType.Ordner, OrdnerData.class);

            Gson gson = new GsonBuilder().registerTypeAdapter(TodoData.class, deserializer).create();
            //Gson gson = new Gson();

            OrdnerData data = gson.fromJson(reader, OrdnerData.class);
            List<TodoData> datas = data.getTodoData();

            mainMenu = new OrdnerSteuerung(steuerung, appFrame, data.getName(), null, new Color(data.getColorRGB()));
            folderManager.addMainMenu(mainMenu.getMenuPanel());

            loadFile(datas, mainMenu);

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    
    private void loadFile(List<TodoData> datas, TodoListController parent) {
        for (int i = datas.size() -1; i >= 0; i--) {
            TodoType dataType = datas.get(i).getType();

            switch (dataType) {
                case Ordner:
                    OrdnerData ordnerData = (OrdnerData) datas.get(i);
                    OrdnerSteuerung ordner = new OrdnerSteuerung(steuerung, appFrame, ordnerData.getName(), parent, new Color(ordnerData.getColorRGB()));
                    parent.addTodoItem(ordner);
                    loadFile(ordnerData.getTodoData(), ordner);
                    break;
                case Notizen:
                    NotizenData notizenData = (NotizenData) datas.get(i);
                    NotizenSteuerung notizen = new NotizenSteuerung(steuerung, appFrame, notizenData.getName(), parent);
                    parent.addTodoItem(notizen);
                    break;
                case Task:
                    break;
                default:
                    throw new IllegalArgumentException("No TodoType " + dataType);
            }
        }
    }
    private void createMainMenuPanel() {
        mainMenu = new OrdnerSteuerung(steuerung, appFrame, "Main Menu", null, Color.BLACK);
        folderManager.addMainMenu(mainMenu.getMenuPanel());
    }
}
