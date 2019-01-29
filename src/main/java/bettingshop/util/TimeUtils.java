package bettingshop.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public interface TimeUtils {
	String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
	DateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN, Locale.US);
	
	
}
