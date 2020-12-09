package bgu.spl.mics;

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
	private ConcurrentHashMap<MicroService, BlockingQueue<Message>> hashMap;
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
	}

	@Override
	public void sendBroadcast(Broadcast b) {
		while (!subscriptionList.get(b.getClass()).isEmpty()) {
			hashMap.get(subscriptionList.get(b).poll()).add(b);
		}
	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		MicroService m = subscriptionList.get(e.getClass()).poll();
		subscriptionList.get(e.getClass()).add(m);
		hashMap.get(m).add(e);
		Future<T> future = new Future<>();
		futureMap.put(e, future);
		return future;
	}

	@Override
	public void register(MicroService m) {
		BlockingQueue<Message> q = new LinkedBlockingQueue<>();
		hashMap.put(m, q);

		/*
		ConcurrentLinkedQueue<Message> q = new ConcurrentLinkedQueue<>();
		hashMap.put(m, q);

		 */
	}

	@Override
	public void unregister(MicroService m) {
		hashMap.remove(m);
	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		return hashMap.get(m).take();
		/*
			while(hashMap.get(m).isEmpty()) {
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			return hashMap.get(m).poll();

		 */
	}
}
