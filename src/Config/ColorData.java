package Config;

import Enums.Theme;

public class ColorData {
    private Theme theme;

    private int bgDark;
    private int bg;
    private int bgLight;
    private int text;
    private int textMuted;
    private int highlight;
    private int border;
    private int borderMuted;
    private int primary;
    private int secondary;
    private int danger;
    private int warning;
    private int success;
    private int info;

    public ColorData() {
    }

    public Theme getTheme() { return theme;}

    public void setTheme(Theme theme) {  this.theme = theme; }

    public int getBgDark()      { return bgDark; }
    public int getBg()          { return bg; }
    public int getBgLight()     { return bgLight; }
    public int getText()        { return text; }
    public int getTextMuted()   { return textMuted; }
    public int getHighlight()   { return highlight; }
    public int getBorder()      { return border; }
    public int getBorderMuted() { return borderMuted; }
    public int getPrimary()     { return primary; }
    public int getSecondary()   { return secondary; }
    public int getDanger()      { return danger; }
    public int getWarning()     { return warning; }
    public int getSuccess()     { return success; }
    public int getInfo()        { return info; }

    public void setBgDark(int bgDark)            { this.bgDark = bgDark; }
    public void setBg(int bg)                    { this.bg = bg; }
    public void setBgLight(int bgLight)          { this.bgLight = bgLight; }
    public void setText(int text)                { this.text = text; }
    public void setTextMuted(int textMuted)      { this.textMuted = textMuted; }
    public void setHighlight(int highlight)      { this.highlight = highlight; }
    public void setBorder(int border)            { this.border = border; }
    public void setBorderMuted(int borderMuted)  { this.borderMuted = borderMuted; }
    public void setPrimary(int primary)          { this.primary = primary; }
    public void setSecondary(int secondary)      { this.secondary = secondary; }
    public void setDanger(int danger)            { this.danger = danger; }
    public void setWarning(int warning)          { this.warning = warning; }
    public void setSuccess(int success)          { this.success = success; }
    public void setInfo(int info)                { this.info = info; }
}
