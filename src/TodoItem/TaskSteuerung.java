package TodoItem;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Data.TaskData;
import Data.TodoData;
import Dialog.TaskSegmentDialog;
import Enums.TodoType;
import Interface.TodoListController;
import Steuerung.Steuerung;
import UI.AppFrame;

public class TaskSteuerung extends TodoItem {
    
    private ArrayList<TaskSegment> taskSements;

    private Steuerung steuerung;
    private AppFrame appFrame;
    private TaskFrame taskFrame;

    public TaskSteuerung(Steuerung steuerung, AppFrame appFrame, String name, TodoListController parent) {
        super(name, TodoType.Task, parent);
        this.steuerung = steuerung;
        this.appFrame = appFrame;

        this.taskFrame = new TaskFrame(steuerung, this, appFrame);
        this.taskSements = new ArrayList<>();
    }

    public void addTodoItem(TaskSegment task) {
        taskSements.addFirst(task);
        updateMenuList();
    }

    public void updateMenuList() {
        JPanel menuList = taskFrame.getMenuList();
        menuList.removeAll();
        for (TaskSegment task : taskSements) {
            menuList.add(task.getDisplayPanel());
            menuList.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        menuList.revalidate();
        menuList.repaint();
    }

    @Override
    public TodoData toData() {
        TaskData data = new TaskData();
        data.setName(getName());
        data.setType(getType());

        return data;
    }

    @Override
    public JPanel display() {
        return taskFrame.getDisplayPanel();
    }

    public void previousPanel(JPanel panel) {
        steuerung.previousMenuPanel();
    }

    public void openNewTaskSegment() {
        new TaskSegment(steuerung, appFrame, this);
    }

    public void createTaskSegment(TaskSegment taskSegment, TaskSegmentDialog taskSegmentDialog, boolean isEdit) {
        if(taskSegment.getTitel().equals("")) {
            JOptionPane.showMessageDialog(taskSegmentDialog, "Bitte einen Namen eingeben.");
            return;
        }else {
            for (TaskSegment task : taskSements) {
                if (task.getTitel().equals(taskSegment.getTitel())) {
                    if (isEdit) {
                        int index = taskSements.indexOf(task);
                        taskSements.remove(task);
                        taskSements.add(index, taskSegment);
                        updateMenuList();
                        taskSegmentDialog.dispose();
                        return;

                    } else {
                        JOptionPane.showMessageDialog(taskSegmentDialog, "Name bereits vergeben.");
                        return;
                    }
                }
            }
        }
        taskSegmentDialog.dispose();
        addTodoItem(taskSegment);

    }

    @Override
    protected MouseListener mouseClicked() {
        TaskSteuerung task = this;
        MouseListener ml = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1) {
                    steuerung.nextMenuPanel(task.getName(), taskFrame.menuPanel());                   
                }else if(e.getButton() == MouseEvent.BUTTON3) {
                    
                }
            }
        };
            return ml;
    }
}
