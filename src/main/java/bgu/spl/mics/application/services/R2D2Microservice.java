package bgu.spl.mics.application.services;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.Event;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.BombDestroyerEvent;
import bgu.spl.mics.application.messages.DeactivationEvent;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.passiveObjects.Diary;

/**
 * R2D2Microservices is in charge of the handling {@link DeactivationEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link DeactivationEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class R2D2Microservice extends MicroService {

    private Diary diary;
    private Class<? extends Event> DeactivationEvent;
    private Class<? extends Broadcast> TerminateBroadcast;
    private DeactivationEvent deactivation;
    private long duration;

    public R2D2Microservice(long duration) {
        super("R2D2");
        this.duration = duration;
    }

    @Override
    protected void initialize() {
        diary = Diary.getInstance();
        subscribeEvent(DeactivationEvent, R2D2-> {
            try {
                handleDeactivate(deactivation);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private void handleDeactivate(DeactivationEvent deactivation) throws InterruptedException {
        //handle the deactivation event
        this.deactivation = deactivation;
        Thread.sleep(duration);
        diary.setR2D2Deactivate(System.currentTimeMillis());
        //write to diary
        sendEvent(new BombDestroyerEvent());
        subscribeBroadcast(TerminateBroadcast, (R2D2)->terminate());
    }


}
