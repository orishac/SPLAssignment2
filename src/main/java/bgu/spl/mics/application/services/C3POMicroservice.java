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

    private Diary diary;
    private TerminateBroadcast terminateBroadcast;
    private Ewoks ewoks;
    private AttackEvent attackEvent;

    public C3POMicroservice() {
        super("C3PO");
        attackEvent = new AttackEvent();
        diary = Diary.getInstance();
        terminateBroadcast = new TerminateBroadcast();
        ewoks = Ewoks.getInstance();
    }

    @Override
    protected void initialize() {
        subscribeBroadcast(terminateBroadcast.getClass(), (C3PO)->terminate());
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
            complete(attackEvent, true);
        });
        l1countDown();
    }

    /*
    private void handleAttack(AttackEvent attack) throws InterruptedException {
        //attack handling
        this.attack = attack;
        ewoks = Ewoks.getInstance();
        //get ewok(s)
        for (Integer i : attack.getSerials()) {
            ewoks.getEwok(i);
        }
        Thread.sleep(attack.getDuration());
        diary.setC3POFinish(System.currentTimeMillis());
        attack.finished();
        complete(attack, true);
        subscribeBroadcast(TerminateBroadcast, (C3PO)->terminate());
    }

     */

    private void writeDiary() {
        diary.setC3POTerminate(System.currentTimeMillis());
    }



}
