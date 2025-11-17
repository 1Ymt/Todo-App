package Data;

public class NotizenData extends TodoData{
    private String text;

    public NotizenData() {
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
    
}
