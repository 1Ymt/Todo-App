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
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;

import java.awt.*;


public class Notizen extends JFrame {
    private JPanel topPanel;
    private JPanel mainPanel;
    private JButton button;
    private JButton button2;
    JTextPane textPane; // Change from JTextArea to JTextPane
    //private Style style;
    MutableAttributeSet attrs;

    public Notizen() {
        initializeFrame();
        createPanels();
        addPanelsToFrame();
        //this.style = textPane.addStyle("Normal", null);
        StyledEditorKit kit = (StyledEditorKit) textPane.getEditorKit();
        attrs = kit.getInputAttributes();
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

        JToggleButton button3 = new JToggleButton("Italic");
        button3.addActionListener(e -> italicStyle(button3));

        topPanel.add(button);
        topPanel.add(button2);
        topPanel.add(button3);
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
        textPane.setText("Hello, name Michael Waldshut");

        textPane.addCaretListener(e -> {
            try {
                carretListener(textPane);
            } catch (BadLocationException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });

        // Enable word wrap

        textField.add(textPane);
        mainPanel.add(textField);
    }

    private void carretListener(JTextPane textPane) throws BadLocationException {
        int dot = textPane.getCaret().getDot();
        int line = getLineOfOffset(textPane, dot);
        int positionInLine = dot - getLineStartOffset(textPane, line);
        System.out.println(positionInLine);
    }

    static int getLineOfOffset(JTextComponent comp, int offset) throws BadLocationException {
        Document doc = comp.getDocument();
        if (offset < 0) {
            throw new BadLocationException("Can't translate offset to line", -1);
        } else if (offset > doc.getLength()) {
            throw new BadLocationException("Can't translate offset to line", doc.getLength() + 1);
        } else {
            Element map = doc.getDefaultRootElement();
            return map.getElementIndex(offset);
        }
    }

    static int getLineStartOffset(JTextComponent comp, int line) throws BadLocationException {
        Element map = comp.getDocument().getDefaultRootElement();
        if (line < 0) {
            throw new BadLocationException("Negative line", -1);
        } else if (line >= map.getElementCount()) {
            throw new BadLocationException("No such line", comp.getDocument().getLength() + 1);
        } else {
            Element lineElem = map.getElement(line);
            return lineElem.getStartOffset();
        }
    }
    private void italicStyle(JToggleButton button) {
        StyledDocument doc = textPane.getStyledDocument();
        if (button.isSelected()) {
            
            StyleConstants.setItalic(attrs, true);
            textPane.setCharacterAttributes(attrs, false);
            
        } else {
            StyleConstants.setItalic(attrs, false);
            textPane.setCharacterAttributes(attrs, false);
        }
        
        // AttributeSet attr = doc.getCharacterElement(textPane.getCaretPosition()).getAttributes();
        // System.out.println("Attr is Italic: " + StyleConstants.isItalic(attr));
        // if (StyleConstants.isItalic(attr)) {
        //     button.setSelected(true);
        // } else {
        //     button.setSelected(false);
        // }
        
    }

    private void buttonControl(JButton button) {
        if (button.getText().equals("Bold")) {
            int start = textPane.getSelectionStart();
            int end = textPane.getSelectionEnd();

            StyledDocument doc = textPane.getStyledDocument();
            //Style style = textPane.addStyle("Custom Style", null);
            StyleConstants.setFontFamily(attrs, "Arial");
            StyleConstants.setFontSize(attrs, textPane.getFont().getSize());
            StyleConstants.setBold(attrs, true);
            //StyleConstants.setForeground(style, Color.GREEN);

            try {
                doc.setCharacterAttributes(start, end - start, attrs, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (button.getText().equals("Submit")) {
            try {
                // StyledDocument doc = textPane.getStyledDocument();
                // String text = doc.getText(0, doc.getLength());

                // System.out.println("=== Document Content ===");
                // System.out.println(text);
                // System.out.println("\n=== Document Properties ===");
                // System.out.println("Length: " + doc.getLength());
                // StringBuilder outputText = new StringBuilder();
                // // Iterate through each character to get its style information
                // for (int i = 0; i < doc.getLength(); i++) {
                //     Element element = doc.getCharacterElement(i);
                //     AttributeSet attrs = element.getAttributes();

                //     if (attrs.getAttributeCount() > 0) {
                //         outputText.append(text.charAt(i));
                //         System.out.println(attrs.getAttributeCount());
                //     }

                //     if (attrs.getAttributeCount() == 0 && !outputText.isEmpty() || i == doc.getLength() - 1) {
                //         System.out.println("\nText: '" + outputText + "' with Style:");
                //         System.out.println("Font Family: " + attrs.getAttribute(StyleConstants.FontFamily));
                //         System.out.println("Font Size: " + attrs.getAttribute(StyleConstants.FontSize));
                //         System.out.println("Bold: " + attrs.getAttribute(StyleConstants.Bold));
                //         System.out.println("Italic: " + attrs.getAttribute(StyleConstants.Italic));
                //         System.out.println("Underline: " + attrs.getAttribute(StyleConstants.Underline));
                //         System.out.println("Foreground: " + attrs.getAttribute(StyleConstants.Foreground));
                //         outputText.setLength(0);
                //     }
                // }

                StyledDocument doc = textPane.getStyledDocument();
                Element root = doc.getDefaultRootElement();
                

                for (int i = 0; i < root.getElementCount(); i++) {
                    Element elem = root.getElement(i);

                    for (int j = 0; j < elem.getElementCount(); j++) {
                        Element run = elem.getElement(j);

                        int start = run.getStartOffset();
                        int end = run.getEndOffset();
                        String text = doc.getText(start, end - start);
                        System.out.println("i: " + i + " text: " + text);
                    }
                    // AttributeSet attrs = elem.getAttributes();

                    // boolean bold = StyleConstants.isBold(attrs);
                    // boolean italic = StyleConstants.isItalic(attrs);
                    // boolean underline = StyleConstants.isUnderline(attrs);
                    // Color color = StyleConstants.getForeground(attrs);

                    // System.out.println("Segment: " + text);
                    // System.out.println("  Bold: " + bold);
                    // System.out.println("  Italic: " + italic);
                    // System.out.println("  Underline: " + underline);
                    // System.out.println("  Color: " + color);
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
