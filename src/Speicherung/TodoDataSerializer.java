package Speicherung;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import Data.NotizenData;
import Data.OrdnerData;
import Data.FontSegmentData;
import Data.TaskData;
import Data.TaskSegmentData;
import Data.TodoData;

public class TodoDataSerializer implements JsonSerializer<TodoData>{

    @Override
    public JsonElement serialize(TodoData src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        switch (src.getType()) {
            case Ordner:
                OrdnerData ordnerData = (OrdnerData) src;
                obj.addProperty("type", ordnerData.getType().name());
                obj.addProperty("name", ordnerData.getName());
                obj.addProperty("colorRGB", ordnerData.getColorRGB());

                JsonElement childrenOrdner = context.serialize(ordnerData.getTodoData(), new TypeToken<List<TodoData>>() {}.getType());
                obj.add("todoData", childrenOrdner);
                return obj;

            case Notizen:
                NotizenData notizenData = (NotizenData) src;
                obj.addProperty("type", notizenData.getType().name());
                obj.addProperty("name", notizenData.getName());

                JsonElement fontSegments = context.serialize(notizenData.getSegments(),new TypeToken<List<FontSegmentData>>() {}.getType());
                obj.add("fontSegment", fontSegments);
                return obj;

            case Task:
                TaskData taskData = (TaskData) src;
                obj.addProperty("type", taskData.getType().name());
                obj.addProperty("name", taskData.getName());

                JsonElement taskSegments = context.serialize(taskData.getTaskSegments(),new TypeToken<List<TaskSegmentData>>() {}.getType());
                obj.add("taskSegment", taskSegments);
                
                return obj;
            default:
                throw new IllegalArgumentException("No TodoType " + src.getType());
        }
    }
}
