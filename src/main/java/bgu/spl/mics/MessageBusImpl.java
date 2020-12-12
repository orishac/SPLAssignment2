package bgu.spl.mics;

import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.BombDestroyerEvent;
import bgu.spl.mics.application.messages.DeactivationEvent;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.passiveObjects.Attack;

import java.util.AbstractQueue;
import java.util.Collections;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {

	private static class MessageBusHolder {
		private static MessageBusImpl instance = new MessageBusImpl();
	}
	private ConcurrentHashMap<String, BlockingQueue<Message>> hashMap;
	private ConcurrentHashMap<Event, Future> futureMap;
	private ConcurrentHashMap<Class<? extends Message>,ConcurrentLinkedQueue<MicroService>> subscriptionList;


	private MessageBusImpl(){
		hashMap = new ConcurrentHashMap<>();
		futureMap = new ConcurrentHashMap<>();
		subscriptionList = new ConcurrentHashMap<>();

	}

	public static MessageBusImpl getInstance() {
		return MessageBusHolder.instance;
	}


	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		subscriptionList.putIfAbsent(type, new ConcurrentLinkedQueue());
		subscriptionList.get(type).add(m);
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		subscriptionList.putIfAbsent(type, new ConcurrentLinkedQueue());
		subscriptionList.get(type).add(m);
	}

	@Override @SuppressWarnings("unchecked")
	public <T> void complete(Event<T> e, T result) {
		futureMap.get(e).resolve(result);
		futureMap.remove(e);
	}

	@Override
	public synchronized void sendBroadcast(Broadcast b) {
		while (!subscriptionList.get(b.getClass()).isEmpty()) {
			hashMap.get(subscriptionList.get(b.getClass()).poll().getName()).add(b);
		}
	}

	
	@Override
	public synchronized <T> Future<T> sendEvent(Event<T> e) {
		String s = null;
		if (subscriptionList.containsKey(e.getClass())) {
			MicroService m = subscriptionList.get(e.getClass()).poll();
			if (m != null) {
				subscriptionList.get(e.getClass()).add(m);
				if (m.getName() != null)
					s = m.getName();
				if (hashMap.containsKey(s)) {
					hashMap.get(s).add(e);
					Future<T> future = new Future<>();
					futureMap.put(e, future);
					return future;
				}
			}
		}
		return null;
	}


	@Override
	public  void register(MicroService m) {
		BlockingQueue<Message> q = new LinkedBlockingQueue<>();
		hashMap.put(m.getName(), q);
	}

	@Override
	public synchronized void unregister(MicroService m) {
		hashMap.remove(m.getName());
		for (ConcurrentLinkedQueue c : subscriptionList.values()) {
			if (c.contains(m)) {
				c.remove(m);
			}
		}
	}

	@Override
	public  Message awaitMessage(MicroService m) throws InterruptedException {
		return hashMap.get(m.getName()).take();

	}
}
