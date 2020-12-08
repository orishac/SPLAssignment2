package bgu.spl.mics.application.services;

import java.util.ArrayList;
import java.util.List;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.Event;
import bgu.spl.mics.Future;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.BombDestroyerEvent;
import bgu.spl.mics.application.messages.DeactivationEvent;
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
    private Diary diary;
	private Attack[] attacks;
	private AttackEvent[] events;
	private Future attackFutures[];
	private Future deactivateEvent;
    private Class<? extends Broadcast> TerminateBroadcast;

    public LeiaMicroservice(Attack[] attacks) {
        super("Leia");
		this.attacks = attacks;
    }

    @Override
    protected void initialize()  {
        diary = Diary.getInstance();
        events = new AttackEvent[attacks.length];
        attackFutures = new Future[attacks.length];
    	for (int i=0; i<attacks.length; i++) {
    	    AttackEvent event = new AttackEvent(attacks[i]);
    	    events[i] = event;
        }
        for (int i=0; i<events.length; i++) {
            Future toAdd = new Future<>();
            toAdd = sendEvent(events[i]);
        }
        for (Future event : attackFutures) {
            event.get();
        }
        deactivateEvent = sendEvent(new DeactivationEvent());
        deactivateEvent.get();
        sendEvent(new BombDestroyerEvent());
        subscribeBroadcast(TerminateBroadcast, (Leia)->terminate());
    }

    private void writeDiary() {
        diary.setLeiaTerminate(System.currentTimeMillis());
    }


}
