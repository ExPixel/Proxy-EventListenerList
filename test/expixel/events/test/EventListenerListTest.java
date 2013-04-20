package expixel.events.test;

import expixel.events.EventListenerList;

public class EventListenerListTest {
	public static void main(String[] args) {
		EventListenerList listenerList = new EventListenerList();

		listenerList.addListener(TestEventListener.class, new TestEventListener() {
			@Override
			public void test(String text) {
				System.out.println("LISTENER 1 FIRED: " + text);
			}
		});

		TestEventListener evtListener2 = new TestEventListener() {
			@Override
			public void test(String text) {
				System.out.println("LISTENER 2 FIRED: " + text);
			}
		};
		listenerList.addListener(TestEventListener.class, evtListener2);

		listenerList.addListener(TestEventListener.class, new TestEventListener() {
			@Override
			public void test(String text) {
				System.out.println("LISTENER 3 FIRED: " + text);
			}
		});
		listenerList.addListener(TestEventListener.class, new TestEventListener() {
			@Override
			public void test(String text) {
				System.out.println("LISTENER 4 FIRED: " + text);
			}
		});

		listenerList.getProxy(TestEventListener.class).test("Testin' Text.");
		listenerList.removeListener(TestEventListener.class, evtListener2);
		System.out.println("--Second Test--");
		listenerList.getProxy(TestEventListener.class).test("Listener #2 will not fire.");
	}
}
