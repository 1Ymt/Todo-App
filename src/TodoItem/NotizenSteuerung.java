package TodoItem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;

import Data.NotizenData;
import Data.SegmentData;
import Data.TodoData;
import Enums.TodoType;
import Enums.UiColor;
import Interface.TodoListController;
import Steuerung.Steuerung;
import UI.AppFrame;

public class NotizenSteuerung extends TodoItem {
    
    private final Integer[] FONTSIZE = new Integer[] { 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48, 72 };
    private final String[] TEXTFORMATTINGNAMES = { "Bold", "Italic", "Underline", "Strikethrough", "Bulleted List",
            "Add Image" };

    private Steuerung steuerung;
    private NotizenFrame notizenFrame;

    private MutableAttributeSet attributeStyle;

    public NotizenSteuerung(Steuerung steuerung, AppFrame appFrame, String name, TodoListController parent) {
        super(name, TodoType.Notizen, parent);
        this.steuerung = steuerung;
        this.notizenFrame = new NotizenFrame(steuerung,this, appFrame);
        
        StyledEditorKit kit = (StyledEditorKit) notizenFrame.getTextPaneEditorKit();
        this.attributeStyle = kit.getInputAttributes();

        setupDefaultAttributeStyle();
    }

    //TODO: Get Default from Config
    public void setupDefaultAttributeStyle() {
        String defaultFont = "Arial";
        Integer defaultFontSize = FONTSIZE[5];

        StyleConstants.setFontFamily(attributeStyle, defaultFont);
        notizenFrame.getFonts().setSelectedItem(defaultFont);

        StyleConstants.setFontSize(attributeStyle, defaultFontSize);
        notizenFrame.getFontSizes().setSelectedItem(defaultFontSize);
    }

    @Override
    public TodoData toData() {
        NotizenData data = new NotizenData();

        data.setName(getName());
        data.setType(getType());
        data.setSegments(getSegmentFromStyledDocument());

        return data;
    }
    
    private ArrayList<SegmentData> getSegmentFromStyledDocument() {
        StyledDocument doc = notizenFrame.getStyledDocument();
        Element root = doc.getDefaultRootElement();
                
        ArrayList<SegmentData> segments = new ArrayList<>();

        for (int i = 0; i < root.getElementCount(); i++) {
            Element elem = root.getElement(i);

            for (int j = 0; j < elem.getElementCount(); j++) {
                try {
                    Element run = elem.getElement(j);

                    int start = run.getStartOffset();
                    int end = run.getEndOffset();
                    String text = doc.getText(start, end - start);

                    AttributeSet attrs = run.getAttributes();

                    boolean bold = StyleConstants.isBold(attrs);
                    boolean italic = StyleConstants.isItalic(attrs);
                    boolean isStrikeThrough = StyleConstants.isStrikeThrough(attrs);
                    boolean underline = StyleConstants.isUnderline(attrs);
                    String fontFamily = StyleConstants.getFontFamily(attrs);
                    int fontSize = StyleConstants.getFontSize(attrs);

                    SegmentData segment = new SegmentData(text, fontSize, fontFamily, bold, italic, underline, isStrikeThrough);

                    segments.add(segment);

                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        }
        return segments;
    }

    @Override
    public JPanel display() {
        return notizenFrame.getDisplayPanel();
    }

    public void updateFont(JComboBox<String> comboBoxFont) {
        String newFont = getFontNames()[comboBoxFont.getSelectedIndex()];

        comboBoxFont.setFont(new Font(newFont, Font.PLAIN, 15));
        StyleConstants.setFontFamily(attributeStyle, newFont);
        comboBoxFont.setFocusable(false);
    }

    public void updateFontSize(JComboBox<Integer> comboBoxFontSize) {
        if (!(comboBoxFontSize.getSelectedItem() instanceof Integer)) {
            JOptionPane.showMessageDialog(notizenFrame.getDisplayPanel(),
                    "Bitte geben Sie eine gültige Schriftgröße ein.", "Fehler", JOptionPane.ERROR_MESSAGE);
            comboBoxFontSize.setSelectedItem(FONTSIZE[5]);
            return;
        }

        int fontSize = Integer.parseInt(comboBoxFontSize.getSelectedItem().toString());
        StyleConstants.setFontSize(attributeStyle, fontSize);
        System.out.println(fontSize);
    }

    public void textFormattingButtonsSteuerung(JToggleButton[] buttons, JToggleButton button, JTextPane textPane) {
        int start = textPane.getSelectionStart();
        int end = textPane.getSelectionEnd();

        switch (button.getName()) {
            case "Bold":
                StyleConstants.setBold(attributeStyle, button.isSelected());
                break;
            case "Italic":
                StyleConstants.setItalic(attributeStyle, button.isSelected());
                break;
            case "Underline":
                StyleConstants.setUnderline(attributeStyle, button.isSelected());
                break;
            case "Strikethrough":
                StyleConstants.setStrikeThrough(attributeStyle, button.isSelected());
                break;
            case "Bulleted List":
                System.out.println("Bulleted List: " + button.isSelected());
                getSegmentFromStyledDocument();
                break;
            case "Add Image":
                //TODO: Add Line Uneditable for Image
                if (!button.isSelected()) {
                    return;
                }
                StyledDocument doc = textPane.getStyledDocument();

                try {
                    StyledEditorKit kit = (StyledEditorKit) textPane.getEditorKit();
                    MutableAttributeSet currentAttr = new SimpleAttributeSet(kit.getInputAttributes());

                    doc.insertString(textPane.getCaretPosition(), "\n", attributeStyle);
                    
                    //Test File
                    String fileName = "C:\\Users\\Michael\\Downloads\\b73d4d6fb050e5e455ce03901a8ce2b6.jpg";

                    ImageIcon ordner = new ImageIcon(fileName);
                    Image image = ordner.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(image);

                    textPane.insertIcon(icon);
                    doc.insertString(textPane.getCaretPosition(), "\n", currentAttr);
                    textPane.setCharacterAttributes(currentAttr, false);

                    
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
                System.out.println("Add Image: " + button.isSelected());
                button.setSelected(false);
                break;
            default:
                break;
        }
        if (start != end) {
            StyledDocument doc = textPane.getStyledDocument();
            doc.setCharacterAttributes(start, end - start, attributeStyle, false);
        }

        textPane.requestFocusInWindow();
    }
    
    public void addImageButton(JButton button) {

    }

    //TODO: Add a feature to move caret to the next line if the word/sentence is too long for the current line.
    public void carretListener(JTextPane textPane, JToggleButton[] button) {
        StyledDocument doc = textPane.getStyledDocument();

        int caretPos = Math.clamp(textPane.getCaretPosition() - 1, 0, doc.getLength());

        Element root = doc.getCharacterElement(caretPos);
        int start = root.getStartOffset();
        int end = root.getEndOffset();

        AttributeSet attrs = root.getAttributes();

        for (JToggleButton jToggleButton : button) {
            switch (jToggleButton.getName()) {
                case "Bold":
                    jToggleButton.setSelected(StyleConstants.isBold(attrs));
                    break;
                case "Italic":
                    jToggleButton.setSelected(StyleConstants.isItalic(attrs));
                    break;
                case "Underline":
                    jToggleButton.setSelected(StyleConstants.isUnderline(attrs));
                    break;
                case "Strikethrough":
                    jToggleButton.setSelected(StyleConstants.isStrikeThrough(attrs));
                    break;
                default:
                    break;
            }
        }

        boolean isIcon = StyleConstants.getIcon(attrs) != null;

        //Testing
        // boolean bold = StyleConstants.isBold(attrs);
        // boolean italic = StyleConstants.isItalic(attrs);
        // boolean underline = StyleConstants.isUnderline(attrs);
        // int fontSize = StyleConstants.getFontSize(attrs);
        // String fontFamily = StyleConstants.getFontFamily(attrs);
        // boolean isComponent = StyleConstants.getComponent(attrs) != null;

        // try {
        //     System.out.println("                                             ");
        //     System.out.println("Start: " + start + "; End: " + end + "; CaretPos: " + caretPos + ";");
        //     System.out.println("Doc length: " + doc.getLength());
        //     System.out.println("-----------------------------------");
        //     System.out.println("Font: " + fontFamily + "; FontSize: " + fontSize + ";");
        //     System.out.println("FontSize: " + fontSize + ";");
        //     System.out.println("IsComponent: " + isComponent + ";");
        //     System.out.println("IsIcon: " + isIcon + ";");
        //     System.out.println("Text: " + doc.getText(start, end - start));
        //     System.out.println("Bold: " + bold + "; Italic: " + italic + "; Underline: " + underline + ";");
        // } catch (BadLocationException e) {
        //     e.printStackTrace();
        // }
    }
    
    public void setSegmentDataToStyledDocument(ArrayList<SegmentData> segments) {
        StyledDocument doc = notizenFrame.getStyledDocument();
        try {
            doc.remove(0, doc.getLength());

            for (SegmentData segment : segments) {
                SimpleAttributeSet attrs = new SimpleAttributeSet();

                StyleConstants.setBold(attrs, segment.isBold());
                StyleConstants.setItalic(attrs, segment.isItalic());
                StyleConstants.setUnderline(attrs, segment.isUnderline());
                StyleConstants.setStrikeThrough(attrs, segment.isStrikethrough());
                StyleConstants.setFontFamily(attrs, segment.getFontFamily());
                StyleConstants.setFontSize(attrs, segment.getFontSize());

                doc.insertString(doc.getLength(), segment.getText(), attrs);
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public String[] getFontNames() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font[] allFonts = ge.getAllFonts();
        String[] fontNames = new String[allFonts.length];
        for (int i = 0; i < allFonts.length; i++) {
            fontNames[i] = allFonts[i].getFontName();
        }
        return fontNames;
    }

    public Integer[] getFontSizes() {
        return FONTSIZE;
    }

    public String[] getTextFormattingNames() {
        return TEXTFORMATTINGNAMES;
    }

    @Override
    protected MouseListener mouseClicked() {
        MouseListener ml = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1) {
                    steuerung.nextMenuPanel(notizenFrame.notizMenuPanel());
                }else if(e.getButton() == MouseEvent.BUTTON3) {
                
                }
            }
        };
        return ml;
    }
}
