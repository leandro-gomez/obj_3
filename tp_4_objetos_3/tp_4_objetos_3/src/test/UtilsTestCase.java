package test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import unq.o3.meta.autodelegate.Utils;

public class UtilsTestCase {

	Method mock_class_list_add;
	Class<?>[] mock_class_add_arguments;

	@Before
	public void setUp() throws SecurityException, NoSuchMethodException {
		mock_class_list_add = MockTestClass.class.getMethod("add",
				Object.class, MockTestClass.class);
	}

	@Test
	public void testCheckParametersSameTypeParameter() {

		mock_class_add_arguments = new Class<?>[2];
		mock_class_add_arguments[0] = Object.class;
		mock_class_add_arguments[1] = MockTestClass.class;
		assertTrue(Utils.checkParameters(mock_class_list_add,
				mock_class_add_arguments));
	}

	@Test
	public void testCheckParameterschildTypeParameter() {
		mock_class_add_arguments = new Class<?>[2];
		mock_class_add_arguments[0] = String.class;
		mock_class_add_arguments[1] = MockTestClass.class;
		assertTrue(Utils.checkParameters(mock_class_list_add,
				mock_class_add_arguments));
	}

	@Test
	public void testCheckParametetsUserDefinedClass() {
		mock_class_add_arguments = new Class<?>[2];
		mock_class_add_arguments[0] = MockTestClass.class;
		mock_class_add_arguments[1] = MockTestClass.class;
		assertTrue(Utils.checkParameters(mock_class_list_add,
				mock_class_add_arguments));
	}

	@Test
	public void testCheckParametetsFails() {
		mock_class_add_arguments = new Class<?>[2];
		mock_class_add_arguments[0] = MockTestClass.class;
		mock_class_add_arguments[1] = Object.class;
		assertFalse(Utils.checkParameters(mock_class_list_add,
				mock_class_add_arguments));
	}

	@Test
	public void testAnyCheckParametersTrueToAdd() {
		Method[] methods = MockTestClass.class.getDeclaredMethods();
		mock_class_add_arguments = new Class<?>[2];
		mock_class_add_arguments[0] = Object.class;
		mock_class_add_arguments[1] = MockTestClass.class;
		assertTrue(Utils.anyCheckParameters(Utils.toArryList(methods),
				mock_class_add_arguments));

	}

	@Test
	public void testAnyCheckParametersFailsToAdd() {
		Method[] methods = MockTestClass.class.getDeclaredMethods();
		mock_class_add_arguments = new Class<?>[2];
		mock_class_add_arguments[0] = MockTestClass.class;
		mock_class_add_arguments[1] = Object.class;
		assertFalse(Utils.anyCheckParameters(Utils.toArryList(methods),
				mock_class_add_arguments));
	}

	@Test
	public void testAnyCheckParametersTrueToRemove() {
		Method[] methods = MockTestClass.class.getDeclaredMethods();
		mock_class_add_arguments = new Class<?>[1];
		mock_class_add_arguments[0] = MockTestClass.class;
		assertTrue(Utils.anyCheckParameters(Utils.toArryList(methods),
				mock_class_add_arguments));

	}

	@Test
	public void testAnyCheckParametersFalseToRemove() {
		Method[] methods = MockTestClass.class.getDeclaredMethods();
		mock_class_add_arguments = new Class<?>[1];
		mock_class_add_arguments[0] = Object.class;
		assertFalse(Utils.anyCheckParameters(Utils.toArryList(methods),
				mock_class_add_arguments));

	}

	@Test
	public void testHasMethodForAdd() {
		mock_class_add_arguments = new Class<?>[2];
		mock_class_add_arguments[0] = Object.class;
		mock_class_add_arguments[1] = MockTestClass.class;
		assertTrue(Utils.hasMethod(MockTestClass.class, "add",
				mock_class_add_arguments));
	}

	@Test
	public void testHasMethodForAddFails() {
		mock_class_add_arguments = new Class<?>[2];
		mock_class_add_arguments[0] = MockTestClass.class;
		mock_class_add_arguments[1] = Object.class;
		assert (Utils.hasMethod(MockTestClass.class, "add",
				mock_class_add_arguments));
	}

	@Test
	public void testHasMethodForRemove() {
		mock_class_add_arguments = new Class<?>[1];
		mock_class_add_arguments[0] = MockTestClass.class;
		assertTrue(Utils.hasMethod(MockTestClass.class, "remove",
				mock_class_add_arguments));
	}

	@Test
	public void testHasMethodForRemoveFails() {
		mock_class_add_arguments = new Class<?>[1];
		mock_class_add_arguments[0] = Object.class;
		assertFalse(Utils.hasMethod(MockTestClass.class, "remove",
				mock_class_add_arguments));
	}

	@Test
	public void testGetMethodsForNameAdd() {
		ArrayList<Method> methods = Utils.getMethodsForName(
				MockTestClass.class, "add");
		assertTrue(methods.size() > 0);
	}

	@Test
	public void testGetMethodsForNameRevome() {
		ArrayList<Method> methods = Utils.getMethodsForName(
				MockTestClass.class, "remove");
		assertTrue(methods.size() > 0);
	}

	@Test
	public void testGetMethodsForNameUnexistentMethod() {
		ArrayList<Method> methods = Utils.getMethodsForName(
				MockTestClass.class, "unexistentMethodName");
		assertTrue(methods.size() == 0);
	}

	@Test
	public void getWhoCheckParametersOfRemove() {
		Method[] methods = MockTestClass.class.getDeclaredMethods();
		mock_class_add_arguments = new Class<?>[1];
		mock_class_add_arguments[0] = MockTestClass.class;
		Method method = Utils.getWhoCheckParameters(Utils.toArryList(methods),
				mock_class_add_arguments);
		assertTrue(method != null);
	}

	@Test
	public void getWhoCheckParametersOfAdd() {
		Method[] methods = MockTestClass.class.getDeclaredMethods();
		mock_class_add_arguments = new Class<?>[2];
		mock_class_add_arguments[1] = MockTestClass.class;
		mock_class_add_arguments[0] = Object.class;
		Method method = Utils.getWhoCheckParameters(Utils.toArryList(methods),
				mock_class_add_arguments);
		assertTrue(method != null);
	}

	@Test
	public void getWhoCheckParametersOfAddFails() {
		Method[] methods = MockTestClass.class.getDeclaredMethods();
		mock_class_add_arguments = new Class<?>[2];
		mock_class_add_arguments[0] = MockTestClass.class;
		mock_class_add_arguments[1] = Object.class;
		Method method = Utils.getWhoCheckParameters(Utils.toArryList(methods),
				mock_class_add_arguments);
		assertFalse(method != null);
	}

}
