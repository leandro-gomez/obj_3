package utils;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @author phm
 */
public class Utils {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static ArrayList<String> toArrayList(String[] args) {
		ArrayList<String> strings = new ArrayList<String>();
		for (String string : args) {
			strings.add(string);
		}
		return strings;
	}

	public static void prettyPrint(String color, ArrayList<String> messages,
			boolean println) {
		System.out.print(color);
		for (String string : messages) {
			if (println) {
				System.out.println(string);
			} else {
				System.out.print(string);
			}

		}
		System.out.print(ANSI_RESET);
	}

	public static void prettyPrint(String color, String message, boolean println) {
		ArrayList<String> messages = new ArrayList<String>();
		messages.add(message);
		prettyPrint(color, messages, println);
	}

	public static void prettyPrint(String color, ArrayList<String> messages) {
		prettyPrint(color, messages, true);
	}

	public static void prettyPrint(String color, String message) {
		prettyPrint(color, message, true);
	}

	public static Object getField(Object thiz, String fieldName) {
		try {
			// TODO: it only works for non-inherited fields
			Field field = thiz.getClass().getDeclaredField(fieldName);
			if (!(field.isAccessible())) {
				field.setAccessible(true);
			}
			return field.get(thiz);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(
					"Error while reading current field value: "
							+ thiz.getClass() + "." + fieldName, e);
		} catch (SecurityException e) {
			throw new RuntimeException(
					"Error while reading current field value: "
							+ thiz.getClass() + "." + fieldName, e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(
					"Error while reading current field value: "
							+ thiz.getClass() + "." + fieldName, e);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(
					"Error while reading current field value: "
							+ thiz.getClass() + "." + fieldName, e);
		}
	}

}
