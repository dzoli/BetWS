package bettingshop.convertors;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class DateDeserializer extends JsonDeserializer<Date>{

	@Override
	public Date deserialize(JsonParser jsonParser, DeserializationContext arg1) throws IOException, JsonProcessingException {
//		DateFormat dateFormat = new SimpleDateFormat(
//	            "EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        String date = jsonParser.getText();
        Date res = new Date(Long.valueOf(date));
        try {
        	return res;
//            return dateFormat.parse(date);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

		
	}

	
}
