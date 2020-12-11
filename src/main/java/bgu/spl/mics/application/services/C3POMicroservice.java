package bgu.spl.mics.application.services;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.Event;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewoks;


/**
 * C3POMicroservices is in charge of the handling {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class C3POMicroservice extends MicroService {

    private Diary diary = Diary.getInstance();
    private Ewoks ewoks = Ewoks.getInstance();
    private AttackEvent attackEvent;

    public C3POMicroservice() {
        super("C3PO");
    }

    @Override
    protected void initialize() {
        subscribeBroadcast(TerminateBroadcast.class, (C3PO)->terminate());
        subscribeEvent(AttackEvent.class, (AttackEvent attackEvent)-> {
            ewoks.acquire(attackEvent.getSerials());
            long duration = attackEvent.getDuration();
            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (Integer ewok : attackEvent.getSerials()) {
                ewoks.release(ewok);
            }
            diary.setTotalAttacks();
            diary.setC3POFinish(System.currentTimeMillis());
            complete(attackEvent, true);
        });
    }

}
