package TodoItem;

import java.awt.event.MouseListener;

import javax.swing.JPanel;

import Data.TodoData;
import Enums.TodoType;
import Interface.TodoListController;
import Steuerung.Steuerung;
import UI.AppFrame;

public class TaskSteuerung extends TodoItem {
    
    private Steuerung steuerung;
    private AppFrame appFrame;

    private TaskFrame taskFrame;

    public TaskSteuerung(Steuerung steuerung, AppFrame appFrame, String name, TodoListController parent) {
        super(name, TodoType.Task, parent);
        this.steuerung = steuerung;
        this.appFrame = appFrame;

        this.taskFrame = new TaskFrame(this, appFrame);
    }

    @Override
    public TodoData toData() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toData'");
    }

    @Override
    public JPanel display() {
        return taskFrame.getDisplayPanel();
    }

    public void createNeueTaskItem() {
        System.out.println("Neue Task");
    }

    public void previousPanel(JPanel panel) {
        steuerung.previousMenuPanel(panel);
    }

    @Override
    protected MouseListener mouseClicked() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseClicked'");
    }

}
