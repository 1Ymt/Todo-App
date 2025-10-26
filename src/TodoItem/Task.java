package TodoItem;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import Data.TodoData;
import Interface.TodoItemTask;
import Steuerung.Steuerung;
import UI.AppFrame;

public class Task extends TodoItem{

    private Steuerung steuerung;
    private AppFrame appFrame;

    public Task(String name, String type, TodoItemTask parent, Steuerung steuerung, AppFrame appFrame) {
        super(name, type, parent);
        
    }

    @Override
    public JPanel display() {
        JPanel taskPanel = new JPanel();
        taskPanel.setName(this.getName());
        taskPanel.setLayout(new FlowLayout());
        taskPanel.setMaximumSize(new Dimension(500, 60));
        taskPanel.setBackground(new Color(95, 111, 181));
        taskPanel.addMouseListener(mouseClicked());
        taskPanel.setToolTipText(this.getName());
        JPanel taskZeichen = new JPanel(new BorderLayout());
        taskZeichen.setPreferredSize(new Dimension(45,45));
        taskZeichen.setOpaque(true);

        //JLabel taskIcon = new JLabel(steuerung.setOrdnerIcon(farbe, 45));

        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setOpaque(false);

        JLabel taskName = new JLabel(this.getName());
        taskName.setFont(new Font("ARIAL_BOLD", Font.BOLD, 15));
        taskName.setPreferredSize(new Dimension(200,40));

        taskZeichen.setBackground(Color.WHITE);

        wrapperPanel.add(taskName);

        taskPanel.add(taskZeichen);
        taskPanel.add(wrapperPanel);

        return taskPanel;
    }

    /*
     * Panels 
     */

     public JPanel taskMenuListPanel() {
        JPanel backgroundListPanel = new JPanel();
        backgroundListPanel.setLayout(new BorderLayout());
        
        backgroundListPanel.add(createMenuListPanel(), BorderLayout.CENTER);
        backgroundListPanel.add(createTopMenuPanel(), BorderLayout.NORTH);
        backgroundListPanel.add(createSidePanel(), BorderLayout.WEST);
        backgroundListPanel.add(createSidePanel(), BorderLayout.EAST);

        return backgroundListPanel;
    }

    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(Color.WHITE);
        sidePanel.setPreferredSize(new Dimension(50, appFrame.getSize().height));
        return sidePanel;
    }

    private JPanel createMenuListPanel() {
        JPanel menuList = new JPanel();
        menuList.setName("Tasklist");
        menuList.setBackground(new Color(110, 128, 210));
        menuList.setLayout(new BoxLayout(menuList, BoxLayout.Y_AXIS));
        return menuList;
    }

    private JPanel createTopMenuPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(appFrame.getSize().width, 90));
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        
        topPanel.add(createMenuNamePanel());
        topPanel.add(createTaskItemButtonPanel());

        return topPanel;
    }

    private JPanel createMenuNamePanel() {
        JPanel menuNamePanel = new JPanel(new BorderLayout());
        menuNamePanel.setPreferredSize(new Dimension(appFrame.getSize().width, 45));

        JLabel menuName = new JLabel(this.getName());
        menuName.setFont(new Font("ARIAL_BOLD", Font.BOLD, 20));

        JPanel wrapperLabel = new JPanel();
        wrapperLabel.setPreferredSize(menuName.getPreferredSize());
        wrapperLabel.setBackground(Color.WHITE);
        wrapperLabel.add(menuName);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(30,30));
        backButton.addActionListener(e -> steuerung.previousMenuPanel(taskMenuListPanel()));

        JPanel wrapperButton = new JPanel(new GridBagLayout());
        wrapperButton.setPreferredSize(new Dimension(45, 45));
        wrapperButton.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        wrapperButton.add(backButton, gbc);

        JPanel balancePanel = new JPanel();
        balancePanel.setPreferredSize(wrapperButton.getPreferredSize());
        balancePanel.setBackground(Color.WHITE);

        menuNamePanel.add(wrapperButton, BorderLayout.WEST);
        menuNamePanel.add(balancePanel, BorderLayout.EAST);
        menuNamePanel.add(wrapperLabel, BorderLayout.CENTER);
        
        return menuNamePanel;
    }

    private JPanel createTaskItemButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setPreferredSize(new Dimension(appFrame.getSize().width, 45));

        JButton todoItemButton = new JButton("Neu");
        todoItemButton.addActionListener(e -> createNeueTaskItem());

        JPanel wrapperButton = new JPanel(new BorderLayout());
        wrapperButton.setPreferredSize(new Dimension(200, 40));
        wrapperButton.add(todoItemButton, BorderLayout.CENTER);

        buttonPanel.add(wrapperButton);

        return buttonPanel;
    }

    /*
     * Steuerung
     */

    private void createNeueTaskItem() {
        System.out.println("Neue Task");
    }

    protected MouseListener mouseClicked() {
        MouseListener ml = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1) {
                    steuerung.nextMenuPanel(taskMenuListPanel());

                }else if(e.getButton() == MouseEvent.BUTTON3) {
                    //steuerung.changeOrdnerProperties(ordner);
                }
            }
        };
        return ml;
    }

    @Override
    public TodoData toData() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toData'");
    }

}
