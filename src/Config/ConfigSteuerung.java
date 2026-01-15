package Config;

import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Enums.Theme;
import Steuerung.Steuerung;
import UI.AppFrame;

public class ConfigSteuerung {

    private final String FILENAME = "Config";
    
    private Steuerung steuerung;
    private ConfigFrame configFrame;
    
    private ConfigData configData;
    private ColorData colorData;

    private Theme currentTheme = Theme.LightMode;

    public ConfigSteuerung(Steuerung steuerung) {
        this.steuerung = steuerung;

        setupApp();
    }

    private void setupApp() {
        setupConfig();
    }

    private void setupConfig() {
        File file = new File(FILENAME);
        if (!file.isFile() || file.length() == 0) {
            createNewConfigData();
            return;
        } 

        try (FileReader reader = new FileReader(FILENAME)) {
            Gson gson = new Gson();
            configData = gson.fromJson(reader, ConfigData.class);
            colorData = configData.getColorData();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void createNewConfigData() {
        configData = new ConfigData();
        if(colorData == null) {
            createNewColorData();
        }
        configData.setColorData(colorData);
    }

    private void createNewColorData() {
        colorData = new ColorData();
        switch (currentTheme) {
            case LightMode:
                colorData.setTheme(currentTheme);
                int bgDark      = 14345969;
                int bg          = 15135743;
                int bgLight     = 16777215;
                int text        = 2842;
                int textMuted   = 3623773;
                int highlight   = 16777215;
                int border      = 7177111;
                int borderMuted = 9150902;
                int primary     = 609404;
                int secondary   = 6043904;
                int danger      = 8345939;
                int warning     = 7038275;
                int success     = 4877658;
                int info        = 5399424;

                colorData.setBgDark(bgDark);
                colorData.setBg(bg);
                colorData.setBgLight(bgLight);
                colorData.setText(text);
                colorData.setTextMuted(textMuted);
                colorData.setHighlight(highlight);
                colorData.setBorder(border);
                colorData.setBorderMuted(borderMuted);
                colorData.setPrimary(primary);
                colorData.setSecondary(secondary);
                colorData.setDanger(danger);
                colorData.setWarning(warning);
                colorData.setSuccess(success);
                colorData.setInfo(info);
                break;
        
            default:
                break;
        }
    }

    public void confirmButtonsListener(JButton button) {
        if (button.getName().equals("Bestätigt")) {
            saveConfig();
            steuerung.previousMenuPanel(configFrame);
        } else {
            steuerung.previousMenuPanel(configFrame);

        }
        configFrame = null;
    }

    public void saveConfig() {
        try (FileWriter writer = new FileWriter(FILENAME)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

            gson.toJson(configData, writer);
            JOptionPane.showMessageDialog(null, "Wurde erfolgreicht gespeichert!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JPanel openConfigFrame(AppFrame appFrame) {
        configFrame = new ConfigFrame(steuerung, this, appFrame);
        return configFrame;
    }

    public ColorData getColorData() {
        return colorData;
    }
    
    //Helper Methods
        public void printAllColors() {
        System.out.println("=== ConfigData Colors ===");

        var fields = colorData.getClass().getDeclaredFields();

        for (var field : fields) {
            field.setAccessible(true);
                if(field.getType() == int.class) {
                    try {
                        int c =  (int) field.get(colorData);
                        System.out.println(field.getName() + ": " + Integer.toString(c));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
        }
    }
}
