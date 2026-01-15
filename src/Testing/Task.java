package Testing;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Task extends JFrame {

    private JPanel centerPanel;
    private int panelCounter = 1;
    private Point buttonOffsetLocation = new Point();
    private JPanel currentPanel = new JPanel();
    JPanel centerContainer;

    public Task() {
        setupFrame();
        add(createTopPanel(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
        
        
    }

    // --- BASIC FRAME SETUP ---
    private void setupFrame() {
        setTitle("Panel Creator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    // --- TOP PANEL (With Button) ---
    private JPanel createTopPanel() {
        JPanel top = new JPanel();

        JButton addButton = new JButton("Add Panel");
        addButton.addActionListener(e -> addPanelToCenter());

        top.add(addButton);
        return top;
    }

    // --- CENTER PANEL (Scrollable) ---
    private JScrollPane createCenterPanel() {
        this.centerContainer = new JPanel(new GridLayout(1, 2));
        JFrame fram = this;
        this.addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint(); // coordinates inside 'parent'
                Component c = centerContainer.getComponentAt(p);
                if (c instanceof JPanel) {
                    if (!currentPanel.equals(c)) {
                        System.out.println(c.getName());
                        currentPanel = (JPanel) c;
                    }
                }
            }
        });
        // Left side panel
        JPanel leftPanel = new JPanel();
        leftPanel.setName("Left");
        leftPanel.setBorder(BorderFactory.createTitledBorder("Left Side"));

        // Right side panel
        JPanel rightPanel = new JPanel();
        rightPanel.setName("Right");
        rightPanel.setBorder(BorderFactory.createTitledBorder("Right Side"));

        // Save them somewhere if you want to add panels later
        this.centerPanel = leftPanel;
        centerContainer.add(leftPanel);
        centerContainer.add(rightPanel);

        return new JScrollPane(centerContainer);
    }

    // --- BOTTOM PANEL ---
    private JPanel createBottomPanel() {
        JPanel bottom = new JPanel();
        bottom.add(new JLabel("Bottom panel"));
        return bottom;
    }

    // --- CREATES AND ADDS A PANEL TO THE CENTER ---
    private void addPanelToCenter() {
        JPanel newPanel = new JPanel();
        newPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        newPanel.add(new JLabel("Panel #" + panelCounter));
        newPanel.setPreferredSize(new Dimension(100, 100));
        newPanel.setBackground(Color.YELLOW);

        newPanel.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                buttonOffsetLocation = new Point();

                if (currentPanel == null) {
                    return;
                }
                Container parent = newPanel.getParent();
                parent.remove(newPanel);
                currentPanel.add(newPanel);

                System.out.println(newPanel.getParent());

                parent.revalidate();
                parent.repaint();
                currentPanel.revalidate();
                currentPanel.repaint();
    
             }
        });
        newPanel.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                if (buttonOffsetLocation.equals(new Point())) {
                    buttonOffsetLocation = e.getPoint();
                    return;
                }
                if (!newPanel.getParent().equals(centerContainer)) {
                    Container parnet = newPanel.getParent();
                    parnet.remove(newPanel);
                    centerContainer.add(newPanel);
                    setComponentZOrder(newPanel, 0);
                }
                
                

                Point currentPoint = e.getPoint();
                Point panelLocation = newPanel.getLocation();

                Point newPoint = new Point(buttonOffsetLocation.x - currentPoint.x,
                        buttonOffsetLocation.y - currentPoint.y);
                
                newPanel.setLocation(panelLocation.x - newPoint.x, panelLocation.y - newPoint.y);              
                

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                // TODO Auto-generated method stub
                //throw new UnsupportedOperationException("Unimplemented method 'mouseMoved'");
            }
            
        });
        panelCounter++;

        centerPanel.add(newPanel);
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Task().setVisible(true));
    }
}
