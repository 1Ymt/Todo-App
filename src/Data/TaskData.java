package Data;

import java.util.ArrayList;

public class TaskData extends TodoData {
    private ArrayList<TaskSegmentData> taskSegments = new ArrayList<>();

    public TaskData() {
    }

    public ArrayList<TaskSegmentData> getTaskSegments() {
        return taskSegments;
    }

    public void addTaskSegmentData(TaskSegmentData taskSegmentData) {
        this.taskSegments.add(taskSegmentData);
    }
}
