package Data;

public class FontSegmentData {
    private String text;
    private int fontSize;
    private String fontFamily;
    private boolean isBold;
    private boolean isItalic;
    private boolean isUnderline;
    private boolean isStrikethrough;


    public FontSegmentData() {
    }

    public FontSegmentData(String text, int fontSize, String fontFamily, boolean isBold, boolean isItalic, boolean isUnderline, boolean isStrikethrough) {
        this.text = text;
        this.fontSize = fontSize;
        this.fontFamily = fontFamily;
        this.isBold = isBold;
        this.isItalic = isItalic;
        this.isUnderline = isUnderline;
        this.isStrikethrough = isStrikethrough;
    }

    public boolean isStrikethrough() {
        return isStrikethrough;
    }

    public void setStrikethrough(boolean strikethrough) {
        isStrikethrough = strikethrough;
    }

    public boolean isBold() {
        return isBold;
    }

    public void setBold(boolean bold) {
        isBold = bold;
    }

    public boolean isItalic() {
        return isItalic;
    }

    public void setItalic(boolean italic) {
        isItalic = italic;
    }

    public boolean isUnderline() {
        return isUnderline;
    }

    public void setUnderline(boolean underline) {
        isUnderline = underline;
    }


    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
}