package unq.o3.meta.autodelegate;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class Utils {

	public static boolean checkParameters(Method method1, Class<?>[] arguments) {
		Class<?>[] parameters = method1.getParameterTypes();
		if (parameters.length == arguments.length) {
			boolean equals = true;
			for (int i = 0; i < parameters.length; i++) {
				equals &= parameters[i].isAssignableFrom(arguments[i]);
			}
			return equals;
		}

		return false;
	}

	public static boolean anyCheckParameters(ArrayList<Method> methods,
			Class<?>[] arguments) {
		for (Method method : methods)
			if (checkParameters(method, arguments))
				return true;

		return false;
	}

	public static boolean hasMethod(Class<?> klass, String methodName,
			Class<?>[] typeParameters) {
		ArrayList<Method> methods = getMethodsForName(klass, methodName);
		return anyCheckParameters(methods, typeParameters);
	}

	public static ArrayList<Method> getMethodsForName(Class<?> klass,
			String name) {
		ArrayList<Method> methods = new ArrayList<Method>();
		for (Method method : klass.getDeclaredMethods()) {
			if (method.getName().equals(name))
				methods.add(method);
		}
		return methods;
	}

	public static Method getWhoCheckParameters(ArrayList<Method> methods,
			Class<?>[] arguments) {
		for (Method method : methods)
			if (checkParameters(method, arguments))
				return method;
		return null;
	}

	public static ArrayList<Method> toArryList(Object[] objects) {
		ArrayList<Method> array_objects = new ArrayList<Method>();
		for (int i = 0; i < objects.length; i++) {
			array_objects.add((Method) objects[i]);
		}
		return array_objects;
	}

	public static Class<?>[] argumentTypes(Object[] args) {
		Class<?>[] types = new Class[args.length];
		int i = 0;
		for (Object argument : args) {
			types[i++] = argument.getClass();
		}
		return types;
	}

}
