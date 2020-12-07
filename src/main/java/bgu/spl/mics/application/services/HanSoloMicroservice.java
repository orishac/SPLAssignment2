package bgu.spl.mics.application.services;


import bgu.spl.mics.Broadcast;
import bgu.spl.mics.Event;
import bgu.spl.mics.Message;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.DeactivationEvent;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewoks;

/**
 * HanSoloMicroservices is in charge of the handling {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class HanSoloMicroservice extends MicroService {

    private Diary diary;
    private Class<? extends Event> attackEvent;
    private Class<? extends Broadcast> TerminateBroadcast;
    private Ewoks ewoks;
    private AttackEvent attack;

    public HanSoloMicroservice() {
        super("Han");
    }


    @Override
    protected void initialize() {
        diary = Diary.getInstance();
        subscribeEvent(attackEvent, (Han)-> {
            try {
                handleAttack(attack);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private void handleAttack(AttackEvent attack) throws InterruptedException {
        //handle attack
        this.attack = attack;
        ewoks = Ewoks.getInstance();
        //get ewok(s)
        Thread.sleep(attack.getDuration());
        diary.setHanSoloFinish(System.currentTimeMillis());
        attack.finished();
        complete(attack, true);
        subscribeBroadcast(TerminateBroadcast, (Han)->terminate());
    }

    private void writeDiary() {
        diary.setHanSoloTerminate(System.currentTimeMillis());
    }


}
