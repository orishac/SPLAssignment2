package bgu.spl.mics.application.services;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.Event;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
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
    private Class<? extends Event> AttackEvent;
    private Class<? extends Broadcast> TerminateBroadcast;
    private Ewoks ewoks;
    private AttackEvent attack;

    public C3POMicroservice() {
        super("C3PO");
        attack = new AttackEvent();
    }

    @Override
    protected void initialize() {
        diary = Diary.getInstance();
        subscribeEvent(attack.getClass(), (C3PO)-> {
            try {
                handleAttack(attack);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        l1countDown();
    }

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

    private void writeDiary() {
        diary.setC3POTerminate(System.currentTimeMillis());
    }



}
