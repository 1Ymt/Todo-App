package Testing;
/*
 * For the note app, use JTextPane to easily apply style to specific text positions. To apply a style, add this method to create a new style:
 * 
 *      Style style = textPane.addStyle("Custom Style", null);
 * 
 * With this you can customize the style:
 * 
 *      StyleConstants.setFontFamily(style, "Arial");
 *      StyleConstants.setFontSize(style, textPane.getFont().getSize());
 *      StyleConstants.setBold(style, true);
 *   
 * To replace the selected text position, first get the document interface from JTextPane:
 * 
 *      StyledDocument doc = textPane.getStyledDocument();
 * 
 * Then you can replace the text with a try catch method with the position of the specific text to be replaced:
 * 
 *      try {
 *          doc.setCharacterAttributes(start, end - start, style, true);
 *      } catch (Exception e) {
 *          e.printStackTrace();
 *      }
 *           
 * The start and end of the text position can be located using this:
 * 
 *      int start = textPane.getSelectionStart();
 *      int end = textPane.getSelectionEnd();
 */


import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.*;


public class Notizen extends JFrame {
    private JPanel topPanel;
    private JPanel mainPanel;
    private JButton button;
    private JButton button2;
    JTextPane textPane; // Change from JTextArea to JTextPane

    public Notizen() {
        initializeFrame();
        createPanels();
        addPanelsToFrame();
    }

    private void initializeFrame() {
        setTitle("Notes Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void createPanels() {
        createTopPanel();
        createMainPanel();
    }

    private void createTopPanel() {
        topPanel = new JPanel(new FlowLayout());
        topPanel.setPreferredSize(new Dimension(800, 50));
        topPanel.setBackground(Color.LIGHT_GRAY);

        button = new JButton("Submit");
        button.addActionListener(e -> buttonControl(button));

        button2 = new JButton("Bold");
        button2.addActionListener(e -> buttonControl(button2));

        topPanel.add(button);
        topPanel.add(button2);
    }

    private void createMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);

        JPanel textField = new JPanel();
        textField.setBackground(Color.gray);
        textField.setPreferredSize(new Dimension(500, 300));

        textPane = new JTextPane(); // Create JTextPane instead of JTextArea
        textPane.setPreferredSize(new Dimension(450, 250));
        textPane.setBackground(Color.WHITE);

        // Enable word wrap

        textField.add(textPane);
        mainPanel.add(textField);
    }

    private void buttonControl(JButton button) {
        if (button.getText().equals("Bold")) {
            int start = textPane.getSelectionStart();
            int end = textPane.getSelectionEnd();

            StyledDocument doc = textPane.getStyledDocument();
            Style style = textPane.addStyle("Custom Style", null);
            StyleConstants.setFontFamily(style, "Arial");
            StyleConstants.setFontSize(style, textPane.getFont().getSize());
            StyleConstants.setBold(style, true);
            StyleConstants.setForeground(style, Color.GREEN);

            try {
                doc.setCharacterAttributes(start, end - start, style, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (button.getText().equals("Submit")) {
            try {
                StyledDocument doc = textPane.getStyledDocument();
                String text = doc.getText(0, doc.getLength());
                
                System.out.println("=== Document Content ===");
                System.out.println(text);
                System.out.println("\n=== Document Properties ===");
                System.out.println("Length: " + doc.getLength());
                StringBuilder outputText = new StringBuilder();
                // Iterate through each character to get its style information
                for (int i = 0; i < doc.getLength(); i++) {
                    javax.swing.text.Element element = doc.getCharacterElement(i);
                    javax.swing.text.AttributeSet attrs = element.getAttributes();

                    if (attrs.getAttributeCount() > 0) {
                        outputText.append(text.charAt(i));
                    }

                    if (attrs.getAttributeCount() == 0 && !outputText.isEmpty() || i == doc.getLength() - 1) {
                        System.out.println("\nText: '" + outputText + "' with Style:");
                        System.out.println("Font Family: " + attrs.getAttribute(StyleConstants.FontFamily));
                        System.out.println("Font Size: " + attrs.getAttribute(StyleConstants.FontSize));
                        System.out.println("Bold: " + attrs.getAttribute(StyleConstants.Bold));
                        System.out.println("Italic: " + attrs.getAttribute(StyleConstants.Italic));
                        System.out.println("Underline: " + attrs.getAttribute(StyleConstants.Underline));
                        System.out.println("Foreground: " + attrs.getAttribute(StyleConstants.Foreground));
                        outputText.setLength(0);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addPanelsToFrame() {
        add(topPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Notizen app = new Notizen();
            app.setVisible(true);
        });
    }
}
