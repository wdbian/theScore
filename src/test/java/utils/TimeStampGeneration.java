package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStampGeneration {
	private static ThreadLocal<SimpleDateFormat> localDateFomatter = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyyMMddHHmmss");
		}
	};
	
	public static String generateTimeStampString()
	{
		String result = localDateFomatter.get().format(new Date());
		return result;	
	}
}
