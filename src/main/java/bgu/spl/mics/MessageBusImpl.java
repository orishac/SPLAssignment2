package bgu.spl.mics;


import jdk.internal.net.http.common.Pair;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {

	private static MessageBusImpl instance;
	private ConcurrentHashMap<MicroService, ConcurrentLinkedQueue<Message>> hashMap;
	private ConcurrentHashMap<Event, Future> futureMap;
	private ConcurrentHashMap<Class<? extends Message>,ConcurrentLinkedDeque> subscriptionList;

	private MessageBusImpl(){
		hashMap = new ConcurrentHashMap<>();
		futureMap = new ConcurrentHashMap<>();
		subscriptionList = new ConcurrentHashMap<>();
	}

	private static MessageBusImpl getInstance() {
		if (instance== null) {
			synchronized (MessageBusImpl.class) {
				if (instance == null) {
					instance = new MessageBusImpl();
				}
			}
		}
		return instance;
	}


	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		if (subscriptionList.get(type) == null) {
			subscriptionList.putIfAbsent(type, new ConcurrentLinkedDeque());
		}
		subscriptionList.get(type).add(m);
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		if (subscriptionList.get(type) == null) {
			subscriptionList.putIfAbsent(type, new ConcurrentLinkedDeque());
		}
		subscriptionList.get(type).add(m);
	}

	@Override @SuppressWarnings("unchecked")
	public <T> void complete(Event<T> e, T result) {
		futureMap.get(e).resolve(result);
	}

	@Override
	public void sendBroadcast(Broadcast b) {
		while (!subscriptionList.get(b).isEmpty()) {
			hashMap.get(subscriptionList.get(b).poll()).add(b);
		}
	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		hashMap.get(subscriptionList.get(e).poll()).add(e);
		Future<T> future = new Future<>();
		futureMap.put(e, future);
		return future;
	}

	@Override
	public void register(MicroService m) {
		ConcurrentLinkedQueue<Message> q = new ConcurrentLinkedQueue<>();
		hashMap.putIfAbsent(m, q);
	}

	@Override
	public void unregister(MicroService m) {
		hashMap.remove(m);
	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
			while(hashMap.get(m).isEmpty()) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return hashMap.get(m).poll();
	}
}
