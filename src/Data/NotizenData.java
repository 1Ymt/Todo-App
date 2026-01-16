package Data;

import java.util.ArrayList;

public class NotizenData extends TodoData{
    private ArrayList<FontSegmentData> fontSegments = new ArrayList<>();

    public NotizenData() {
    }


    public ArrayList<FontSegmentData> getSegments() {
        return fontSegments;
    }

    public void setSegments(ArrayList<FontSegmentData> segments) {
        this.fontSegments = segments;
    }
    
}
