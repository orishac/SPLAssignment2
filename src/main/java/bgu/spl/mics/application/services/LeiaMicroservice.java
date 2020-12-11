package bgu.spl.mics.application.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.Event;
import bgu.spl.mics.Future;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.BombDestroyerEvent;
import bgu.spl.mics.application.messages.DeactivationEvent;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Diary;

/**
 * LeiaMicroservices Initialized with Attack objects, and sends them as  {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LeiaMicroservice extends MicroService {
    private Diary diary = Diary.getInstance();
	private Attack[] attacks;
	private ConcurrentLinkedQueue<AttackEvent> events;
	private ConcurrentLinkedQueue<Future> attackFutures;
	private Future deactivateEvent;


    public LeiaMicroservice(Attack[] attacks) {
        super("Leia");
		this.attacks = attacks;
    }

    @Override
    protected void initialize()  {
        subscribeBroadcast(TerminateBroadcast.class, (Leia)->terminate());
        events = new ConcurrentLinkedQueue();
        attackFutures = new ConcurrentLinkedQueue();
    	for (int i=0; i<attacks.length; i++) {
    	    AttackEvent event = new AttackEvent(attacks[i]);
    	    events.add(event);
        }
    	try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (!events.isEmpty()) {
            Future toAdd = sendEvent(events.poll());
            attackFutures.add(toAdd);
        }
        for (Future event : attackFutures) {
            event.get();
        }
        deactivateEvent = sendEvent(new DeactivationEvent());
        deactivateEvent.get();
        sendEvent(new BombDestroyerEvent());
    }

}
