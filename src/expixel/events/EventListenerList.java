package expixel.events;

import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

public class EventListenerList {
	Map<Class<? extends EventListener>, EventListeners<? extends EventListener>> eventListMap;

	public EventListenerList() {
		this.eventListMap = new HashMap<>();
	}

	private <T extends EventListener> EventListeners<T> createList( Class<T> clazz ) {
		if(!this.classListExists(clazz)) {
			EventListeners<T> listenerList = new EventListeners<T>(clazz);
			this.eventListMap.put(clazz, listenerList);
			return listenerList;
		} else {
			return this.getListenerList(clazz);
		}
	}

	private boolean classListExists( Class<? extends EventListener> clazz ) {
		return this.eventListMap.containsKey(clazz);
	}

	@SuppressWarnings("unchecked")
	public <T extends EventListener> EventListeners<T> getListenerList(Class<T> clazz) {
		if(this.classListExists(clazz)) {
			return (EventListeners<T>) this.eventListMap.get(clazz);
		} else {
			return this.createList(clazz);
		}
	}

	public <T extends EventListener> T getProxy(Class<T> clazz) {
		return this.getListenerList(clazz).getProxyObject();
	}

	public <T extends EventListener> void addListener(Class<T> clazz, T listener) {
		this.getListenerList(clazz).addListener(listener);
	}

	public <T extends EventListener> void removeListener(Class<T> clazz, T listener) {
		this.getListenerList(clazz).removeListener(listener);
	}
}
