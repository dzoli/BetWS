package bettingshop.convertors;

import java.io.IOException;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class ObjectIdJsonDeserializer extends JsonDeserializer<ObjectId> {

    @Override
    public ObjectId deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return new ObjectId(p.readValueAs(String.class));
    }

}