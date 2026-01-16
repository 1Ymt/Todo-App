package Config;

import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
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
    private AppFrame appFrame;
    private ConfigFrame configFrame;
    
    private ConfigData configData;
    private ColorData colorData;
    private ColorData oldColorData;

    private Theme currentTheme = Theme.LightMode;

    public ConfigSteuerung(Steuerung steuerung, AppFrame appFrame) {
        this.steuerung = steuerung;
        this.appFrame = appFrame;

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
            oldColorData = colorData;
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
        oldColorData = colorData;
        saveConfig();
    }

    private void createNewColorData() {
        colorData = new ColorData();
        switch (currentTheme) {
            case LightMode:
                colorData.setTheme(currentTheme);
                defaultLightModeColor();
                break;

            default:
                break;
        }
       
    }
    
    private void defaultLightModeColor() {
        int bgDark = 14345969;
        int bg = 15135743;
        int bgLight = 16777215;
        int text = 2842;
        int textMuted = 3623773;
        int highlight = 16777215;
        int border = 7177111;
        int borderMuted = 9150902;
        int primary = 609404;
        int secondary = 6043904;
        int danger = 8345939;
        int warning = 7038275;
        int success = 4877658;
        int info = 5399424;

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
    }

    public void confirmButtonsListener(JButton button) {
        if (button.getName().equals("Bestätigt")) {
            saveConfig();
            JOptionPane.showMessageDialog(appFrame, "Wurde erfolgreicht gespeichert!");
            steuerung.reloadFile();
            steuerung.firstMenuPanel();
        } else {
            colorData = oldColorData;
            steuerung.previousMenuPanel();
        }
    }

    public void saveConfig() {
        colorData.setTheme(currentTheme);
        configData.setColorData(colorData);

        try (FileWriter writer = new FileWriter(FILENAME)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            gson.toJson(configData, writer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JPanel openConfigFrame() {
        configFrame = new ConfigFrame(steuerung, this, appFrame);
        
        return configFrame;
    }

    public ColorData getColorData() {
        return colorData;
    }

    public void changeUIColor(JButton colorButton, String colorName) {
        Color initialColor = colorButton.getBackground();
        Color newColor = JColorChooser.showDialog(appFrame, "Wählen Sie eine neue Farbe", initialColor);
        if (newColor == null) {
            newColor = initialColor;
        }
        colorButton.setBackground(newColor);

        switch (colorName) {
            case "Background-Dark":
                colorData.setBgDark(newColor.getRGB());
                break;
            case "Background":
                colorData.setBg(newColor.getRGB());
                break;
            case "Background-Light":
                colorData.setBgLight(newColor.getRGB());
                break;
            case "Text":
                colorData.setText(newColor.getRGB());
                break;
            case "Text-Muted":
                colorData.setTextMuted(newColor.getRGB());
                break;
            case "Highlight":
                colorData.setHighlight(newColor.getRGB());
                break;
            case "Border":
                colorData.setBorder(newColor.getRGB());
                break;
            case "Border-Muted":
                colorData.setBorderMuted(newColor.getRGB());
                break;
            case "Primary":
                colorData.setPrimary(newColor.getRGB());
                break;
            case "Secondary":
                colorData.setSecondary(newColor.getRGB());
                break;
            case "Danger":
                colorData.setDanger(newColor.getRGB());
                break;
            case "Warning":
                colorData.setWarning(newColor.getRGB());
                break;
            case "Success":
                colorData.setSuccess(newColor.getRGB());
                break;
            case "Info":
                colorData.setInfo(newColor.getRGB());
                break;
            default:
                break;
        }
        steuerung.reloadPanel(new ConfigFrame(steuerung, this, appFrame));
    }

     public void changeTheme(JComboBox<Theme> themePanel) {
        currentTheme = Theme.valueOf(themePanel.getSelectedItem().toString());
    }

    public void resetUIColor() {
        int option = -1;
        option = JOptionPane.showConfirmDialog(appFrame, "Willst du die UI Farben resetten?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            switch (currentTheme) {
                case LightMode:
                    colorData.setTheme(currentTheme);
                    defaultLightModeColor();
                    break;
            
                default:
                    break;
            }
            steuerung.reloadPanel(new ConfigFrame(steuerung, this, appFrame));
            
        }
    }
    
    //Helper Methods
    public void printAllColors(ColorData a) {
        System.out.println("=== "+ a.getClass().getName() + "===");

        var fields = a.getClass().getDeclaredFields();

        for (var field : fields) {
            field.setAccessible(true);
                if(field.getType() == int.class) {
                    try {
                        int c =  (int) field.get(a);
                        System.out.println(field.getName() + ": " + Integer.toString(c));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
        }
    }
}
