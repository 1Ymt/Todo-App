package Data;

import java.util.ArrayList;

public class NotizenData extends TodoData{
    private ArrayList<SegmentData> segments;

    public NotizenData() {
    }

    public ArrayList<SegmentData> getSegments() {
        return segments;
    }

    public void setSegments(ArrayList<SegmentData> segments) {
        this.segments = segments;
    }
    
}
