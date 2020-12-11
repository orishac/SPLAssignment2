package bgu.spl.mics.application.services;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.Event;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.BombDestroyerEvent;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.passiveObjects.Diary;

/**
 * LandoMicroservice
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LandoMicroservice  extends MicroService {

    private Diary diary = Diary.getInstance();;
    private long duration;

    public LandoMicroservice(long duration) {
        super("Lando");
        this.duration = duration;
    }

    @Override
    protected void initialize() {
        subscribeBroadcast(TerminateBroadcast.class, (Lando)->terminate());
        subscribeEvent(BombDestroyerEvent.class, (BombDestroyerEvent bomb)-> {
            //handle the bomb destroyer event
            Thread.sleep(duration);
            complete(bomb, true);
            sendBroadcast(new TerminateBroadcast());
       });
    }

}
