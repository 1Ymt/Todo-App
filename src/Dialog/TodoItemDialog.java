package Dialog;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;

import javax.swing.*;

import Enums.TodoType;
import Interface.TodoListController;
import Steuerung.Steuerung;

public class TodoItemDialog extends JDialog{

    private final TodoType[] ITEMNAMES = TodoType.values();

    private Steuerung steuerung;
    private JFrame appFrame;
    private TodoListController todoItemClass;
    private JToggleButton[] allTodoItemsButton;
    private String taskAuswahl;

    public TodoItemDialog(Steuerung steuerung, JFrame appFrame, TodoListController todoItemClass) {
        super(appFrame, "Neu", true); //modal: "true" - sodass man nicht auf das andere Fenster klicken kann
        this.steuerung = steuerung;
        this.appFrame = appFrame;
        this.todoItemClass = todoItemClass;

        

        this.allTodoItemsButton = new JToggleButton[ITEMNAMES.length];
        this.taskAuswahl = "";
        
        this.setSize(500, 300);
        this.setLayout(new BorderLayout());

        this.add(createMainMenuListPanel(), BorderLayout.CENTER);
        this.add(createConfirmPanelMenu(), BorderLayout.SOUTH);
        this.add(createSidePanel(), BorderLayout.WEST);
        this.add(createSidePanel(), BorderLayout.EAST);

        this.setLocationRelativeTo(appFrame); // Die Anzeige wird dann im Zentrum angezeigt.
        this.setVisible(true);

    }

    /*
     * Panel für das Wählen der Tasks per Button klick.
     */
    private JPanel createMainMenuListPanel() {
        JPanel mainMenuList =  new JPanel();
        mainMenuList.setLayout(new BoxLayout(mainMenuList, BoxLayout.Y_AXIS));
        mainMenuList.setBackground(new Color(95, 111, 181));

        /*
         * sorgt dafür, dass wenn man mehr TodoItems hat als standard (3), 
         * dann soll ein Button mit dem Namen im Array (itemNames) erschaffen werden.
         */
        for (int i = 0; i < ITEMNAMES.length; i++) {
            mainMenuList.add(createTodoItemButton(ITEMNAMES[i], i));
        }
        return mainMenuList;
    }
    
    /*
     * Hilfsmethode für die createMainMenuListPanel, um die allTodoItemsButtonArray Button zu erschaffen
     */
    private JPanel createTodoItemButton(TodoType name, int i) {
        //WrapperPanel für den Button erzeugt um ein besseres Managment für den Layout zu haben
        JPanel wrapperButton = new JPanel(new FlowLayout(FlowLayout.LEFT,  5, 5)); 
        wrapperButton.setOpaque(false);
        wrapperButton.setAlignmentX(Component.LEFT_ALIGNMENT);  //Schiebt die Tasks Buttons nach links.

        allTodoItemsButton[i] = new JToggleButton(name.name());
        allTodoItemsButton[i].setName(name.name());
        allTodoItemsButton[i].setPreferredSize(new Dimension(300,35));
        allTodoItemsButton[i].setBackground(Color.WHITE);
        allTodoItemsButton[i].addItemListener(e -> allTodoItemsButtoListener(allTodoItemsButton[i])); //Lampda Expression

        wrapperButton.setMaximumSize(allTodoItemsButton[i].getPreferredSize());
        wrapperButton.add(allTodoItemsButton[i]);

        return wrapperButton;
    }
    
    /*
     * Panel für die Seiteränder
     */
    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(Color.WHITE);
        sidePanel.setPreferredSize(new Dimension(20, this.getHeight()));
        return sidePanel;
    }

    /*
     * Panel für die Confirm Menu
     */
    private JPanel createConfirmPanelMenu() {
        JPanel confirmPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        confirmPanel.setPreferredSize(new Dimension(this.getWidth(), 50));
        confirmPanel.setBackground(Color.WHITE);

        confirmPanel.add(createConfirmButtons("Bestätigt"));
        confirmPanel.add(createConfirmButtons("Abbrechen"));

        return confirmPanel;
    }

    /*
     * Hilfsmethode für die createConfirmPanelMenu, um die Bestätigt und Abbrechen Button zu erschaffen
     */
    private JPanel createConfirmButtons(String name) {
        JPanel wrapperButton = new JPanel(new BorderLayout());

        JButton button = new JButton(name);
        button.setName(name);
        button.addActionListener(e -> confirmButtonsListener(button));
        
        wrapperButton.setPreferredSize(new Dimension(100, 30));
        wrapperButton.add(button, BorderLayout.CENTER);
        return wrapperButton;
    }

    /*
     * Diese Methode sorgt dafür, dass es nur EIN Button von allTodoItemButtonArray angeklickt wurde.
     * Wenn ein Knop schon angeklickt wurde und der User ein weiteres Button drückt, 
     * dann wird der schon angeklickt Button deangeklickt und der von User ausgewählte Button angeklickt.
     */
    public void allTodoItemsButtoListener(JToggleButton sourceButton) {
        //Prüft, ob der Button angeklickt wurde
        if (sourceButton.isSelected()) {

            /*
             * Durchlaufen das Array und schaut, ob nur ein Button angelickt wurde.
             * Wenn der aktuelle Button in i nicht der angeklickte Button von User ist,
             * dann soll alle button deselect.
             */
            for (int i = 0; i < allTodoItemsButton.length; i++) {
                if (allTodoItemsButton[i] != sourceButton) {
                    allTodoItemsButton[i].setSelected(false);
                }
            }

            taskAuswahl = sourceButton.getName(); //der taskAuswahl soll den gleichen Namen wie der Button, die die angeklickt wurde, heißen.

            //Prüft, ob der Button deselect wurde
        } else {
            taskAuswahl = "";   //Wenn der Button deselect wird, dann soll der taskAuswahl wieder auf nichts gemacht werden.
        }
    }

    /*
     * Diese Funktion ist für den Bestätigt und Abbrechen Button verantwortlich. 
     */
    private void confirmButtonsListener(JButton button) {
        if (button.getName().equals("Bestätigt")) {
            
            if(taskAuswahl.equals("")) {
            JOptionPane.showMessageDialog(this, "Bitte eine Task auswählen");

            }else{
                this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

                TodoType types = TodoType.valueOf(taskAuswahl);

                switch (types) {
                    case Notizen:
                        System.out.println("Notizen");
                        steuerung.createNotizen("", this, todoItemClass);
                        break;
                    case Ordner:
                        new OrdnerDialog(steuerung, appFrame, todoItemClass);
                        break;
                    case Task:
                        System.out.println("Task");
                        //new Task(taskAuswahl, taskAuswahl, todoItemClass, steuerung, null)
                        break;
                    default:
                        throw new IllegalArgumentException("No Method named: " + taskAuswahl);
                }
            }

        }else if(button.getName().equals("Abbrechen")) {
            //Wenn das Button betätigt wird, dann schließt sich das Fenster
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }
}
