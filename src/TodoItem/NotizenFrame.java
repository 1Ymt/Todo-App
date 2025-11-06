package TodoItem;

import java.awt.*;
import javax.swing.*;

import UI.AppFrame;

public class NotizenFrame {

    private NotizenSteuerung notizenSteuerung;
    private AppFrame appFrame;

    public NotizenFrame(NotizenSteuerung notizenSteuerung, AppFrame appFrame) {
        this.appFrame = appFrame;
        this.notizenSteuerung = notizenSteuerung;
    }

    public JPanel getDisplayPanel() {
        JPanel notizenPanel = new JPanel();
        notizenPanel.setLayout(new FlowLayout());
        notizenPanel.setMaximumSize(new Dimension(500, 60));
        notizenPanel.setBackground(new Color(95, 111, 181));
        notizenPanel.addMouseListener(notizenSteuerung.mouseClicked());
        notizenPanel.setToolTipText(notizenSteuerung.getName());
        JPanel ordnerZeichen = new JPanel(new BorderLayout());
        ordnerZeichen.setPreferredSize(new Dimension(45, 45));
        ordnerZeichen.setOpaque(false);

        JLabel notizenIcon = new JLabel("Notizen");

        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setOpaque(false);

        JLabel notizenName = new JLabel(notizenSteuerung.getName());
        notizenName.setFont(new Font("ARIAL_BOLD", Font.BOLD, 15));
        notizenName.setPreferredSize(new Dimension(200, 40));

        ordnerZeichen.add(notizenIcon, BorderLayout.CENTER);

        wrapperPanel.add(notizenName);

        notizenPanel.add(ordnerZeichen);
        notizenPanel.add(wrapperPanel);

        return notizenPanel;
    }
    
    public JPanel notizMenuPanel() {
        return null;
    }
}
