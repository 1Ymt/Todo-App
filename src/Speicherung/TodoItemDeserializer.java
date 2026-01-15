package Speicherung;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import Data.TodoData;
import Enums.TodoType;

public class TodoItemDeserializer implements JsonDeserializer<TodoData> {
    private Map<TodoType, Class<? extends TodoData>> dataTypeRegistry;

    TodoItemDeserializer() {
        dataTypeRegistry = new HashMap<>();
    }

    public void registerDataType(TodoType dataTypes, Class<? extends TodoData> dataClass) {
        dataTypeRegistry.put(dataTypes, dataClass);
    }

    @Override
    public TodoData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject dataObject = json.getAsJsonObject();
        
        //Bekommt TodoType von Json.
        JsonElement dataTypeElement = dataObject.get("type");

        //Bekommt die jeweilige Klasse bezogen auf des TodoType im dataTypeRegistry.
        Class<? extends TodoData> dataClass = dataTypeRegistry.get(TodoType.valueOf(dataTypeElement.getAsString()));

        //Return die Daten des Json in den jeweils richtige Klasse zurück
        return context.deserialize(dataObject, dataClass);
    }

}
