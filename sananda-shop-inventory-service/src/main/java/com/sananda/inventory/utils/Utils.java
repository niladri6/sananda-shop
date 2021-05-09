package com.sananda.inventory.utils;

public class Utils {
	
	public static final String DATE_TIME_PATTERN = "dd-MM-yyyy hh:mm:ss";
	
	/**
	 * This method returns true if the object is null.
	 * @param object
	 * @return true | false
	 */
	public static boolean isEmpty(Object object) {
		if (object == null) {
			return true;
		}
		return false;
	}
}
