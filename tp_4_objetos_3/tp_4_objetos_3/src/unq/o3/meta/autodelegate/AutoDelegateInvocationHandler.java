package unq.o3.meta.autodelegate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import unq.o3.meta.autodelegate.Utils;

public class AutoDelegateInvocationHandler implements InvocationHandler {

	private Object original;
	private Object interceptor;

	public AutoDelegateInvocationHandler(Object original, Object interceptor) {
		this.original = original;
		this.interceptor = interceptor;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] parameters)
			throws Throwable {
		Object[] interceptorParams = getFullInterceptorsParams(parameters);

		if (Utils.hasMethod(this.interceptor.getClass(), method.getName(),
				Utils.argumentTypes(interceptorParams))) {

			ArrayList<Method> methods = Utils.getMethodsForName(
					interceptor.getClass(), method.getName());

			Method interceptor_method = Utils.getWhoCheckParameters(methods,
					Utils.argumentTypes(interceptorParams));

			return interceptor_method.invoke(interceptor, interceptorParams);
		}

		return method.invoke(this.original, parameters);

	}

	public Object[] getFullInterceptorsParams(Object[] parameters) {
		int interceptorParamsLength;
		if (parameters == null) {
			interceptorParamsLength = 1;
		} else {
			interceptorParamsLength = parameters.length + 1;
		}
		Object[] interceptorParams = new Object[interceptorParamsLength];
		interceptorParams[0] = original;
		if (parameters != null) {
			for (int i = 0; i < parameters.length; i++) {
				int aux = i + 1;
				interceptorParams[aux] = parameters[i];
			}
		}

		return interceptorParams;
	}

}
