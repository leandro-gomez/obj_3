package unq.o3.meta.autodelegate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.Assert.*;

import org.junit.Test;

/**
 * 
 * @author jfernandes
 */
public class AutoDelegateTestCase {

	// *************************************
	// ** TESTS
	// *************************************
	
	/**
	 * Intercepta el add y addAll para multiplicar los elementos (funciona con Integers nada mas)
	 * @author jfernandes
	 */
	public class MultiplicaListInterceptor {
		public boolean add(List decoratee, Object e) {
			return decoratee.add(((Integer) e) * 2);
		}
	}

	@Test
	public void testInterceptaArrayListParaMultiplicarElElementoEnElAdd() {
		List<Integer> numeros = new ArrayList<Integer>();
		List<Integer> proxy = this.interceptar(List.class, numeros, new MultiplicaListInterceptor());
		
		proxy.add(2);
		
		assertFalse(numeros.isEmpty());
		assertFalse(proxy.isEmpty());
		assertEquals(4, numeros.get(0).intValue());
		assertEquals(4, proxy.get(0).intValue());
	}
	
	/**
	 * Intercepta/Decora para filtrar los negativos en add y addAll()
	 * 
	 * @author jfernandes
	 */
	public class FiltraNegativosSetInterceptor {
		public boolean add(Set<Integer> decoratee, Object e) {
			Integer entero = (Integer) e;
			if (entero < 0) {
				return true;
			}
			return decoratee.add(entero);
		}
		public boolean addAll(Set<Integer> decoratee, Collection<Integer> c) {
			boolean result = true;
			for (Integer e : c) {
				result &= this.add(decoratee, e);
			}
			return result;
		}
	}
	
	@Test
	public void testInterceptaHashSetParaFiltrarNegativosEnElAddYAddAll() {
		Set<Integer> numeros = new HashSet<Integer>();
		Set proxy = this.interceptar(Set.class, numeros, new FiltraNegativosSetInterceptor());
		
		proxy.add(2);
		
		assertFalse(numeros.isEmpty());
		assertEquals(2, numeros.iterator().next().intValue());
		
		// no agrega el negativo 
		proxy.add(-1);
		assertEquals(1, numeros.size());
		
		// probamos el addAll, solo debe agregar los 3 positivos
		proxy.addAll(Arrays.asList(10, 23, -6, 1, -20));
		assertEquals(4, numeros.size());
	}
	
	

	// *************************************
	// ** METODOS UTILES 
	// *************************************
	
	protected <T> T interceptar(Class<T> interfaz, T original, Object interceptor) {
		return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[] { interfaz }, this.crearInterceptor(original, interceptor));
	}

	protected <T> InvocationHandler crearInterceptor(T original, 	Object interceptor) {
		// ACA VA LA INSTANCIACION DE SU HANDLER !
		return new AutoDelegateInvocationHandler(original, interceptor);
	}

}
