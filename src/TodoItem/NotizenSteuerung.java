package TodoItem;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;


import Data.TodoData;
import Enums.TodoType;
import Interface.TodoListController;
import Steuerung.Steuerung;
import UI.AppFrame;

public class NotizenSteuerung extends TodoItem {
    
    private Steuerung steuerung;

    private NotizenFrame notizenFrame;

    public NotizenSteuerung(Steuerung steuerung, AppFrame appFrame,  String name, TodoListController parent) {
        super(name, TodoType.Notizen, parent);
        this.steuerung = steuerung;
        
        this.notizenFrame = new NotizenFrame(this, appFrame);
    }

    @Override
    public TodoData toData() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toData'");
    }

    @Override
    public JPanel display() {
        return notizenFrame.getDisplayPanel();
    }

    @Override
    protected MouseListener mouseClicked() {
        MouseListener ml = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1) {
                

                }else if(e.getButton() == MouseEvent.BUTTON3) {
                
                }
            }
        };
            return ml;
    }

}
