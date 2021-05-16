package com.sananda.inventory.utils;

import java.util.List;
import java.util.Map;

import com.sananda.inventory.exception.InvalidDataException;
import com.sananda.inventory.response.MenuItems;

public class Utils {

	public static final String DATE_TIME_PATTERN = "dd-MM-yyyy hh:mm:ss";

	/**
	 * This method returns true if the object is null.
	 * 
	 * @param object
	 * @return true | false
	 */
	public static boolean isEmpty(Object object) {
		if (object == null) {
			return true;
		}
		return false;
	}

	// String.format("Can not found with Id = %s", productId)); boolean
	public static boolean isKeyValueNull(Map<?,?> map, String keyName) throws InvalidDataException {
		if (!map.containsKey(keyName) || (map.get(keyName) == null)) {
			//throw new InvalidDataException(String.format("%s is required", keyName));
			return true;
		} else return false;
	}
	
	
	public static void menuItemSort(List<MenuItems> items) {
		items.sort((n1, n2) -> n1.getName().compareToIgnoreCase(n2.getName()));
	}
}
