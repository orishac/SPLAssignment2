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
	//private ConcurrentHashMap<Pair<Class<? extends Event>,MicroService>, MicroService> eventMap;
	//private ConcurrentHashMap<Pair<Class<? extends Broadcast>, MicroService>, MicroService> broadcastMap;
	private ConcurrentHashMap<Event, Future> futureMap;
	private ConcurrentLinkedDeque<Pair<Class<? extends Message>,MicroService>> subscriptionList;

	private MessageBusImpl(){
		hashMap = new ConcurrentHashMap<>();
		//eventMap = new ConcurrentHashMap<>();
		//broadcastMap = new ConcurrentHashMap<>();
		futureMap = new ConcurrentHashMap<>();
		subscriptionList = new ConcurrentLinkedDeque<>();
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
		if (hashMap.contains(m)) {
			Pair<Class<? extends Message>, MicroService> p = new Pair<>(type, m);
			subscriptionList.add(p);
		}
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		if (hashMap.contains(m)) {
			Pair<Class<? extends Message>, MicroService> p = new Pair<>(type, m);
			subscriptionList.add(p);
		}
    }

	@Override @SuppressWarnings("unchecked")
	public <T> void complete(Event<T> e, T result) {
		futureMap.get(e).resolve(result);
	}

	@Override
	public void sendBroadcast(Broadcast b) {
		for (Pair p : subscriptionList) {
			if (p.first.getClass() == b.getClass()) {
				hashMap.get(p.second).add(b);
			}
		}
	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		for (Pair p : subscriptionList) {
			if (p.first.getClass() == e.getClass()) {
				hashMap.get(p.second).add(e);
				break;
			}
		}
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
