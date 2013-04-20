package expixel.events;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class EventListeners<T> implements InvocationHandler {

	Set<T> listeners;
	T proxyObject;

	public EventListeners(Class<T> clazz) {
		this.createProxyObject(clazz);
		this.listeners = new HashSet<>();
	}

	public void addListener(T listener) {
		this.listeners.add(listener);
	}

	public void removeListener(T listener) {
		this.listeners.remove(listener);
	}

	public Set<T> getListeners() {
		return Collections.unmodifiableSet(this.listeners);
	}

	@SuppressWarnings("unchecked")
	private void createProxyObject(Class<T> clazz) {
		Class<?> proxyClass = Proxy.getProxyClass(clazz.getClassLoader(), new Class[] {clazz});
		try {
			this.proxyObject = (T) proxyClass.getConstructor(new Class[] {InvocationHandler.class}).newInstance(new Object[] {this});
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	/*
    Class proxyClass = Proxy.getProxyClass(
            Foo.class.getClassLoader(), new Class[] { Foo.class });
        Foo f = (Foo) proxyClass.
            getConstructor(new Class[] { InvocationHandler.class }).
            newInstance(new Object[] { handler });
	 */
	public T getProxyObject() {
		return this.proxyObject;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		for(T listener : this.listeners) {
			method.invoke(listener, args);
		}
		return null;
	}

}
