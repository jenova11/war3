package com.kyrion.utils;

import java.util.ArrayList;

public class StringsUtils {
	
	public static String convertToString(ArrayList<String> numbers) {
	StringBuilder builder = new StringBuilder();
		// Append all Integers in StringBuilder to the StringBuilder.

		for (String number : numbers) {
		    builder.append(number);
		    System.out.println("number=>"+number);
		    builder.append(",");
		}
		System.out.println("Builder=>"+builder.toString());
		System.out.println("jer passe");
		// Remove last delimiter with setLength.
		builder.setLength(builder.length() - 1);
		System.out.println("jer passe=>"+builder.toString());
		return builder.toString();
	}
}
