package TodoItem;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Dialog.TaskSegmentDialog;
import Enums.UIColor;
import Steuerung.Steuerung;
import UI.AppFrame;

public class TaskSegment {
    private Steuerung steuerung;
    private AppFrame appFrame;
    private TaskSteuerung taskSteuerung;

    private String titel;
    private String beschreibung;
    
    public TaskSegment(Steuerung steuerung, AppFrame appFrame, TaskSteuerung taskSteuerung) {
        this.steuerung = steuerung;
        this.appFrame = appFrame;
        this.taskSteuerung = taskSteuerung;

        this.titel = "";
        this.beschreibung = "";
    }

    public JPanel getDisplayPanel() {
        JPanel segmentPanel = new JPanel();
        segmentPanel.setLayout(new FlowLayout());
        segmentPanel.setMaximumSize(new Dimension(appFrame.getWidth(), 60));
        segmentPanel.setBackground(steuerung.getUiColor(UIColor.bgLight));
        segmentPanel.addMouseListener(mouseClicked());
        segmentPanel.setToolTipText(titel);
        segmentPanel.setBorder(BorderFactory.createRaisedBevelBorder());

        JLabel taskNameLabel = new JLabel(titel);
        taskNameLabel.setFont(new Font("ARIAL_BOLD", Font.BOLD, 15));
        taskNameLabel.setPreferredSize(new Dimension(200, 40));
        taskNameLabel.setBackground(steuerung.getUiColor(UIColor.text));

        segmentPanel.add(taskNameLabel);

        return segmentPanel;
    }

    public void showDialog(boolean isEdited) {
        new TaskSegmentDialog(steuerung, appFrame, this, titel, beschreibung, isEdited);
    }

    public void createTaskSegment(TaskSegmentDialog taskSegmentDialog, boolean isEdited) {
        taskSteuerung.createTaskSegment(this, taskSegmentDialog, isEdited);
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
    
    protected MouseListener mouseClicked() {
        MouseListener ml = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    showDialog(true);
                }else if(e.getButton() == MouseEvent.BUTTON3) {
                    
                }
            }
        };
            return ml;
    }
}
